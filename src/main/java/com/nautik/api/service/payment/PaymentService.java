package com.nautik.api.service.payment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miguelangeljulvez.easyredsys.client.core.MessageOrderCESRequest;
import com.miguelangeljulvez.easyredsys.client.core.OrderCES;
import com.miguelangeljulvez.easyredsys.client.util.Currency;
import com.miguelangeljulvez.easyredsys.client.util.Language;
import com.miguelangeljulvez.easyredsys.client.util.PaymentMethod;
import com.miguelangeljulvez.easyredsys.client.util.TransactionType;
import com.nautik.api.configuration.redsys.AppConfigImpl;
import com.nautik.api.configuration.redsys.RedsysConfig;
import com.nautik.api.domain.Boat;
import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.booking.BookingStatus;
import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.users.User;
import com.nautik.api.dto.payment.PaymentInitRequestDto;
import com.nautik.api.dto.payment.PaymentResponseDto;
import com.nautik.api.repository.bookings.BookingRepository;
import com.nautik.api.repository.moorings.MooringRepository;
import com.nautik.api.repository.user.UserRepository;
import com.nautik.api.service.bookings.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserRepository userRepository;
    private final MooringRepository mooringRepository;
    private final BookingRepository bookingRepository;
    private final RedsysConfig redsysConfig;
    private final AppConfigImpl appConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BookingService bookingService;


    @Transactional
    public PaymentResponseDto initPayment(PaymentInitRequestDto request, String userName) throws Exception {
        Date startDate = parseDate(request.getStartDate());
        Date endDate = parseDate(request.getEndDate());

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Boat boat = user.getBoats().stream()
                .filter(b -> (long) b.getId() == request.getBoatId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Embarcación no encontrada"));

        Booking pendingBooking = bookingService.createBooking(request.getMooringCategoryId(), boat, startDate,endDate);

        String orderNumber = String.format("%012d", System.currentTimeMillis() % 1_000_000_000_000L);

        pendingBooking.setOrderNumber(orderNumber);
        bookingRepository.save(pendingBooking);

        OrderCES orderCES = new OrderCES.Builder(appConfig)
                .transactionType(TransactionType.AUTORIZACION)
                .currency(Currency.EUR)
                .consumerLanguage(Language.SPANISH)
                .order(orderNumber)
                .amount((long) (pendingBooking.getTotalCost() * 100))
                .productDescription("Reserva de amarre en " + pendingBooking.getMooring().getMooringCategory().getZone().getName())
                .payMethods(PaymentMethod.TARJETA)
                .urlOk(redsysConfig.getUrlOk() + "?order=" + orderNumber)
                .urlKo(redsysConfig.getUrlKo() + "?order=" + orderNumber)
                .urlNotification(redsysConfig.getUrlNotification())
                .build();

        MessageOrderCESRequest messageOrderCESRequest = new MessageOrderCESRequest.Builder(orderCES).build();

        return PaymentResponseDto.builder()
                .url(messageOrderCESRequest.getRedsysUrl())
                .dsSignatureVersion(messageOrderCESRequest.getDs_SignatureVersion())
                .dsMerchantParameters(messageOrderCESRequest.getDs_MerchantParameters())
                .dsSignature(messageOrderCESRequest.getDs_Signature())
                .build();
    }

    private Date parseDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return formatter.parse(dateStr);
        } catch (ParseException | java.text.ParseException e) {
            throw new RuntimeException("Formato de fecha inválido. Use dd-MM-yyyy");
        }
    }


    @Transactional
    public void processNotification(String merchantParams, String signature, String signatureVersion) throws Exception {

        merchantParams = merchantParams.replace(' ', '+');
        signature = signature.replace(' ', '+');
        String secretKey = appConfig.getSecretKey(); // obten la clave secreta (en texto)
        if (!validateSignature(merchantParams, signature, secretKey)) {
            throw new SecurityException("Firma inválida en notificación de Redsys");
        }

        String decodedParams = new String(Base64.getDecoder().decode(merchantParams));
        JsonNode json = objectMapper.readTree(decodedParams);

        String orderNumber = json.get("Ds_Order").asText();
        String responseCode = json.get("Ds_Response").asText();
        String errorCode = json.has("Ds_ErrorCode") ? json.get("Ds_ErrorCode").asText() : null;

        Booking booking = bookingRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con número de pedido: " + orderNumber));

        boolean isSuccess = responseCode != null && responseCode.matches("0\\d{3}");

        if (isSuccess) {
            booking.setStatus(BookingStatus.PAID);
        } else {
            booking.setStatus(BookingStatus.FAILED);
        }

        bookingRepository.save(booking);
    }

    private boolean validateSignature(String merchantParams, String signature, String secretKey) {
        try {

            byte[] keyBytes = Base64.getDecoder().decode(secretKey);

            String decodedParams = new String(Base64.getDecoder().decode(merchantParams));
            JsonNode json = objectMapper.readTree(decodedParams);
            String orderNumber = json.get("Ds_Order").asText();

            byte[] diversifiedKey = encrypt3DES(orderNumber.getBytes(StandardCharsets.UTF_8), keyBytes);

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(diversifiedKey, "HmacSHA256");
            mac.init(keySpec);
            byte[] hmacBytes = mac.doFinal(merchantParams.getBytes(StandardCharsets.UTF_8));

            String calculatedSignature = Base64.getUrlEncoder().encodeToString(hmacBytes);

            String normalizedSignature = signature.replace('+', '-').replace('/', '_');

            return calculatedSignature.equals(normalizedSignature);
        } catch (Exception e) {
            throw new RuntimeException("Error al validar firma", e);
        }
    }

    private byte[] encrypt3DES(byte[] data, byte[] key) throws Exception {
        int paddedLength = ((data.length + 7) / 8) * 8;
        byte[] paddedData = new byte[paddedLength];
        System.arraycopy(data, 0, paddedData, 0, data.length);

        SecretKeySpec keySpec = new SecretKeySpec(key, "DESede");
        Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        return cipher.doFinal(paddedData);
    }

}
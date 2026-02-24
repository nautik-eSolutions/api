package com.nautik.api.configuration.redsys;

import com.miguelangeljulvez.easyredsys.client.AppConfig;
import com.miguelangeljulvez.easyredsys.client.core.Notification;
import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.booking.BookingStatus;
import com.nautik.api.repository.bookings.BookingRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AppConfigImpl implements AppConfig {


    private BookingRepository bookingRepository;

    @Value("${redsys.merchantCode}")
    private String merchantCode;

    @Value("${redsys.terminal}")
    private String terminal;

    @Value("${redsys.secretKey}")
    private String secretKey;

    @Value("${redsys.testMode:true}")
    private boolean testMode;

    @Override
    public String getMerchantCode() {
        return this.merchantCode;
    }

    @Override
    public String getTerminal() {
        return this.terminal;
    }

    @Override
    public String getSecretKey() {
        return this.secretKey;
    }

    @Override
    public boolean isTestMode() {
        return this.testMode;
    }

    @Override
    public void saveNotification(Notification notification) {
        log.info("Notificación recibida: {}", notification);

        String orderNumber = notification.getDs_Order();
        String responseCode = notification.getDs_Response();

        Booking booking = bookingRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada para order: " + orderNumber));

        if ("0000".equals(responseCode)) {
            booking.setStatus(BookingStatus.PAID);
            log.info("Reserva {} pagada correctamente", orderNumber);
        } else {
            booking.setStatus(BookingStatus.FAILED);
            log.warn("Reserva {} cancelada. Código respuesta: {}", orderNumber, responseCode);
        }

        bookingRepository.save(booking);
    }
}
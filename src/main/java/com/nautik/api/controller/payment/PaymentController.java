package com.nautik.api.controller.payment;

import com.nautik.api.dto.payment.PaymentInitRequestDto;
import com.nautik.api.dto.payment.PaymentResponseDto;
import com.nautik.api.service.jwt.JwtService;
import com.nautik.api.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final JwtService jwtService;

    @PostMapping("/init")
    public ResponseEntity<PaymentResponseDto> initPayment(
            @RequestBody PaymentInitRequestDto request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws Exception {

        String token = authHeader.substring(7);
        String userName = jwtService.extractUsername(token);

        PaymentResponseDto response = paymentService.initPayment(request, userName);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/notify")
    public ResponseEntity<String> notifyPayment(
            @RequestParam("Ds_MerchantParameters") String merchantParams,
            @RequestParam("Ds_Signature") String signature,
            @RequestParam("Ds_SignatureVersion") String signatureVersion) throws Exception {

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(merchantParams);
        System.out.println(signature);
        System.out.println(signatureVersion);

        paymentService.processNotification(merchantParams, signature, signatureVersion);

        return ResponseEntity.ok("OK");
    }
}
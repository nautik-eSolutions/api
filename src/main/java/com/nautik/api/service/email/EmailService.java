package com.nautik.api.service.email;

import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final Environment environment;
    private final Resend resend;

    @Value("${resend.email}")
    private String from;

    public Map<String, ?> sendEmailVerificationEmail( String to, String firstName) {
        String subject = "Email verificaction";
        String verificationUrl = "";
        try {
            var params = CreateEmailOptions.builder()
                    .from(from)
                    .to(to)
                    .subject(subject)
                    .html(EmailTemplates.verificationTemplate(to,firstName,verificationUrl))
                    .build();

            var response = resend.emails().send(params);
            return Map.of("success", true, "id", response.getId());
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }
}

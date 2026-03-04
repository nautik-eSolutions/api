package com.nautik.api.configuration.email;

import com.resend.Resend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResendConfiguration {

    @Value("${RESEND_API_KEY}")
    private String apiKey;


    @Bean
    public Resend resend() {
        return new Resend(apiKey);
    }

}

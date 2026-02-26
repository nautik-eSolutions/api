package com.nautik.api.configuration.redsys;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "redsys")
public class RedsysConfig {
    private String merchantCode;
    private String terminal;
    private String secretKey;
    private String currency;
    private String transactionType;
    private String urlOk;
    private String urlKo;
    private String urlNotification;
    private String urlRedsys;
}
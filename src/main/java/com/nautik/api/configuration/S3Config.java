package com.nautik.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client(
            @Value("${application.aws.s3.region}") String region,
            @Value("${application.aws.s3.access-key:}") String accessKey,
            @Value("${application.aws.s3.secret-key:}") String secretKey,
            @Value("${application.aws.s3.session-token:}") String sessionToken
    ) {
        S3ClientBuilder builder = S3Client.builder()
                .region(Region.of(region));

        if (StringUtils.hasText(accessKey) && StringUtils.hasText(secretKey)) {
            if (StringUtils.hasText(sessionToken)) {
                builder.credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsSessionCredentials.create(accessKey, secretKey, sessionToken)
                        )
                );
            }
        } else {
            builder.credentialsProvider(DefaultCredentialsProvider.create());
        }

        return builder.build();
    }

    @Bean
    public S3Presigner s3Presigner(
            @Value("${application.aws.s3.region}") String region,
            @Value("${application.aws.s3.access-key:}") String accessKey,
            @Value("${application.aws.s3.secret-key:}") String secretKey,
            @Value("${application.aws.s3.session-token:}") String sessionToken
    ) {
        S3Presigner.Builder builder = S3Presigner.builder()
                .region(Region.of(region));

        if (StringUtils.hasText(accessKey) && StringUtils.hasText(secretKey)) {
            if (StringUtils.hasText(sessionToken)) {
                builder.credentialsProvider(
                        StaticCredentialsProvider.create(AwsSessionCredentials.create(accessKey, secretKey, sessionToken))
                );
            }
        } else {
            builder.credentialsProvider(DefaultCredentialsProvider.create());
        }

        return builder.build();
    }
}
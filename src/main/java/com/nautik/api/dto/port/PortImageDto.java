package com.nautik.api.dto.port;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortImageDto {
    private Integer id;
    private String imageKey;
    private String presignedUrl;
}
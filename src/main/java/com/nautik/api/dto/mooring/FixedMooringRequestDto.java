package com.nautik.api.dto.mooring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FixedMooringRequestDto {
    private Integer id;
    private Integer portId;
    private String portName;
    private Integer mooringId;
    private String mooringNumber;
    private Integer userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPhone;
    private String userIdentificationDocument;
    private String message;
    private String status;
    private String rejectionReason;
    private String createdAt;

}
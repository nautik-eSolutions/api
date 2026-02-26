package com.nautik.api.dto.payment;

import lombok.Data;

@Data
public class PaymentInitRequestDto {
    private Integer mooringCategoryId;
    private String startDate;
    private String endDate;
    private Long boatId;
}
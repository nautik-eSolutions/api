package com.nautik.api.dto.mooring;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewFixedMooringRequestDto {
    private String action;
    private String rejectionReason;
}
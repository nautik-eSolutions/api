package com.nautik.api.dto.mooring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MooringCategoryPriceConfigurationDto implements Serializable {
    private Long id;
    private Long mooringCategoryId;
    private Long priceConfigurationId;
}
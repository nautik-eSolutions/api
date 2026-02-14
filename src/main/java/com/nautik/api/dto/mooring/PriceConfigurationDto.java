package com.nautik.api.dto.mooring;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class PriceConfigurationDto {

    Integer minPrice;
    Date startDate;
    Date endDate;

}

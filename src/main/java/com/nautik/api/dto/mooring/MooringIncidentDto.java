package com.nautik.api.dto.mooring;

import com.nautik.api.domain.moorings.MooringIncidentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MooringIncidentDto {
    private Long id;
    private Long mooringId;
    private String mooringNumber;
    private String description;
    private Date startDate;
    private Date endDate;
    private String status;
}

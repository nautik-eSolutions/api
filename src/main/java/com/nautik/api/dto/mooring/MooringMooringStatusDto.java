package com.nautik.api.dto.mooring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MooringMooringStatusDto implements Serializable {
    private Long id;
    private MooringStatusDto mooring;
    private MooringDto mooringStatus;
    private Instant date;
}
package com.nautik.api.dto.mooring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MooringStatusDto implements Serializable {
    private Long id;
    private String status;
}
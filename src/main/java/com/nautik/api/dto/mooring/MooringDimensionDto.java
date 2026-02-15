package com.nautik.api.dto.mooring;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.nautik.api.domain.moorings.MooringDimension}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MooringDimensionDto implements Serializable {
    private Long id;
    @NotNull
    private Long maxLength;
    @NotNull
    private Long maxBeam;
    @NotNull
    private Long maxDraft;
}
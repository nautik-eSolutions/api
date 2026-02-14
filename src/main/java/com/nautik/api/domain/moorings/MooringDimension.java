package com.nautik.api.domain.moorings;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "mooring_dimensions")
public class MooringDimension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "max_length", nullable = false)
    private Integer maxLength;

    @NotNull
    @Column(name = "max_beam", nullable = false)
    private Integer maxBeam;


}
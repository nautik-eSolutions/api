package com.nautik.api.domain.moorings;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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
    private Long maxLength;

    @NotNull
    @Column(name = "max_beam", nullable = false)
    private Long maxBeam;

    @NotNull
    @Column(name = "max_draft", nullable = false)
    private Long maxDraft;




}
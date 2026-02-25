package com.nautik.api.domain.moorings;

import com.nautik.api.domain.Port;
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
    private Integer id;

    @NotNull
    @Column(name = "max_length", nullable = false)
    private Double maxLength;

    @NotNull
    @Column(name = "max_beam", nullable = false)
    private Double maxBeam;

    @NotNull
    @Column(name = "max_draft", nullable = false)
    private Integer maxDraft;


    private String name;


    @ManyToOne
    @JoinColumn(name = "port_id")
    private Port port;


}
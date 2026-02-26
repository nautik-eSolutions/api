package com.nautik.api.domain.moorings;

import com.nautik.api.domain.Zone;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "mooring_categories")
public class MooringCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;

    @Column(name = "min_price")
    private Double minPricePerDay;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mooring_dimensions_id", nullable = false)
    private MooringDimension dimensions;


    @ManyToMany(mappedBy = "mooringCategories")
    private List<PriceConfiguration> priceConfigurations = new ArrayList<>();




}
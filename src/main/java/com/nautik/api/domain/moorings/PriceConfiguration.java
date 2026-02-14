package com.nautik.api.domain.moorings;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "price_configuration")
public class PriceConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "min_price")
    private Integer minPrice;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToMany
    @JoinTable(
    name = "mooring_category_price_configuration",
    joinColumns = @JoinColumn(name = "price_configuration_id"),
    inverseJoinColumns = @JoinColumn(name = "mooring_category_id")
    )
    List<MooringCategory> mooringCategories = new ArrayList<>();
}

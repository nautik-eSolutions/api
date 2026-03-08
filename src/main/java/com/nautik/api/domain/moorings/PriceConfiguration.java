package com.nautik.api.domain.moorings;

import com.nautik.api.domain.Port;
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
    private Integer id;

    @Column(name = "min_price")
    private Integer minPricePerDay;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    private String name;

    @ManyToOne
    @JoinColumn(name = "port_id")
    private Port port;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
    name = "mooring_category_price_configuration",
    joinColumns = @JoinColumn(name = "price_configuration_id"),
    inverseJoinColumns = @JoinColumn(name = "mooring_category_id")
    )
    List<MooringCategory> mooringCategories = new ArrayList<>();
}

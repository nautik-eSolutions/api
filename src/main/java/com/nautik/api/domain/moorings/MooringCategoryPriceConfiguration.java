package com.nautik.api.domain.moorings;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mooring_category_price_configuration")
public class MooringCategoryPriceConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "mooring_category_id", nullable = false)
    private Long mooringCategoryId;

    @Column(name = "price_configuration_id", nullable = false)
    private Long priceConfigurationId;


}
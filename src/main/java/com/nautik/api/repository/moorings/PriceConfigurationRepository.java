package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.PriceConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PriceConfigurationRepository extends JpaRepository<PriceConfiguration, Long> {
    List<PriceConfiguration> findByMooringCategories(List<MooringCategory> mooringCategories);

    List<PriceConfiguration> findAllByMooringCategories(List<MooringCategory> mooringCategories);

    List<PriceConfiguration> findByMooringCategoriesId(Integer mooringCategoriesId);
}

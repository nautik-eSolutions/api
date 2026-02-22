package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.domain.moorings.PriceConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface PriceConfigurationRepository extends JpaRepository<PriceConfiguration, Integer> {
    List<PriceConfiguration> findByMooringCategories(List<MooringCategory> mooringCategories);

    List<PriceConfiguration> findAllByMooringCategories(List<MooringCategory> mooringCategories);

    List<PriceConfiguration> findByMooringCategoriesId(Integer mooringCategoriesId);

    List<PriceConfiguration> findByEndDateAfterAndStartDateBefore(Date endDateAfter, Date startDateBefore);


    @NativeQuery("select pc.id, pc.min_price, pc.start_date, pc.end_date from " +
            "price_configuration pc inner join mooring_category_price_configuration mcpc on pc.id = mcpc.price_configuration_id " +
            "inner join mooring_categories mc on mcpc.mooring_category_id = mc.id where mc.id = ?1 ")
    PriceConfiguration findByMooringCategoryAndDates(Integer mooringCategoryId);

    Collection<Object> findAllByPortId(Integer portId);
}

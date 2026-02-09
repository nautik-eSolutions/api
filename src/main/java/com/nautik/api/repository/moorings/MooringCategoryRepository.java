package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MooringCategoryRepository extends JpaRepository<MooringCategory, Integer> {
    List<MooringCategory> findAllByZone_Port_Id(Integer zonePortId);
}
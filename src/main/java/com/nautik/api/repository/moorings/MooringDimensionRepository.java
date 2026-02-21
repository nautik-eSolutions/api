package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.MooringDimension;
import com.nautik.api.dto.mooring.MooringDimensionDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MooringDimensionRepository extends JpaRepository<MooringDimension, Integer> {
    List<MooringDimensionDto> findByPortId(Integer portId);
}
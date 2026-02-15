package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.MooringDimension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MooringDimensionRepository extends JpaRepository<MooringDimension, Long> {
}
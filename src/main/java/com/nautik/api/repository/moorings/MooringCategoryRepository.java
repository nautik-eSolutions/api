package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.MooringCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MooringCategoryRepository extends JpaRepository<MooringCategory, Integer> {
    <T> Optional<T> findByDimensions_IdAndZone_Id(Long dimensionsId, Integer zoneId);
}
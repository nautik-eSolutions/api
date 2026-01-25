package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.MooringCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MooringCategoryRepository extends JpaRepository<MooringCategory, Integer> {
}
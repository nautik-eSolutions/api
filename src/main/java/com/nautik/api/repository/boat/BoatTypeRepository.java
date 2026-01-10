package com.nautik.api.repository.boat;

import com.nautik.api.domain.BoatType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoatTypeRepository extends JpaRepository<BoatType, Integer> {
}
package com.nautik.api.repository.boat;

import com.nautik.api.domain.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BoatRepository extends JpaRepository<Boat, Integer>, JpaSpecificationExecutor<Boat> {
}
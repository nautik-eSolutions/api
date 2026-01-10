package com.nautik.api.repository;

import com.nautik.api.domain.Boat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoatRepository extends JpaRepository<Boat, Integer> {
}
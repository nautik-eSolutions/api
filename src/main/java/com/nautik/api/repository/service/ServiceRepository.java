package com.nautik.api.repository.service;

import com.nautik.api.domain.ZoneServicesOffered;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceRepository extends JpaRepository<ZoneServicesOffered,Integer> {
}

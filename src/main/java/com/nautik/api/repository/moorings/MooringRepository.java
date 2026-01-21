package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.Mooring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MooringRepository extends JpaRepository<Mooring, Long> {
}
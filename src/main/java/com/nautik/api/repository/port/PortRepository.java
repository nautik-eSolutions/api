package com.nautik.api.repository.port;

import com.nautik.api.domain.Port;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortRepository extends JpaRepository<Port, Integer> {
}
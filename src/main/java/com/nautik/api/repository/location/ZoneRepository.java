package com.nautik.api.repository.location;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, Integer> {



    List<Zone> findAllByPort_Name(String portName);

    Optional<Zone> findZoneById(Integer id);

    Optional<Zone> findZoneByIdAndPort_Name(Integer id, String portName);

    Optional<Zone> findZoneByIdAndPort(Integer id, Port port);
}
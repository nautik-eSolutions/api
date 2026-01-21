package com.nautik.api.repository.port;

import com.nautik.api.domain.Port;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortRepository extends JpaRepository<Port, Integer> {

    List<Port> findAllByCity_Name(String cityName);

    List<Port> findByName(String name);



}
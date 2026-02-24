package com.nautik.api.repository.port;

import com.nautik.api.domain.Port;
import com.nautik.api.domain.PortImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PortImageRepository extends JpaRepository<PortImage, Integer> {
    List<PortImage> findAllByPortId(Integer portId);
}

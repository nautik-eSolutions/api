package com.nautik.api.repository.port;

import com.nautik.api.domain.Port;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PortRepository extends JpaRepository<Port, Integer> {

    List<Port> findAllByCity_Name(String cityName);

    Optional<Port> findByName(String name);
    
    Optional<Port> findByNameIgnoreCase(String name);

    @Query("select p from Port p inner join Zone z on z.port = p inner join MooringCategory mc on mc.zone = z and mc.id = ?1")
    Optional<Port> findByMooringCategoryId(Integer mooringCategoryId);

    List<Port> findAllByCompanyAdminId(Integer companyAdminId);
}
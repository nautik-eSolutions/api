package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.MooringIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MooringIncidentRepository extends JpaRepository<MooringIncident, Integer> {
    @Query("select m FROM MooringIncident m where m.startDate <= :now and m.endDate >= ?1 and m.mooring.mooringCategory.zone.port.id = ?2 ")
    List<MooringIncident> findCurrentIncidents( Date now,Integer portId);

    @Query("select m from MooringIncident m where m.mooring.mooringCategory.zone.port.id = ?1 ")
    List<MooringIncident> findIncidentsByPort(Integer portId);

}

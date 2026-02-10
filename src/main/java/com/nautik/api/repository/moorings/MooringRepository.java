package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.Mooring;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MooringRepository extends JpaRepository<Mooring, Long> {

    List<Mooring> findAllByMooringCategory_Zone_Port_NameIgnoreCase(String mooringCategoryZonePortName);

    List<Mooring> findAllByMooringCategory_Zone_Id(long mooringCategoryZoneId);


    List<Mooring> findAllByMooringCategoryZonePortId(Integer mooringCategoryZonePortId);
}
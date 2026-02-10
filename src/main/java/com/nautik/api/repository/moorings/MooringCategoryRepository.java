package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface MooringCategoryRepository extends JpaRepository<MooringCategory, Integer> {
    List<MooringCategory> findAllByZone_Port_Id(Integer zonePortId);


    List<MooringCategory> findByZonePortIdAndDimensionsMaxBeamGreaterThanAndDimensionsMaxLengthGreaterThan(Integer zonePortId, Long maxBeam,Long maxLength);
    List<MooringCategory>findByZonePortIdAndDimensionsMaxBeamLessThanAndDimensionsMaxLengthLessThan(Integer zonPortId, Long beam, Long lenght);

    List<MooringCategory> findAllByZonePortId(Integer zonePortId);

    List<MooringCategory> findByZone_Port_Id(Integer portId);



    @Query("select mc from MooringCategory mc inner join Mooring m on m.mooringCategory = mc where m not in ?1 ")
    List<MooringCategory>findMooringCategoriesByMooringNotIn(List<Mooring> moorings);

    List<MooringCategory> findAllByDimensionsMaxBeamGreaterThanEqualAndDimensionsMaxLengthGreaterThanEqual(Integer dimensionsMaxBeamIsGreaterThan, Integer dimensionsMaxLengthIsGreaterThan);

    List<MooringCategory> findAllByDimensionsMaxBeamGreaterThanEqualAndDimensionsMaxLengthGreaterThanEqualAndZonePortId(Integer dimensionsMaxBeamIsGreaterThan, Integer dimensionsMaxLengthIsGreaterThan, Integer zonePortId);
}
package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.Mooring;
import com.nautik.api.domain.moorings.MooringCategory;
import com.nautik.api.dto.mooring.MooringCategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    List<MooringCategory> findAllByZonePortIdAndDimensionsMaxLengthGreaterThanEqualAndDimensionsMaxBeamGreaterThanEqual(Integer zonePortId, Integer dimensionsMaxLengthIsGreaterThan, Integer dimensionsMaxBeamIsGreaterThan);



    @Query("select mc from MooringCategory mc " +
            "inner join MooringDimension md on mc.dimensions = md " +
            "inner join Mooring m on m.mooringCategory = mc " +
            "inner join Zone z on mc.zone = z " +
            "inner join Port p on z.port = p " +
            "where p.id = ?1  and md.maxBeam >= ?3 and md.maxLength >= ?2 " +
            "and m not in (select m1 from Mooring m1 " +
            "inner join Booking b on b.mooring = m1 " +
            "inner join MooringCategory mc1 on m1.mooringCategory = mc1 " +
            "inner join Zone z1 on mc1.zone = z1 " +
            "inner join Port p1 on z.port = p1 " +
            "where p.id = ?1 and b.startDate <= ?5 and b.endDate >= ?4 )")
    List<MooringCategory>getAllByDimensionsAndAvailability(Integer portId,
                                                           Integer length,
                                                           Integer beam,
                                                           Date startDate,
                                                           Date endDate);


    @Query("select mc from MooringCategory mc " +
            "inner join MooringDimension md on mc.dimensions = md " +
            "inner join Mooring m on m.mooringCategory = mc "+
            "where mc.id = ?1" +
            "and m not in (select m1 from Mooring m1 " +
            "inner join Booking b on b.mooring = m1 " +
            "inner join MooringCategory mc1 on m1.mooringCategory = mc1 " +
            "where mc1.id = ?1 and b.startDate <= ?3 and b.endDate >= ?2 )")
    Optional<MooringCategory> getMooringCategoryByAvailability(Integer mooringId, Date startDate, Date endDate);
    
    Optional<MooringCategory> findByDimensions_IdAndZone_Id(Long dimensionsId, Integer zoneId);
}
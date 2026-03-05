package com.nautik.api.repository.boat;

import com.nautik.api.domain.Boat;
import com.nautik.api.dto.boat.BoatDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BoatRepository extends JpaRepository<Boat, Integer>, JpaSpecificationExecutor<Boat> {
    Optional<Boat> findAllByNameAndUser_UserName(String name, String userUserName);

    List<Boat> findAllByUser_Id(Integer userId);

    Object findByIdAndUser_Id(Integer id, Integer userId);

    @Query("select bt from Boat bt inner join Booking bk " +
            "on bk.boat = bt inner join Mooring m on bk.mooring = m " +
            "inner join MooringCategory mc on m.mooringCategory = mc " +
            "inner join Zone z on mc.zone  = z inner join Port p on z.port = p " +
            "where p.id = ?1 and bk.startDate < ?3 and bk.endDate > ?2  and (bk.status = 'PAID' or bk.status = 'PENDING') ")
    List<Boat>getAllBoatsInPortBetweenDates(Integer portId, Date startDate, Date endDate);

    Optional<Boat> findAllByIdAndUser_Id(Integer id, Integer userId);
}
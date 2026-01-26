package com.nautik.api.repository.boat;

import com.nautik.api.domain.Boat;
import com.nautik.api.dto.boat.BoatDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BoatRepository extends JpaRepository<Boat, Integer>, JpaSpecificationExecutor<Boat> {
    Optional<Boat> findAllByNameAndUser_UserName(String name, String userUserName);

    List<Boat> findAllByUser_Id(Integer userId);
}
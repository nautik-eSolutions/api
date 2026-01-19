package com.nautik.api.repository.location;

import com.nautik.api.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer> {

    List<Optional<City>> getAllByCommunity_Name(String communityName);

    Optional<City> getByNameContainsIgnoreCase(String name);


}
package com.nautik.api.repository.location;

import com.nautik.api.domain.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Integer> {

    Optional<Community> findByName(String name);

}
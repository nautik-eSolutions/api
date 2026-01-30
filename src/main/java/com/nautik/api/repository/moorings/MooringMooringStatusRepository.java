package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.MooringMooringStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MooringMooringStatusRepository extends JpaRepository<MooringMooringStatus, Long> {

    List<MooringMooringStatus> findByMooring_Id(Long mooringId);

    MooringMooringStatus findFirstByMooring_Id(Long mooringId);
}
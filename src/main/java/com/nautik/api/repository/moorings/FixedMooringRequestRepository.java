package com.nautik.api.repository.moorings;

import com.nautik.api.domain.moorings.FixedMooringRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FixedMooringRequestRepository extends JpaRepository<FixedMooringRequest, Integer> {

    List<FixedMooringRequest> findByPortIdOrderByCreatedAtDesc(Integer portId);

    List<FixedMooringRequest> findByPortIdAndStatusOrderByCreatedAtDesc(Integer portId, FixedMooringRequest.RequestStatus status);

    List<FixedMooringRequest> findByUserIdOrderByCreatedAtDesc(Integer userId);

    @Query("SELECT fmr FROM FixedMooringRequest fmr " +
            "WHERE fmr.id = ?1 AND fmr.port.id = ?2")
    Optional<FixedMooringRequest> findByIdAndPortId(Integer requestId, Integer portId);

    @Query("SELECT COUNT(fmr) > 0 FROM FixedMooringRequest fmr " +
            "WHERE fmr.user.id = ?1 " + "AND fmr.status = 'PENDING'")
    boolean existsPendingRequestByUser(Integer userId);
}
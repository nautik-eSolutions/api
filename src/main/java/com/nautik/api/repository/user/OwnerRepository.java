package com.nautik.api.repository.user;

import com.nautik.api.domain.users.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
}
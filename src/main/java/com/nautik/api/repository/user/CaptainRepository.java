package com.nautik.api.repository.user;

import com.nautik.api.domain.users.Captain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaptainRepository extends JpaRepository<Captain, Integer> {
}
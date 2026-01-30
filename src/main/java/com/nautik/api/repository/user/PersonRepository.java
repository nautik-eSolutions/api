package com.nautik.api.repository.user;

import com.nautik.api.domain.users.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
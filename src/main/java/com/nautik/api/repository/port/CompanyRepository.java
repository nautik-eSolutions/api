package com.nautik.api.repository.port;

import com.nautik.api.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    public List<Company> findByNameContainingIgnoreCase(String name);

    Optional<Company> findByName(String name);


}
package com.nautik.api.repository.port;

import com.nautik.api.domain.Company;
import com.nautik.api.domain.Port;
import com.nautik.api.domain.users.Admin;
import com.nautik.api.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    public List<Company> findByNameContainingIgnoreCase(String name);

    Optional<Company> findByName(String name);


    Optional<Company> findCompanyByName(String name);

    Optional<Company> findFirstByName(String name);

    Optional<Company> findById(Long id);

    Optional<Company>findByAdmin(Admin admin);
}
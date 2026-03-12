package com.nautik.api.service.company;

import com.nautik.api.domain.Company;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.domain.roles.Role;
import com.nautik.api.domain.users.Admin;
import com.nautik.api.dto.admin.AdminCompanyRequest;
import com.nautik.api.dto.port.company.CompanyDto;
import com.nautik.api.dto.port.company.CompanyDtoResponse;
import com.nautik.api.repository.port.CompanyRepository;
import com.nautik.api.repository.roles.RoleRepository;
import com.nautik.api.repository.user.AdminRepository;
import com.nautik.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public CompanyDtoResponse findCompanyByName(String name){
        Company searchedCompany = companyRepository.findByName(name).orElseThrow(()->new EntityNotFoundException("Company not found"));

        return modelMapper.map(searchedCompany, CompanyDtoResponse.class);
    }


    public CompanyDtoResponse createCompany(CompanyDto companyDto, String username, String password) {
        Company providedCompany = modelMapper.map(companyDto, Company.class);
        Company createdCompany = companyRepository.save(providedCompany);

        Role role = roleRepository.findByName("ADMIN_COMPANY");
        if (role == null) {
            throw new EntityNotFoundException("Role ADMIN_COMPANY not found");
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole(role);
        admin.setCompany(createdCompany);
        admin.setPort(null);

        Admin createdAdmin = adminRepository.save(admin);
        createdCompany.setAdmin(createdAdmin);

        return modelMapper.map(
                companyRepository.save(createdCompany),
                CompanyDtoResponse.class
        );
    }


    public CompanyDtoResponse updateCompany(CompanyDto companyDto,Long companyId) {
        Company searchedCompany = companyRepository.findById(companyId).orElseThrow(()->new EntityNotFoundException("Company not found"));

        companyDto.setId(searchedCompany.getId());

        Company mappedCompany =  modelMapper.map(companyDto, Company.class);

        return modelMapper.map(
                companyRepository.save(mappedCompany),
                CompanyDtoResponse.class);
    }

    public void deleteCompany(Long companyId) {
        Company searchedCompany = companyRepository.findById(companyId).orElseThrow();
        companyRepository.delete(searchedCompany);
    }

    public List<CompanyDtoResponse> getAllCompanies(){

        List<Company>companies =  companyRepository.findAll();
        return companies.stream().map(
                company -> modelMapper.map(company, CompanyDtoResponse.class))
                .collect(Collectors.toList());
    }


    public CompanyDtoResponse findCompanyById(Long id) {
        return modelMapper.map(companyRepository.findById(id), CompanyDtoResponse.class);

    }
}

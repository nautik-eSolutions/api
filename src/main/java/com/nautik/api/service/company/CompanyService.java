package com.nautik.api.service.company;

import com.nautik.api.domain.Company;
import com.nautik.api.domain.exceptions.EntityNotFoundException;
import com.nautik.api.dto.port.company.CompanyDto;
import com.nautik.api.dto.port.company.CompanyDtoResponse;
import com.nautik.api.repository.port.CompanyRepository;
import com.nautik.api.repository.user.AdminRepository;
import com.nautik.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;


    public CompanyDtoResponse findCompanyByName(String name){
        Company searchedCompany = companyRepository.findByName(name).orElseThrow(()->new EntityNotFoundException("Company not found"));

        return modelMapper.map(searchedCompany, CompanyDtoResponse.class);
    }


    public CompanyDtoResponse createCompany(CompanyDto companyDto, Integer userId) {
        Company providedCompany = modelMapper.map(companyDto, Company.class);

        providedCompany.setAdmin(adminRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("Administrador not found")));

        return modelMapper.map(
                companyRepository.save(providedCompany),
                CompanyDtoResponse.class);
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

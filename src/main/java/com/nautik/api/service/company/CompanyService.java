package com.nautik.api.service.company;

import com.nautik.api.domain.Company;
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
        Company searchedCompany = companyRepository.findByName(name).orElseThrow();

        return modelMapper.map(searchedCompany, CompanyDtoResponse.class);
    }


    public CompanyDtoResponse createCompany(CompanyDto companyDto, String userName) {
        Company providedCompany = modelMapper.map(companyDto, Company.class);

        providedCompany.getAdmins().add(adminRepository.findByUser_UserName(userName).orElseThrow());

        return modelMapper.map(
                companyRepository.save(providedCompany),
                CompanyDtoResponse.class);
    }


    public CompanyDtoResponse updateCompany(CompanyDto companyDto,String name) {
        Company searchedCompany = companyRepository.findByNameContainingIgnoreCase(name).stream().findFirst().orElseThrow();

        companyDto.setId(searchedCompany.getId());

        Company mappedCompany =  modelMapper.map(companyDto, Company.class);

        return modelMapper.map(
                companyRepository.save(mappedCompany),
                CompanyDtoResponse.class);
    }

    public void deleteCompany(String name) {
        Company searchedCompany = companyRepository.findByNameContainingIgnoreCase(name).stream().findFirst().orElseThrow();
        companyRepository.delete(searchedCompany);
    }

    public List<CompanyDtoResponse> getAllCompanies(){

        List<Company>companies =  companyRepository.findAll();
        return companies.stream().map(
                company -> modelMapper.map(company, CompanyDtoResponse.class))
                .collect(Collectors.toList());
    }


}

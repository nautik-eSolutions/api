package com.nautik.api.service.company;

import com.nautik.api.domain.Company;
import com.nautik.api.dto.port.company.CompanyDto;
import com.nautik.api.dto.port.company.CompanyDtoResponse;
import com.nautik.api.repository.port.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CompanyDtoResponse createCompany(CompanyDto companyDto) {

        Company providedCompany = modelMapper.map(companyDto, Company.class);

        CompanyDtoResponse createdCompany = modelMapper.map(
                companyRepository.save(providedCompany),
                CompanyDtoResponse.class);

        return createdCompany;
    }


    public CompanyDtoResponse updateCompany(CompanyDto companyDto,String name) {
        Company searchedCompany = companyRepository.getCompanyByNameContainingIgnoreCase(name);

        companyDto.setId(searchedCompany.getId());

        Company mappedCompany =  modelMapper.map(companyDto, Company.class);

        CompanyDtoResponse updatedCompany = modelMapper.map(
                companyRepository.save(mappedCompany),
                CompanyDtoResponse.class);

        return updatedCompany;
    }

    public CompanyDtoResponse deleteCompany(String firstName) {

    }


}

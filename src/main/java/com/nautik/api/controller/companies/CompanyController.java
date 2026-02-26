package com.nautik.api.controller.companies;

import com.nautik.api.dto.port.company.CompanyDto;
import com.nautik.api.dto.port.company.CompanyDtoResponse;
import com.nautik.api.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @GetMapping("/{idCompany}")
    public ResponseEntity<CompanyDtoResponse> getCompanyByName(@PathVariable Long idCompany){

        return ResponseEntity.ok(companyService.findCompanyById(idCompany));

    }

    @GetMapping
    public ResponseEntity<List<CompanyDtoResponse>> getAllCompanies(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }


    @PostMapping("/administrators/{idUser}")
    public ResponseEntity<CompanyDtoResponse> createCompany(
            @RequestBody CompanyDto companyDto,
            @PathVariable Integer idUser){
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(companyDto,idUser));
    }

    @PatchMapping("/{companyId}")
    public ResponseEntity<CompanyDtoResponse>updateCompany(
            @RequestBody CompanyDto companyDto,
            @PathVariable Long companyId){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(companyService.updateCompany(companyDto,companyId));
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompanyByName(@PathVariable Long companyId){
        companyService.deleteCompany(companyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}

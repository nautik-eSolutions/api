package com.nautik.api.controller.companies;

import com.nautik.api.dto.port.company.CompanyDto;
import com.nautik.api.dto.port.company.CompanyDtoResponse;
import com.nautik.api.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @GetMapping("/{name}")
    public ResponseEntity<CompanyDtoResponse> getCompanyByName(@PathVariable String name){

        return ResponseEntity.ok(companyService.findCompanyByName(name));

    }

    public ResponseEntity<List<CompanyDtoResponse>> getAllCompanies(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }


    @PostMapping
    public ResponseEntity<CompanyDtoResponse> createCompany(@RequestBody CompanyDto companyDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(companyDto));
    }

    @PatchMapping("/{name}")
    public ResponseEntity<CompanyDtoResponse>updateCompany(
            @RequestBody CompanyDto companyDto,
            @PathVariable String name){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(companyService.updateCompany(companyDto,name));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCompanyByName(@PathVariable String name){
        companyService.deleteCompany(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}

package com.aviad.coupons.controllers;

import com.aviad.coupons.dto.Company;
import com.aviad.coupons.exceptions.ApplicationException;
import com.aviad.coupons.logic.CompanyLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {
    private CompanyLogic companyLogic;

    @Autowired
    public CompaniesController(CompanyLogic companyLogic) {
        this.companyLogic = companyLogic;
    }

    // Add company
    @PostMapping
    public void createCompany(@RequestBody Company company) throws ApplicationException {
        this.companyLogic.addCompany(company);
    }

    // Update company
    @PutMapping
    public void updateCompany(@RequestBody Company company) throws ApplicationException {
        this.companyLogic.updateCompany(company);
    }

    // Get all companies
    @GetMapping
    public List<Company> getCompanies() throws ApplicationException {
        return this.companyLogic.getCompanies();
    }

    // Get specific company by id
    @GetMapping("/{id}")
    public Company getCompany(@PathVariable("id") Integer id) throws ApplicationException {
        return this.companyLogic.getCompany(id);
    }

    // Delete specific company
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable("id") Integer id) throws ApplicationException {
        this.companyLogic.deleteCompany(id);
    }
}

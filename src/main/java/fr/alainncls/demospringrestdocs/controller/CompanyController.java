package fr.alainncls.demospringrestdocs.controller;

import fr.alainncls.demospringrestdocs.model.Company;
import fr.alainncls.demospringrestdocs.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/")
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable("id") String id) {
        return companyService.getCompany(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCompany(@RequestBody Company company) {
        companyService.createCompany(company);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable("id") String id) {
        companyService.deleteCompany(id);
    }
}

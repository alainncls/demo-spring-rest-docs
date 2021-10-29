package fr.alainncls.demospringrestdocs.service;

import fr.alainncls.demospringrestdocs.exception.CompanyNotFoundException;
import fr.alainncls.demospringrestdocs.model.Company;
import fr.alainncls.demospringrestdocs.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompany(String id) {
        return companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }

    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    public void deleteCompany(String id) {
        companyRepository.deleteById(id);
    }
}

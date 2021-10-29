package fr.alainncls.demospringrestdocs.service;

import fr.alainncls.demospringrestdocs.exception.CompanyNotFoundException;
import fr.alainncls.demospringrestdocs.model.Company;
import fr.alainncls.demospringrestdocs.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CompanyServiceTest {

    private final Company company1 = Company.builder().id("ID_1").name("CoolCorp").location("Paris").creationDate(new Date()).build();
    private final Company company2 = Company.builder().id("ID_2").name("OtherCorp").location("London").creationDate(new Date()).build();
    @Mock
    private CompanyRepository companyRepository;
    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    void getAllCompanies() {
        List<Company> expectedCompanies = List.of(company1, company2);

        when(companyRepository.findAll()).thenReturn(expectedCompanies);

        List<Company> result = companyService.getAllCompanies();

        assertThat(result).isNotNull().hasSize(expectedCompanies.size()).isEqualTo(expectedCompanies);
    }

    @Test
    void getCompany() {
        final String ID = "ID_1";

        when(companyRepository.findById(ID)).thenReturn(Optional.of(company1));

        Company result = companyService.getCompany(ID);

        assertThat(result).isNotNull().isEqualTo(company1);
    }

    @Test
    void getCompanyNotFound() {
        assertThrows(CompanyNotFoundException.class, () -> companyService.getCompany("UNKNOWN_ID"));
    }

    @Test
    void createCompany() {
        when(companyRepository.save(company1)).thenReturn(company1);

        companyService.createCompany(company1);

        verify(companyRepository, times(1)).save(company1);
    }

    @Test
    void deleteCompany() {
        final String ID = "ID_1";

        doNothing().when(companyRepository).deleteById(ID);

        companyService.deleteCompany(ID);

        verify(companyRepository, times(1)).deleteById(ID);
    }
}

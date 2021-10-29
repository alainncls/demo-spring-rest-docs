package fr.alainncls.demospringrestdocs;

import fr.alainncls.demospringrestdocs.controller.CompanyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DemoSpringRestDocsApplicationTests {

    @Autowired
    CompanyController companyController;

    @Test
    void contextLoads() {
        assertNotNull(companyController);
    }

}

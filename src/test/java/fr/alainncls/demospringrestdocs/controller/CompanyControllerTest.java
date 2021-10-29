package fr.alainncls.demospringrestdocs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.alainncls.demospringrestdocs.exception.CompanyNotFoundException;
import fr.alainncls.demospringrestdocs.model.Company;
import fr.alainncls.demospringrestdocs.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = CompanyController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class CompanyControllerTest {

    private final Company company1 = Company.builder().id("ID_1").name("CoolCorp").location("Paris").creationDate(new Date()).build();
    private final Company company1ToCreate = Company.builder().name("CoolCorp").location("Paris").creationDate(new Date()).build();
    private final Company company2 = Company.builder().id("ID_2").name("OtherCorp").location("London").creationDate(new Date()).build();
    @MockBean
    private CompanyService companyService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCompanies() throws Exception {
        List<Company> expectedCompanies = List.of(company1, company2);

        when(companyService.getAllCompanies()).thenReturn(expectedCompanies);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/companies/"))
                .andExpect(handler().handlerType(CompanyController.class))
                .andExpect(handler().methodName("getAllCompanies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCompanies)))
                .andDo(document(
                        "getAllCompanies",
                        ControllerTestUtils.preprocessRequest(),
                        ControllerTestUtils.preprocessResponse(),
                        responseFields(
                                fieldWithPath("[].id").description("The company unique ID"),
                                fieldWithPath("[].name").description("The company name"),
                                fieldWithPath("[].location").description("The company location"),
                                fieldWithPath("[].creationDate").description("The company creation date"))));
    }

    @Test
    void getCompany() throws Exception {
        final String ID = "ID_1";

        when(companyService.getCompany(ID)).thenReturn(company1);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/companies/{id}", ID))
                .andExpect(handler().handlerType(CompanyController.class))
                .andExpect(handler().methodName("getCompany"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(company1)))
                .andDo(document(
                        "getCompany",
                        ControllerTestUtils.preprocessRequest(),
                        ControllerTestUtils.preprocessResponse(),
                        pathParameters(parameterWithName("id").description("The requested company id")),
                        responseFields(
                                fieldWithPath("id").description("The company unique ID"),
                                fieldWithPath("name").description("The company name"),
                                fieldWithPath("location").description("The company location"),
                                fieldWithPath("creationDate").description("The company creation date"))));
    }

    @Test
    void getCompanyNotFound() throws Exception {
        final String ID = "ID_3";

        when(companyService.getCompany(ID)).thenThrow(new CompanyNotFoundException());

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/companies/{id}", ID))
                .andExpect(handler().handlerType(CompanyController.class))
                .andExpect(handler().methodName("getCompany"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document(
                        "getCompanyNotFound",
                        ControllerTestUtils.preprocessRequest(),
                        ControllerTestUtils.preprocessResponse()));
    }

    @Test
    void createCompany() throws Exception {
        doNothing().when(companyService).createCompany(company1ToCreate);

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/companies/")
                        .content(objectMapper.writeValueAsString(company1ToCreate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(handler().handlerType(CompanyController.class))
                .andExpect(handler().methodName("createCompany"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document(
                        "createCompany",
                        ControllerTestUtils.preprocessRequest(),
                        ControllerTestUtils.preprocessResponse(),
                        requestFields(
                                fieldWithPath("id").ignored(),
                                fieldWithPath("name").description("The company name"),
                                fieldWithPath("location").description("The company location"),
                                fieldWithPath("creationDate").description("The company creation date"))));
    }

    @Test
    void deleteCompany() throws Exception {
        final String ID = "ID_1";

        doNothing().when(companyService).deleteCompany(ID);

        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/companies/{id}", ID)
                        .content(objectMapper.writeValueAsString(company1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(handler().handlerType(CompanyController.class))
                .andExpect(handler().methodName("deleteCompany"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document(
                        "deleteCompany",
                        ControllerTestUtils.preprocessRequest(),
                        ControllerTestUtils.preprocessResponse(),
                        pathParameters(parameterWithName("id").description("The ID of the company to delete"))));
    }
}

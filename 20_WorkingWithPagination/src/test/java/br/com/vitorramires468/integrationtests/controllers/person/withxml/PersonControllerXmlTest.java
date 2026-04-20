package br.com.vitorramires468.integrationtests.controllers.person.withxml;

import br.com.vitorramires468.config.TestConfigs;
import br.com.vitorramires468.integrationtests.dto.PersonDTO;
import br.com.vitorramires468.integrationtests.dto.wrapper.xml.person.PagedModelPerson;
import br.com.vitorramires468.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerXmlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static XmlMapper xmlMapper;
    private static PersonDTO person;

    @BeforeAll
    static void setUp(){
        xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        person = new PersonDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException, JAXBException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .setBasePath("person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .accept("application/xml")
                .contentType(MediaType.APPLICATION_XML_VALUE)
                    .body(person)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                        .asString();



        PersonDTO createdPerson = xmlMapper.readValue(content, PersonDTO.class);

        person = createdPerson;

        assertNotNull(createdPerson.getId());

        assertTrue(createdPerson.getId()>0);

        assertEquals("Linus",createdPerson.getFirstName());
        assertEquals("Torvalds" ,createdPerson.getLastName());
        assertEquals("Helsinki - Finland",createdPerson.getAddress());
        assertEquals("Male",createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        person.setLastName("Benedict Torvalds");

        var content = given(specification)
                .accept("application/xml")
                .contentType(MediaType.APPLICATION_XML_VALUE)
                    .body(person)
                .when()
                    .put()
                .then()
                    .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                        .asString();

        PersonDTO createdPerson = xmlMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());

        assertTrue(createdPerson.getId()>0);

        assertEquals("Linus",createdPerson.getFirstName());
        assertEquals("Benedict Torvalds" ,createdPerson.getLastName());
        assertEquals("Helsinki - Finland",createdPerson.getAddress());
        assertEquals("Male",createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());

    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {
        var content = given(specification)
                .pathParam("id", person.getId())
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .accept("application/xml")
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = xmlMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());

        assertTrue(createdPerson.getId()>0);

        assertEquals("Linus",createdPerson.getFirstName());
        assertEquals("Benedict Torvalds" ,createdPerson.getLastName());
        assertEquals("Helsinki - Finland",createdPerson.getAddress());
        assertEquals("Male",createdPerson.getGender());
        assertTrue(createdPerson.getEnabled());
    }

    @Test
    @Order(4)
    void disablePersonTest() throws JsonProcessingException {
        var content = given(specification)
                .accept("application/xml")
                .pathParam("id", person.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = xmlMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());

        assertTrue(createdPerson.getId()>0);

        assertEquals("Linus",createdPerson.getFirstName());
        assertEquals("Benedict Torvalds" ,createdPerson.getLastName());
        assertEquals("Helsinki - Finland",createdPerson.getAddress());
        assertEquals("Male",createdPerson.getGender());
        assertFalse(createdPerson.getEnabled());
    }

    @Test
    @Order(5)
    void deleteByIdTest() throws JsonProcessingException {
        given(specification)
                    .pathParam("id", person.getId())
                .when()
                    .delete("{id}")
                .then()
                    .statusCode(204);
    }

    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {

        var content = given(specification)
                .accept("application/xml")
                .queryParam("page",3, "size", 5, "direction", "asc")
                .contentType(MediaType.APPLICATION_XML_VALUE)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .contentType(MediaType.APPLICATION_XML_VALUE)
                    .extract()
                .body()
                    .asString();

        PagedModelPerson wrapper = xmlMapper.readValue(content, PagedModelPerson.class);
        List<PersonDTO> people = wrapper.getContent();

        PersonDTO personOne = people.get(0);

        assertNotNull(personOne.getId());

        assertTrue(personOne.getId()>0);

        assertEquals("Alexei", personOne.getFirstName());
        assertEquals("Forst" , personOne.getLastName());
        assertEquals("Apt 976", personOne.getAddress());
        assertEquals("Male", personOne.getGender());
        assertTrue(personOne.getEnabled());


        PersonDTO personTwo = people.get(1);

        assertTrue(personTwo.getId()>0);

        assertEquals("Alfie", personTwo.getFirstName());
        assertEquals("Hagston" , personTwo.getLastName());
        assertEquals("1st Floor", personTwo.getAddress());
        assertEquals("Male", personTwo.getGender());
        assertFalse(personTwo.getEnabled());
    }

    @Test
    @Order(7)
    void findByNameTest() throws JsonProcessingException {

        var content = given(specification)
                .accept("application/xml")
                    .pathParam("firstName", "and")
                    .queryParam("page",0, "size", 3, "direction", "desc")
                    .contentType(MediaType.APPLICATION_XML_VALUE)
                .when()
                    .get("/findPeopleByName/{firstName}")
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_XML_VALUE)
                .extract()
                    .body()
                        .asString();

        PagedModelPerson wrapper = xmlMapper.readValue(content, PagedModelPerson.class);
        List<PersonDTO> people = wrapper.getContent();

        PersonDTO personOne = people.get(0);

        assertNotNull(personOne.getId());

        assertTrue(personOne.getId()>0);

        assertEquals("Alexandre", personOne.getFirstName());
        assertEquals("Jiranek" , personOne.getLastName());
        assertEquals("PO Box 53091", personOne.getAddress());
        assertEquals("Male", personOne.getGender());
        assertTrue(personOne.getEnabled());


        PersonDTO personTwo = people.get(1);

        assertTrue(personTwo.getId()>0);

        assertEquals("Amanda", personTwo.getFirstName());
        assertEquals("Anderson" , personTwo.getLastName());
        assertEquals("975 TX", personTwo.getAddress());
        assertEquals("Female", personTwo.getGender());
        assertTrue(personTwo.getEnabled());
    }


    private void mockPerson() {
        person.setFirstName("Linus");
        person.setLastName("Torvalds");
        person.setAddress("Helsinki - Finland");
        person.setGender("Male");
        person.setEnabled(true);
    }
}
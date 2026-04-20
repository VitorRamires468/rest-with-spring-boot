package br.com.vitorramires468.integrationtests.controllers.book.withyaml;

import br.com.vitorramires468.config.TestConfigs;
import br.com.vitorramires468.integrationtests.controllers.person.withYaml.mapper.YamlMapper;
import br.com.vitorramires468.integrationtests.dto.BookDTO;
import br.com.vitorramires468.integrationtests.dto.wrapper.xml.book.PagedModelBook;
import br.com.vitorramires468.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static YamlMapper yamlMapper;
    private static BookDTO bookDTO;

    @BeforeAll
    static void beforeAll() {
       yamlMapper =  new YamlMapper();

        bookDTO = new BookDTO();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_ERUDIO)
                .setBasePath("/book/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        mockBook();

        var content = given(specification)
                .config(RestAssuredConfig.config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs("application/yaml", ContentType.TEXT))
                )
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                    .body(bookDTO, yamlMapper)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                        .as(BookDTO.class, yamlMapper);

        var createdBook = content;


        assertNotNull(createdBook);

        assertTrue(createdBook.getId() > 0);

        assertEquals("John", createdBook.getAuthor() );
        assertEquals("Book 1", createdBook.getTitle());
        assertEquals(22.0, createdBook.getPrice());
        assertEquals(new Date(2000,01,01),createdBook.getLaunchDate());
        assertTrue(createdBook.getEnabled());

    }

    @Test
    @Order(2)
    void update() throws JsonProcessingException {

        bookDTO.setTitle("Book 2");

        var content = given(specification)
                .config(RestAssuredConfig.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs("application/yaml", ContentType.TEXT))
                )
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                    .body(bookDTO, yamlMapper)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                        .as(BookDTO.class, yamlMapper);

        BookDTO updatedBook = content;
        bookDTO = updatedBook;

        assertNotNull(updatedBook);

        assertTrue(updatedBook.getId() > 0);

        assertEquals("John", updatedBook.getAuthor() );
        assertEquals("Book 2", updatedBook.getTitle());
        assertEquals(22.0, updatedBook.getPrice());
        assertEquals(new Date(2000,01,01), updatedBook.getLaunchDate());
        assertTrue(updatedBook.getEnabled());

    }


    @Test
    @Order(3)
    void findById() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", bookDTO.getId())
                .when()
                    .get("/{id}")
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                        .as(BookDTO.class, yamlMapper);

        BookDTO createdBook = bookDTO;

        assertNotNull(createdBook);

        assertTrue(createdBook.getId() > 0);

        assertEquals("John", createdBook.getAuthor() );
        assertEquals("Book 2", createdBook.getTitle());
        assertEquals(22.0, createdBook.getPrice());
        assertEquals(new Date(2000,01,01),createdBook.getLaunchDate());
        assertTrue(createdBook.getEnabled());
    }

    @Test
    @Order(4)
    void disableBook() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", bookDTO.getId())
                .when()
                    .patch("/{id}")
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                        .as(BookDTO.class, yamlMapper);

        BookDTO disabledBook = content;

        assertNotNull(disabledBook);

        assertTrue(disabledBook.getId() > 0);

        assertFalse(disabledBook.getEnabled());

        assertEquals("John", disabledBook.getAuthor() );
        assertEquals("Book 2", disabledBook.getTitle());
        assertEquals(22.0, disabledBook.getPrice());
        assertEquals(new Date(2000,01,01), disabledBook.getLaunchDate());
    }

    @Test
    @Order(5)
    void deleteById() {
        given(specification)
                .pathParam("id", bookDTO.getId())
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    void findAll() throws JsonProcessingException {
        var content = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .queryParam("page", 1, "size", "2", "direction", "asc")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                    .body()
                        .as(PagedModelBook.class, yamlMapper);


        List<BookDTO> bookDTOList = content.getContent();

        BookDTO bookOne = bookDTOList.get(0);

        assertNotNull(bookOne);

        assertTrue(bookOne.getId() > 0);

        assertTrue(bookOne.getEnabled());
        assertEquals("Eric Evans", bookOne.getAuthor() );
        assertEquals("Domain Driven Design", bookOne.getTitle());
        assertEquals(92.0, bookOne.getPrice());
        assertEquals(new Date(117 ,10,07),bookOne.getLaunchDate());
    }

    private void mockBook(){
        bookDTO.setAuthor("John");
        bookDTO.setTitle("Book 1");
        bookDTO.setPrice(22.0);
        bookDTO.setLaunchDate(new Date(2000,01,01));
        bookDTO.setEnabled(true);
    }

}
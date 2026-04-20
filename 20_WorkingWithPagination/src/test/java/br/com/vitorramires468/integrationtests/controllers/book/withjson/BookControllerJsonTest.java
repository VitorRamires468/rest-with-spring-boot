package br.com.vitorramires468.integrationtests.controllers.book.withjson;

import br.com.vitorramires468.config.TestConfigs;
import br.com.vitorramires468.integrationtests.dto.BookDTO;
import br.com.vitorramires468.integrationtests.dto.wrapper.json.book.WrapperBookDTO;
import br.com.vitorramires468.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
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
class BookControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static BookDTO bookDTO;

    @BeforeAll
    static void beforeAll() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

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
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(bookDTO)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                    .body()
                        .asString();

        BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);
        bookDTO = createdBook;

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
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(bookDTO)
                .when()
                    .put()
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                    .body()
                        .asString();

        BookDTO updatedBook = objectMapper.readValue(content, BookDTO.class);
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
                .pathParam("id", bookDTO.getId())
                .when()
                    .get("/{id}")
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                    .body()
                        .asString();

        BookDTO createdBook = objectMapper.readValue(content, BookDTO.class);

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
                .pathParam("id", bookDTO.getId())
                .when()
                    .patch("/{id}")
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                    .body()
                        .asString();

        BookDTO disabledBook = objectMapper.readValue(content, BookDTO.class);
        bookDTO = disabledBook;

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
                .queryParam("page", 1, "size", "2", "direction", "asc")
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                    .body()
                        .asString();

        WrapperBookDTO wrapper =  objectMapper.readValue(content, WrapperBookDTO.class);
        List<BookDTO> bookDTOList = wrapper.getEmbedded().getBooks();

        var bookOne = bookDTOList.get(0);

        assertNotNull(bookOne);

        assertTrue(bookOne.getId() > 0);

        assertEquals("Roger S. Pressman", bookOne.getAuthor() );
        assertEquals("Engenharia de Software: uma abordagem profissional", bookOne.getTitle());
        assertEquals(56.0, bookOne.getPrice());
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
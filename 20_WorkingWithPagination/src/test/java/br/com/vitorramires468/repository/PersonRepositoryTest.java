package br.com.vitorramires468.repository;

import br.com.vitorramires468.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.vitorramires468.model.Person;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PersonRepository repository;

    private static Person person;

    @BeforeAll
    static void setUp() {
        person = new Person();
    }

    @Test
    @Order(1)
    void findPeopleByName() {
        Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.ASC, "firstName"));

        person = repository.findPeopleByName("and", pageable).getContent().get(0);

        assertNotNull(person);
        assertNotNull(person.getId());

        assertEquals("Alexandre",person.getFirstName());
        assertEquals("Jiranek",person.getLastName());
        assertEquals("PO Box 53091",person.getAddress());
        assertEquals("Male",person.getGender());
        assertTrue(person.getEnabled());
    }

    @Test
    @Order(2)
    void disablePerson() {

        Long id = person.getId();

        repository.disablePerson(id);

        var result = repository.findById(id).get();


        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals("Alexandre",result.getFirstName());
        assertEquals("Jiranek",result.getLastName());
        assertEquals("PO Box 53091",result.getAddress());
        assertEquals("Male",result.getGender());
        assertFalse(result.getEnabled());
    }

}
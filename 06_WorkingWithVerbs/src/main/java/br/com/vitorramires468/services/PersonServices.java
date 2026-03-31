package br.com.vitorramires468.services;

import br.com.vitorramires468.model.Person;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public Person findById(String id){
        logger.info("Find one Person");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Vitor");
        person.setLastName("Souza");
        person.setAddress("Manaus - Amazonas - Brasil");
        person.setGender("Male");
        return person;
    }

    public List<Person> findAll(){

        logger.info("Find all");

        List<Person> persons = new ArrayList<>();

        for (int i=0; i<8;i++){
            Person person = mockPerson(i);
            persons.add(person);
        }

        return persons;
    }

    public Person create(Person person){
        logger.info("Creating one Person");
        return person;
    }

    public Person update(Person person){
        logger.info("Updating one Person");
        return person;
    }

    public void delete(String id){
        logger.info("Deleting one Person");
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("FirstName "+ i);
        person.setLastName("LastName "+ i);
        person.setAddress("Address "+i);
        person.setGender("Gender "+i);
        return person;
    }
}

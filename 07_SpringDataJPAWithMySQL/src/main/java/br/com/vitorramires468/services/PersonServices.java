package br.com.vitorramires468.services;

import br.com.vitorramires468.exceptions.ResourceNotFoundException;
import br.com.vitorramires468.model.Person;
import br.com.vitorramires468.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    PersonRepository personRepository;

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public Person findById(Long id){
        logger.info("Find one Person");

        return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));
    }

    public List<Person> findAll(){

        logger.info("Find all");

        return personRepository.findAll();
    }

    public Person create(Person person){
        logger.info("Creating one Person");
        return personRepository.save(person);
    }

    public Person update(Person person){
        logger.info("Updating one Person");

        Person entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return personRepository.save(entity);
    }

    public void delete(Long id){
        logger.info("Deleting one Person");
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        personRepository.delete(entity);
    }

}

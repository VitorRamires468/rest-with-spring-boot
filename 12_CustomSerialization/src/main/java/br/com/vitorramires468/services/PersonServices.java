package br.com.vitorramires468.services;

import br.com.vitorramires468.data.dto.PersonDTO;
import br.com.vitorramires468.exceptions.ResourceNotFoundException;
import static br.com.vitorramires468.mapper.ObjectMapper.parseListObjects;
import static br.com.vitorramires468.mapper.ObjectMapper.parseObject;
import br.com.vitorramires468.model.Person;
import br.com.vitorramires468.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    PersonRepository personRepository;

    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    public PersonDTO findById(Long id){
        logger.info("Find one Person");

        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        return parseObject(entity, PersonDTO.class);
    }

    public List<PersonDTO> findAll(){

        logger.info("Find all");

        return parseListObjects(personRepository.findAll(), PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person){
        logger.info("Creating one Person");

        var entity = parseObject(person, Person.class);

        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public PersonDTO update(PersonDTO person){
        logger.info("Updating one Person");

        Person entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(personRepository.save(entity), PersonDTO.class);
    }

    public void delete(Long id){
        logger.info("Deleting one Person");
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        personRepository.delete(entity);
    }

}

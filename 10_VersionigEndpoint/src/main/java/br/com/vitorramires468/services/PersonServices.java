package br.com.vitorramires468.services;

import br.com.vitorramires468.data.dto.v1.PersonDTOV1;
import br.com.vitorramires468.data.dto.v2.PersonDTOV2;
import br.com.vitorramires468.exceptions.ResourceNotFoundException;
import static br.com.vitorramires468.mapper.ObjectMapper.parseListObjects;
import static br.com.vitorramires468.mapper.ObjectMapper.parseObject;

import br.com.vitorramires468.mapper.custom.PersonMapper;
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

    @Autowired
    PersonMapper converter;

    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    public PersonDTOV1 findById(Long id){
        logger.info("Find one Person");

        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        return parseObject(entity, PersonDTOV1.class);
    }

    public List<PersonDTOV1> findAll(){

        logger.info("Find all");

        return parseListObjects(personRepository.findAll(), PersonDTOV1.class);
    }

    public PersonDTOV1 create(PersonDTOV1 person){
        logger.info("Creating one Person");

        var entity = parseObject(person, Person.class);

        return parseObject(personRepository.save(entity), PersonDTOV1.class);
    }

    public PersonDTOV2 createV2(PersonDTOV2 person){
        logger.info("Creating one Person V2!");

        var entity = converter.convertDTOToEntity(person);

        return converter.convertEntityToDTO(personRepository.save(entity));
    }

    public PersonDTOV1 update(PersonDTOV1 person){
        logger.info("Updating one Person");

        Person entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(personRepository.save(entity), PersonDTOV1.class);
    }

    public void delete(Long id){
        logger.info("Deleting one Person");
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        personRepository.delete(entity);
    }

}

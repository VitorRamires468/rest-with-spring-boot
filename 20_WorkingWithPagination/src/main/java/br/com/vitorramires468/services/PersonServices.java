package br.com.vitorramires468.services;

import br.com.vitorramires468.controllers.PersonController;
import br.com.vitorramires468.data.dto.PersonDTO;
import br.com.vitorramires468.exceptions.RequiredObjectIsNullException;
import br.com.vitorramires468.exceptions.ResourceNotFoundException;

import static br.com.vitorramires468.mapper.ObjectMapper.parseObject;
import br.com.vitorramires468.model.Person;
import br.com.vitorramires468.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class PersonServices {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PagedResourcesAssembler<PersonDTO> assembler;

    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    public PersonDTO findById(Long id){
        logger.info("Find one Person");

        var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        var dto = parseObject(entity, PersonDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable){

        logger.info("Find all");

        var people = personRepository.findAll(pageable);

        var peoplewithLinks = people.map(person ->{
            var dto =  parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        //Adiciona os links HATEOAS para a página;
        Link findAllLinks = linkTo(
                methodOn(PersonController.class)
                        .findAll(
                                pageable.getPageNumber(),
                                pageable.getPageSize(),
                                String.valueOf(pageable.getSort())))
                .withSelfRel();


        return assembler.toModel(peoplewithLinks, findAllLinks);
    }

    public PagedModel<EntityModel<PersonDTO>> findByName(String firstName, Pageable pageable){

        logger.info("Find People By Name");

        var people = personRepository.findPeopleByName(firstName, pageable);

        var peoplewithLinks = people.map(person ->{
            var dto =  parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        //Adiciona os links HATEOAS para a página;
        Link findAllLinks = linkTo(
                methodOn(PersonController.class)
                        .findAll(
                                pageable.getPageNumber(),
                                pageable.getPageSize(),
                                String.valueOf(pageable.getSort())))
                .withSelfRel();


        return assembler.toModel(peoplewithLinks, findAllLinks);
    }

    public PersonDTO create(PersonDTO person){
        logger.info("Creating one Person");

        if(person==null) throw new RequiredObjectIsNullException();

        var entity = parseObject(person, Person.class);

        var dto = parseObject(personRepository.save(entity), PersonDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTO update(PersonDTO person){
        logger.info("Updating one Person");

        if(person==null) throw new RequiredObjectIsNullException();

        Person entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = parseObject(personRepository.save(entity), PersonDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    @Transactional
    public PersonDTO disablePerson(Long id){
        logger.info("Disabling one Person");

        personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        personRepository.disablePerson(id);

        var entity =  personRepository.findById(id).get();

        var dto = parseObject(entity, PersonDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one Person");
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        personRepository.delete(entity);
    }

    private void addHateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).deleteById(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(PersonController.class).findAll(1,12, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));
    }

}

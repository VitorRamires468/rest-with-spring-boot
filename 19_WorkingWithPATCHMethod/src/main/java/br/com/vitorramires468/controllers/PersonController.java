package br.com.vitorramires468.controllers;

import br.com.vitorramires468.controllers.docs.PersonControllerDocs;
import br.com.vitorramires468.data.dto.PersonDTO;
import br.com.vitorramires468.services.PersonServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin//(origins = "http://localhost:8080") - apenas quem usar esse caminho vai poder acessar à API
@RestController
@RequestMapping("/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController implements PersonControllerDocs {

    @Autowired
    private PersonServices services;

    @CrossOrigin(origins = "http://localhost:8080")
    @Override
    @GetMapping(value = "/{id}",
    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public PersonDTO findById(@PathVariable("id") Long id){
        return services.findById(id);
    }

    @Override
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public List<PersonDTO> findAll(){
        return services.findAll();
    }

    //@CrossOrigin(origins = "http://localhost:8080") - Se formos fazer a autorização do CORS no cotroller, devemos aplicá-la no controller diretamente.
    @Override
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public PersonDTO create(@RequestBody PersonDTO person){
        return services.create(person);
    }

    @Override
    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public PersonDTO update(@RequestBody PersonDTO person){
        return services.update(person);
    }

    @Override
    @PatchMapping(value = {"/{id}"},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    public PersonDTO disablePerson(Long id) {
        return services.disablePerson(id);
    }


    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
        services.delete(id);
        return ResponseEntity.noContent().build();
    }

}

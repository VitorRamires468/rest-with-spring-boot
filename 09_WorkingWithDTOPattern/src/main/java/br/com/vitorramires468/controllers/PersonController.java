package br.com.vitorramires468.controllers;

import br.com.vitorramires468.data.dto.PersonDTO;
import br.com.vitorramires468.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices services;

    @GetMapping(value = "/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO findById(@PathVariable("id") Long id){
        return services.findById(id);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonDTO> findAll(){
        return services.findAll();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO create(@RequestBody PersonDTO person){
        return services.create(person);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO update(@RequestBody PersonDTO person){
        return services.update(person);
    }

    @DeleteMapping(
            value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
        services.delete(id);
        return ResponseEntity.noContent().build();
    }

}

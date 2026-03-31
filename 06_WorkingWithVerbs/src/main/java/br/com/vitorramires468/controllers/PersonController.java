package br.com.vitorramires468.controllers;

import br.com.vitorramires468.model.Person;
import br.com.vitorramires468.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonServices services;

    @RequestMapping(value = "/{id}",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById(@PathVariable("id") String id){
        return services.findById(id);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll(){
        return services.findAll();
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person create(@RequestBody Person person){
        return services.create(person);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@RequestBody Person person){
        return services.update(person);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE)
    public void deleteById(@PathVariable("id") String id){
        services.delete(id);
    }

}

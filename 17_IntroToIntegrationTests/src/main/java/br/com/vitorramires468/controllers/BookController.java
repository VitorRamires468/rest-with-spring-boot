package br.com.vitorramires468.controllers;


import br.com.vitorramires468.controllers.docs.BookControllerDocs;
import br.com.vitorramires468.data.dto.BookDTO;
import br.com.vitorramires468.services.BookServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/book/v1")
@Tag(name = "Book", description = "Endpoint for manangin book")
public class BookController implements BookControllerDocs {

    @Autowired
    BookServices services;

    @Override
    @GetMapping(value = "/{id}",
    produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE,
        MediaType.APPLICATION_YAML_VALUE})
    public BookDTO findById(@PathVariable Long id){
        return services.findById(id);
    }

    @Override
    @GetMapping(
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE})
    public List<BookDTO> findAll(){
        return services.findAll();
    }

    @Override
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    public BookDTO create(@RequestBody BookDTO bookDTO){
        return services.create(bookDTO);
    }

    @Override
    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    public BookDTO update(@RequestBody BookDTO bookDTO){
        return services.update(bookDTO);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        services.delete(id);
        return ResponseEntity.noContent().build();
    }

}

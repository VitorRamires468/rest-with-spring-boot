package br.com.vitorramires468.services;

import br.com.vitorramires468.controllers.BookController;
import br.com.vitorramires468.data.dto.BookDTO;
import br.com.vitorramires468.exceptions.RequiredObjectIsNullException;
import br.com.vitorramires468.exceptions.ResourceNotFoundException;
import br.com.vitorramires468.model.Book;
import br.com.vitorramires468.repository.BookRepository;
import static br.com.vitorramires468.mapper.ObjectMapper.parseListObjects;
import static br.com.vitorramires468.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BookServices {

    @Autowired
    BookRepository repository;

    @Autowired
    PagedResourcesAssembler<BookDTO> assembler;

    public BookDTO findById(Long id){
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID"));

        var dto = parseObject(entity, BookDTO.class);

        adddHateoasLinks(dto);

        return dto;
    }


    public PagedModel<EntityModel<BookDTO>> findAll(Pageable pageable){

        Page<Book> books = repository.findAll(pageable);

        var bookWithLinks = books.map(book -> {
            var bookDTO = parseObject(book, BookDTO.class);
            adddHateoasLinks(bookDTO);
            return bookDTO;
        });

        var pageWithLinks = linkTo(methodOn(BookController.class)
                .findAll(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        String.valueOf(pageable.getSort())))
                    .withSelfRel();

        return assembler.toModel(bookWithLinks, pageWithLinks);
    }

    public BookDTO create(BookDTO bookDTO){

        if(bookDTO == null) throw new RequiredObjectIsNullException();

        var entity = parseObject(bookDTO, Book.class);

        var dto =  parseObject(repository.save(entity), BookDTO.class);

        adddHateoasLinks(dto);

        return dto;

    }

    public BookDTO update(BookDTO bookDTO){

        if(bookDTO == null) throw new RequiredObjectIsNullException();

        var entity = repository.findById(bookDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID"));

        entity.setAuthor(bookDTO.getAuthor());
        entity.setLaunchDate(bookDTO.getLaunchDate());
        entity.setPrice(bookDTO.getPrice());
        entity.setTitle(bookDTO.getTitle());


        var dto =  parseObject(repository.save(entity), BookDTO.class);

        adddHateoasLinks(dto);

        return dto;

    }

    @Transactional
    public BookDTO disableBook(Long id){

        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID"));

        repository.disableBook(id);

        var disabledBook = repository.findById(id).get();

        var dto = parseObject(disabledBook, BookDTO.class);

        adddHateoasLinks(dto);

        return dto;

    }

    public void delete(Long id){
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No record found for this ID"));

        repository.delete(entity);
    }

    private static void adddHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).findAll(1,5, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BookController.class).disableBook(dto.getId())).withRel("disable").withType("PATCH"));
        dto.add(linkTo(methodOn(BookController.class).deleteById(dto.getId())).withRel("delete").withType("DELETE"));
    }
}

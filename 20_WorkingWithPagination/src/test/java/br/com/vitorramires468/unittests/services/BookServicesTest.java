package br.com.vitorramires468.unittests.services;

import br.com.vitorramires468.data.dto.BookDTO;
import br.com.vitorramires468.model.Book;
import br.com.vitorramires468.repository.BookRepository;
import br.com.vitorramires468.services.BookServices;
import br.com.vitorramires468.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    MockBook mockedBook;

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookServices services;

    @BeforeEach
    void setUp() {
        mockedBook = new MockBook();
    }

    @Test
    void findById() {

        Book book = mockedBook.mockBook(1);
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        var result = services.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                && link.getType().equals("GET")
                && link.getHref().contains("/book/v1/1")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getType().equals("GET")
                        && link.getHref().contains("/book/v1")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getType().equals("POST")
                        && link.getHref().contains("/book/v1")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getType().equals("PUT")
                        && link.getHref().contains("/book/v1")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getType().equals("DELETE")
                        && link.getHref().contains("/book/v1/1")));


        assertEquals("Author1", result.getAuthor());
        assertEquals("Title1", result.getTitle());
        assertEquals(1D, result.getPrice());
        assertEquals(new Date(2000,01,01), result.getLaunchDate());

    }

    /*@Test
    @Disabled("REASON: Still under development")
    void findAll() {
        List<Book> bookList = mockedBook.mockBookList();

        when(bookRepository.findAll()).thenReturn(bookList);

        var result = new ArrayList<>();//services.findAll();

        assertNotNull(result);
        assertEquals(5, result.size());

        var bookOne = result.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getId());
        assertNotNull(bookOne.getLinks());

        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getType().equals("GET")
                        && link.getHref().contains("/book/v1/1")));

        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getType().equals("GET")
                        && link.getHref().contains("/book/v1")));

        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getType().equals("POST")
                        && link.getHref().contains("/book/v1")));

        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getType().equals("PUT")
                        && link.getHref().contains("/book/v1")));

        assertTrue(bookOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getType().equals("DELETE")
                        && link.getHref().contains("/book/v1/1")));

        assertEquals("Author1", bookOne.getAuthor());
        assertEquals("Title1", bookOne.getTitle());
        assertEquals(1D, bookOne.getPrice());
        assertEquals(new Date(2000,01,01), bookOne.getLaunchDate());

        var bookTwo = result.get(2);

        assertNotNull(bookTwo);
        assertNotNull(bookTwo.getId());
        assertNotNull(bookTwo.getLinks());

        assertTrue(bookTwo.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getType().equals("GET")
                        && link.getHref().contains("/book/v1/2")));

        assertTrue(bookTwo.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getType().equals("GET")
                        && link.getHref().contains("/book/v1")));

        assertTrue(bookTwo.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getType().equals("POST")
                        && link.getHref().contains("/book/v1")));

        assertTrue(bookTwo.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getType().equals("PUT")
                        && link.getHref().contains("/book/v1")));

        assertTrue(bookTwo.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getType().equals("DELETE")
                        && link.getHref().contains("/book/v1/2")));

        assertEquals("Author2", bookTwo.getAuthor());
        assertEquals("Title2", bookTwo.getTitle());
        assertEquals(2D, bookTwo.getPrice());
        assertEquals(new Date(2000,01,01), bookTwo.getLaunchDate());

    }*/

    @Test
    void create() {

        Book book = mockedBook.mockBook(1);
        Book persisted = book;
        persisted.setId(1L);

        BookDTO bookDTO = mockedBook.mockBookDTO(1);

        when(bookRepository.save(book)).thenReturn(persisted);

        var result =  services.create(bookDTO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getType().equals("GET")
                        && link.getHref().contains("/book/v1/1")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getType().equals("GET")
                        && link.getHref().contains("/book/v1")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getType().equals("POST")
                        && link.getHref().contains("/book/v1")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getType().equals("PUT")
                        && link.getHref().contains("/book/v1")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getType().equals("DELETE")
                        && link.getHref().contains("/book/v1/1")));

        assertEquals("Author1", result.getAuthor());
        assertEquals("Title1", result.getTitle());
        assertEquals(1D, result.getPrice());
        assertEquals(new Date(2000,01,01), result.getLaunchDate());
    }

    @Test
    void update() {

        Book book = mockedBook.mockBook(1);
        Book persisted = book;
        persisted.setId(1L);

        BookDTO bookDTO = mockedBook.mockBookDTO(1);

        when(bookRepository.findById(bookDTO.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(persisted);

        var result =  services.update(bookDTO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getType().equals("GET")
                        && link.getHref().contains("/book/v1/1")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getType().equals("GET")
                        && link.getHref().contains("/book/v1")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getType().equals("POST")
                        && link.getHref().contains("/book/v1")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getType().equals("PUT")
                        && link.getHref().contains("/book/v1")));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getType().equals("DELETE")
                        && link.getHref().contains("/book/v1/1")));

        assertEquals("Author1", result.getAuthor());
        assertEquals("Title1", result.getTitle());
        assertEquals(1D, result.getPrice());
        assertEquals(new Date(2000,01,01), result.getLaunchDate());
    }

    @Test
    void delete() {
        Book book = mockedBook.mockBook(1);
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        services.delete(1L);

        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(bookRepository);

    }
}
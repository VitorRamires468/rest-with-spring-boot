package br.com.vitorramires468.unitetests.mapper.mocks;

import br.com.vitorramires468.data.dto.BookDTO;
import br.com.vitorramires468.model.Book;
import java.util.*;

import java.util.Date;

public class MockBook {

    public Book mockBook(){
        return mockBook(0);
    }

    public BookDTO mockBookDTO(){
        return mockBookDTO(0);
    }

    public List<Book> mockBookList(){
        List<Book> bookList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            bookList.add(mockBook(i));
        }

        return bookList;
    }

    public List<BookDTO> mockBookDTOList(){
        List<BookDTO> bookDTOList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            bookDTOList.add(mockBookDTO(i));
        }

        return bookDTOList;
    }

    public Book mockBook(Integer number){
        Book book = new Book();

        book.setId(number.longValue());
        book.setAuthor("Author"+number);
        book.setTitle("Title"+number);
        book.setPrice(number.doubleValue());
        book.setLaunchDate(new Date(2000,01,01));

        return book;
    }

    public BookDTO mockBookDTO(Integer number){
        BookDTO dto = new BookDTO();

        dto.setId(number.longValue());
        dto.setAuthor("Author"+number);
        dto.setTitle("Title"+number);
        dto.setPrice(number.doubleValue());
        dto.setLaunchDate(new Date(2000,01,01));

        return dto;
    }
}

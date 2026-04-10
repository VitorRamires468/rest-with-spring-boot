package br.com.vitorramires468.repository;

import br.com.vitorramires468.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}

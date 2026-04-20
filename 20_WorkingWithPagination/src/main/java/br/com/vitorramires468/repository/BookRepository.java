package br.com.vitorramires468.repository;

import br.com.vitorramires468.data.dto.BookDTO;
import br.com.vitorramires468.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Book b SET b.enabled = false WHERE b.id = :id")
    void disableBook(@Param("id") Long id);

}

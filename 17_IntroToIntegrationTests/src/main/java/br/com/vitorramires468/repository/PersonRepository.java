package br.com.vitorramires468.repository;

import br.com.vitorramires468.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}

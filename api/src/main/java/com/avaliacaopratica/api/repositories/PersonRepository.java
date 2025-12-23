package com.avaliacaopratica.api.repositories;

import com.avaliacaopratica.api.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByCpf(String cpf);
    boolean existsByCpf(String cpf);

}

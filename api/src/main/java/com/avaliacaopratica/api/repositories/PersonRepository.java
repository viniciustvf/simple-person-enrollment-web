package com.avaliacaopratica.api.repositories;

import com.avaliacaopratica.api.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByCpf(@Param("cpf") String cpf);
    List<Person> findAll();
    Page<Person> findAll(Pageable pageable);
    boolean existsByCpf(String cpf);
}

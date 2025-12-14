package com.avaliacaopratica.api.services;

import com.avaliacaopratica.api.dto.PersonRequestDTO;
import com.avaliacaopratica.api.dto.PersonResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {

    PersonResponseDTO createPerson(PersonRequestDTO request);

    PersonResponseDTO updatePerson(Integer id, PersonRequestDTO request);

    void deletePerson(Integer id);

    PersonResponseDTO findByCpf(String cpf);

    List<PersonResponseDTO> findAll();

    Page<PersonResponseDTO> findAllPaginated(Pageable pageable);

    boolean existsByCpf(String cpf);

}

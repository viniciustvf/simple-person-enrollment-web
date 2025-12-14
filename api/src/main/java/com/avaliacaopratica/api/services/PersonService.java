package com.avaliacaopratica.api.services;

import com.avaliacaopratica.api.dto.person.PersonRequestDTO;
import com.avaliacaopratica.api.dto.person.PersonResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {

    PersonResponseDTO createPerson(PersonRequestDTO request);
    PersonResponseDTO updatePerson(Integer id, PersonRequestDTO request);
    void deletePerson(Integer id);
    PersonResponseDTO findByCpf(String cpf);
    List<PersonResponseDTO> findAll();
    boolean existsByCpf(String cpf);
    void deleteByCpf(String cpf);

}

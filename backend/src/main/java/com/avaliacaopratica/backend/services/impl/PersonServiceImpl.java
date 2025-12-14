package com.avaliacaopratica.backend.services.impl;

import com.avaliacaopratica.backend.business.PersonBusiness;
import com.avaliacaopratica.backend.dto.person.PersonRequestDTO;
import com.avaliacaopratica.backend.dto.person.PersonResponseDTO;
import com.avaliacaopratica.backend.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public

class PersonServiceImpl implements PersonService {

    private final PersonBusiness personBusiness;

    @Override
    public PersonResponseDTO createPerson(PersonRequestDTO request) {
        return personBusiness.create(request);
    }

    @Override
    public PersonResponseDTO updatePerson(Integer id, PersonRequestDTO request) {
        return personBusiness.update(id, request);
    }

    @Override
    public void deletePerson(Integer id) {
        personBusiness.delete(id);
    }

    @Override
    public PersonResponseDTO findByCpf(String cpf) {
        return personBusiness.findByCpf(cpf);
    }

    @Override
    public List<PersonResponseDTO> findAll() {
        return personBusiness.findAll();
    }

    @Override
    public Page<PersonResponseDTO> findAllPaginated(Pageable pageable) {
        return personBusiness.findAllPaged(pageable);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return personBusiness.existsByCpf(cpf);
    }

    @Override
    public PersonResponseDTO integrate(Integer id) {
        return personBusiness.integrate(id);
    }
}
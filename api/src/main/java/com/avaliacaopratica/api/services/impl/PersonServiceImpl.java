package com.avaliacaopratica.api.services.impl;

import com.avaliacaopratica.api.business.PersonBusiness;
import com.avaliacaopratica.api.dto.person.PersonRequestDTO;
import com.avaliacaopratica.api.dto.person.PersonResponseDTO;
import com.avaliacaopratica.api.services.PersonService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

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
    public boolean existsByCpf(String cpf) {
        return personBusiness.existsByCpf(cpf);
    }

    @Override
    public void deleteByCpf(String cpf) {
        personBusiness.deleteByCpf(cpf);
    }
}
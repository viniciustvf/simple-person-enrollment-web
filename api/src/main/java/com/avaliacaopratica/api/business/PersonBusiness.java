package com.avaliacaopratica.api.business;

import com.avaliacaopratica.api.mapper.PersonMapper;
import com.avaliacaopratica.api.models.Person;
import com.avaliacaopratica.api.dto.person.PersonRequestDTO;
import com.avaliacaopratica.api.dto.person.PersonResponseDTO;
import com.avaliacaopratica.api.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class PersonBusiness {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonResponseDTO create(PersonRequestDTO request) {
        Person entity = personRepository.findByCpf(request.getCpf()).orElseGet(Person::new);
        entity.setName(request.getName());
        entity.setCpf(request.getCpf());
        entity.setEmail(request.getEmail());
        personRepository.save(entity);
        return personMapper.toResponse(entity);
    }

    public PersonResponseDTO update(Integer id, PersonRequestDTO request) {
        Person entity = findByIdOrThrow(id);
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setCpf(request.getCpf());
        personRepository.save(entity);
        return personMapper.toResponse(entity);
    }

    public void delete(Integer id) {
        Person entity = findByIdOrThrow(id);
        personRepository.delete(entity);
    }

    public void deleteByCpf(String cpf) {
        Person entity = findByCpfOrThrow(cpf);
        personRepository.delete(entity);
    }

    public PersonResponseDTO findByCpf(String cpf) {
        Person entity = findByCpfOrThrow(cpf);
        return personMapper.toResponse(entity);
    }

    public List<PersonResponseDTO> findAll() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toResponse)
                .toList();
    }

    public boolean existsByCpf(String cpf) {
        return personRepository.existsByCpf(cpf);
    }


    private Person findByIdOrThrow(Integer id) {
        return personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person not found"));
    }

    private Person findByCpfOrThrow(String cpf) {
        return personRepository.findByCpf(cpf).orElseThrow(() -> new EntityNotFoundException("Person not found"));
    }
}
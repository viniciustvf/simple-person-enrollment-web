package com.avaliacaopratica.api.business;

import com.avaliacaopratica.api.mapper.PersonMapper;
import com.avaliacaopratica.api.models.Person;
import com.avaliacaopratica.api.dto.PersonRequestDTO;
import com.avaliacaopratica.api.dto.PersonResponseDTO;
import com.avaliacaopratica.api.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class PersonBusiness {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonResponseDTO create(PersonRequestDTO request) {

        Person entity = personRepository.findByCpf(request.getCpf())
                .orElseGet(Person::new);

        entity.setName(request.getName());
        entity.setCpf(request.getCpf());
        entity.setEmail(request.getEmail());

        personRepository.save(entity);
        return personMapper.toResponse(entity);
    }

    public PersonResponseDTO update(Integer id, PersonRequestDTO request) {
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setCpf(request.getCpf());

        personRepository.save(entity);
        return personMapper.toResponse(entity);
    }

    public void delete(Integer id) {
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        personRepository.delete(entity);
    }

    public PersonResponseDTO findByCpf(String cpf) {
        Person entity = personRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        return personMapper.toResponse(entity);
    }

    public List<PersonResponseDTO> findAll() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::toResponse)
                .toList();
    }

    public Page<PersonResponseDTO> findAllPaged(Pageable pageable) {
        return personRepository.findAll(pageable)
                .map(personMapper::toResponse);
    }

    public boolean existsByCpf(String cpf) {
        return personRepository.existsByCpf(cpf);
    }
}
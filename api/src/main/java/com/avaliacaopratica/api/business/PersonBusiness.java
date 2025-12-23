package com.avaliacaopratica.api.business;

import com.avaliacaopratica.api.exceptions.BusinessException;
import com.avaliacaopratica.api.mapper.PersonMapper;
import com.avaliacaopratica.api.models.Person;
import com.avaliacaopratica.api.dto.person.PersonRequestDTO;
import com.avaliacaopratica.api.dto.person.PersonResponseDTO;
import com.avaliacaopratica.api.repositories.PersonRepository;
import com.avaliacaopratica.api.repositories.RegistrationRepository;
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
    private final RegistrationRepository registrationRepository;
    private final PersonMapper personMapper;

    public PersonResponseDTO create(PersonRequestDTO request) {
        Person entity = personRepository.findByCpf(request.getCpf()).orElseGet(() -> personMapper.toEntity(request));
        personMapper.toEntity(request, entity);
        personRepository.save(entity);
        return personMapper.toResponse(entity);
    }

    public PersonResponseDTO update(Integer id, PersonRequestDTO request) {
        Person entity = findByIdOrThrow(id);
        personMapper.toEntity(request, entity);
        personRepository.save(entity);
        return personMapper.toResponse(entity);
    }

    public void delete(Integer id) {
        Person entity = findByIdOrThrow(id);
        validarSePodeExcluir(entity.getCpf());
        personRepository.delete(entity);
    }

    public void deleteByCpf(String cpf) {
        Person entity = findByCpfOrThrow(cpf);
        validarSePodeExcluir(cpf);
        personRepository.delete(entity);
    }

    public PersonResponseDTO findByCpf(String cpf) {
        return personMapper.toResponse(findByCpfOrThrow(cpf));
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

    private void validarSePodeExcluir(String cpf) {
        if (registrationRepository.existsByCpf(cpf)) {
            throw new BusinessException("Não é possível remover a pessoa: ela possui inscrição em curso.");
        }
    }
}
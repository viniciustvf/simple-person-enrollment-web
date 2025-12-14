package com.avaliacaopratica.backend.business;

import com.avaliacaopratica.backend.dto.person.PersonRequestDTO;
import com.avaliacaopratica.backend.dto.person.PersonResponseDTO;
import com.avaliacaopratica.backend.enums.IntegrationAction;
import com.avaliacaopratica.backend.enums.IntegrationStatus;
import com.avaliacaopratica.backend.exceptions.IntegrityViolation;
import com.avaliacaopratica.backend.mapper.PersonMapper;
import com.avaliacaopratica.backend.messaging.producer.PersonProducer;
import com.avaliacaopratica.backend.models.Address;
import com.avaliacaopratica.backend.models.Person;
import com.avaliacaopratica.backend.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonBusiness {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final PersonProducer personProducer;

    @Transactional
    public PersonResponseDTO create(PersonRequestDTO request) {
        validate(request);
        Person entity = personMapper.toEntity(request);
        personRepository.save(entity);
        return personMapper.toResponse(entity);
    }

    @Transactional
    public PersonResponseDTO update(Integer id, PersonRequestDTO request) {
        Person entity = findByIdOrThrow(id);

        updateBasicData(entity, request);
        updateAddress(entity, request);

        personRepository.save(entity);
        return personMapper.toResponse(entity);
    }

    @Transactional
    public void delete(Integer id) {
        Person entity = findByIdOrThrow(id);
        personRepository.delete(entity);
        personProducer.enviarParaFila(personMapper.toIntegrate(entity, IntegrationAction.DELETE));
    }

    @Transactional
    public PersonResponseDTO integrate(Integer id) {
        Person entity = findByIdOrThrow(id);
        entity.setIntegrationStatus(IntegrationStatus.PENDENTE);
        personRepository.save(entity);
        personProducer.enviarParaFila(personMapper.toIntegrate(entity, IntegrationAction.CREATE_UPDATE));
        return personMapper.toResponse(entity);
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

    public Page<PersonResponseDTO> findAllPaged(Pageable pageable) {
        return personRepository.findAll(pageable).map(personMapper::toResponse);
    }

    public boolean existsByCpf(String cpf) {
        return personRepository.existsByCpf(cpf);
    }

    public void validate(PersonRequestDTO request) {
        if (personRepository.existsByCpf(request.getCpf())) {
            throw new IntegrityViolation("CPF já cadastrado.");
        }
    }

    private void updateBasicData(Person entity, PersonRequestDTO request) {
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setBirth(request.getBirth());
        entity.setCpf(request.getCpf());
    }

    private void updateAddress(Person entity, PersonRequestDTO request) {
        if (request.getAddress() == null) {
            return;
        }

        Address address = getOrCreateAddress(entity);

        address.setCep(request.getAddress().getCep());
        address.setRua(request.getAddress().getRua());
        address.setNumero(request.getAddress().getNumero());
        address.setCidade(request.getAddress().getCidade());
        address.setUf(request.getAddress().getUf());
    }

    private Address getOrCreateAddress(Person entity) {
        if (entity.getAddress() == null) {
            Address address = new Address();
            address.setPerson(entity);
            entity.setAddress(address);
        }
        return entity.getAddress();
    }

    private Person findByIdOrThrow(Integer id) {
        return personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
    }

    private Person findByCpfOrThrow(String cpf) {
        return personRepository.findByCpf(cpf).orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
    }
}

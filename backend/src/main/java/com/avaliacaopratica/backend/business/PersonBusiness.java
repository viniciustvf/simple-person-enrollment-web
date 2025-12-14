package com.avaliacaopratica.backend.business;

import com.avaliacaopratica.backend.dto.PersonRequestDTO;
import com.avaliacaopratica.backend.dto.PersonResponseDTO;
import com.avaliacaopratica.backend.enums.IntegrationStatus;
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
@Transactional
public class PersonBusiness {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final PersonProducer personProducer;

    public PersonResponseDTO create(PersonRequestDTO request) {
        if (personRepository.existsByCpf(request.getCpf())) {
            throw new IllegalArgumentException("CPF already registered");
        }

        Person entity = personMapper.toEntity(request);

        personProducer.enviarParaFila(personMapper.toIntegrate(entity));

        personRepository.save(entity);
        return personMapper.toResponse(entity);
    }

    @Transactional
    public PersonResponseDTO update(Integer id, PersonRequestDTO request) {
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setBirth(request.getBirth());

        entity.setCpf(request.getCpf());

        if (request.getAddress() != null) {
            Address address = entity.getAddress();

            if (address == null) {
                address = new Address();
                address.setPerson(entity);
                entity.setAddress(address);
            }

            address.setCep(request.getAddress().getCep());
            address.setRua(request.getAddress().getRua());
            address.setNumero(request.getAddress().getNumero());
            address.setCidade(request.getAddress().getCidade());
            address.setUf(request.getAddress().getUf());
        }

        personRepository.save(entity);
        personProducer.enviarParaFila(personMapper.toIntegrate(entity));

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

    public PersonResponseDTO reintegrate(Integer id) {
        Person entity = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person not found"));

        personProducer.enviarParaFila(personMapper.toIntegrate(entity));

        entity.setIntegrationStatus(IntegrationStatus.PENDENTE);
        personRepository.save(entity);

        return personMapper.toResponse(entity);
    }
}

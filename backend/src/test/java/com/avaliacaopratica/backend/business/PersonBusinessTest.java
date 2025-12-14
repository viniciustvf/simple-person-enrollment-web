package com.avaliacaopratica.backend.business;

import com.avaliacaopratica.backend.dto.person.PersonRequestDTO;
import com.avaliacaopratica.backend.dto.person.PersonResponseDTO;
import com.avaliacaopratica.backend.enums.IntegrationStatus;
import com.avaliacaopratica.backend.exceptions.IntegrityViolation;
import com.avaliacaopratica.backend.mapper.PersonMapper;
import com.avaliacaopratica.backend.messaging.producer.PersonProducer;
import com.avaliacaopratica.backend.models.Person;
import com.avaliacaopratica.backend.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonBusinessTest {

    @InjectMocks
    private PersonBusiness personBusiness;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private PersonProducer personProducer;

    private PersonRequestDTO request;
    private Person entity;
    private PersonResponseDTO response;

    @BeforeEach
    void setup() {
        request = new PersonRequestDTO();
        request.setCpf("12345678909");
        request.setName("João");
        request.setEmail("joao@email.com");

        entity = new Person();
        entity.setIdPerson(1);
        entity.setCpf(request.getCpf());

        response = new PersonResponseDTO();
    }

    @Test
    void deveCriarPessoaComSucesso() {
        when(personRepository.existsByCpf(request.getCpf())).thenReturn(false);
        when(personMapper.toEntity(request)).thenReturn(entity);
        when(personMapper.toResponse(entity)).thenReturn(response);

        PersonResponseDTO result = personBusiness.create(request);

        verify(personRepository).save(entity);
        assertEquals(response, result);
    }

    @Test
    void deveLancarErroQuandoCpfJaExisteAoCriar() {
        when(personRepository.existsByCpf(request.getCpf())).thenReturn(true);

        assertThrows(IntegrityViolation.class, () -> personBusiness.create(request));

        verify(personRepository, never()).save(any());
    }

    @Test
    void deveAtualizarPessoaComSucesso() {
        when(personRepository.findById(1)).thenReturn(Optional.of(entity));
        when(personMapper.toResponse(entity)).thenReturn(response);

        PersonResponseDTO result = personBusiness.update(1, request);

        verify(personRepository).save(entity);
        assertEquals(response, result);
    }

    @Test
    void deveLancarErroQuandoAtualizarPessoaInexistente() {
        when(personRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> personBusiness.update(1, request));
    }

    @Test
    void deveDeletarPessoaEEnviarParaFila() {
        when(personRepository.findById(1)).thenReturn(Optional.of(entity));

        personBusiness.delete(1);

        verify(personRepository).delete(entity);
        verify(personProducer).enviarParaFila(any());
    }

    @Test
    void deveIntegrarPessoaComSucesso() {
        when(personRepository.findById(1)).thenReturn(Optional.of(entity));
        when(personMapper.toResponse(entity)).thenReturn(response);

        PersonResponseDTO result = personBusiness.integrate(1);

        assertEquals(IntegrationStatus.PENDENTE, entity.getIntegrationStatus());
        verify(personProducer).enviarParaFila(any());
        verify(personRepository).save(entity);
        assertEquals(response, result);
    }

    @Test
    void deveBuscarPessoaPorCpf() {
        when(personRepository.findByCpf(request.getCpf())).thenReturn(Optional.of(entity));
        when(personMapper.toResponse(entity)).thenReturn(response);

        PersonResponseDTO result = personBusiness.findByCpf(request.getCpf());

        assertEquals(response, result);
    }

    @Test
    void deveLancarErroQuandoCpfNaoEncontrado() {
        when(personRepository.findByCpf(request.getCpf())).thenReturn(Optional.empty());

        String cpf = request.getCpf();

        assertThrows(EntityNotFoundException.class, () -> personBusiness.findByCpf(cpf));
    }

}

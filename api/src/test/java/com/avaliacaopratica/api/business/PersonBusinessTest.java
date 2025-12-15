package com.avaliacaopratica.api.business;

import com.avaliacaopratica.api.dto.person.PersonRequestDTO;
import com.avaliacaopratica.api.dto.person.PersonResponseDTO;
import com.avaliacaopratica.api.exceptions.BusinessException;
import com.avaliacaopratica.api.mapper.PersonMapper;
import com.avaliacaopratica.api.models.Person;
import com.avaliacaopratica.api.repositories.PersonRepository;
import com.avaliacaopratica.api.repositories.RegistrationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonBusinessTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonBusiness personBusiness;

    @Test
    void deveCriarNovaPessoaQuandoCpfNaoExiste() {
        PersonRequestDTO request = mock(PersonRequestDTO.class);
        when(request.getName()).thenReturn("Vinicius");
        when(request.getCpf()).thenReturn("123");
        when(request.getEmail()).thenReturn("vinicius@email.com");

        PersonResponseDTO response = mock(PersonResponseDTO.class);

        when(personRepository.findByCpf("123")).thenReturn(Optional.empty());
        when(personMapper.toResponse(any(Person.class))).thenReturn(response);

        PersonResponseDTO result = personBusiness.create(request);

        assertSame(response, result);

        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(personRepository).save(captor.capture());

        Person saved = captor.getValue();
        assertEquals("Vinicius", saved.getName());
        assertEquals("123", saved.getCpf());
        assertEquals("vinicius@email.com", saved.getEmail());

        verify(personMapper).toResponse(saved);
    }

    @Test
    void deveAtualizarPessoaExistenteQuandoCpfJaExisteNoCreate() {
        Person existing = new Person();
        existing.setName("Antigo");
        existing.setCpf("123");
        existing.setEmail("antigo@email.com");

        PersonRequestDTO request = mock(PersonRequestDTO.class);
        when(request.getName()).thenReturn("Novo");
        when(request.getCpf()).thenReturn("123");
        when(request.getEmail()).thenReturn("novo@email.com");

        PersonResponseDTO response = mock(PersonResponseDTO.class);

        when(personRepository.findByCpf("123")).thenReturn(Optional.of(existing));
        when(personMapper.toResponse(existing)).thenReturn(response);

        PersonResponseDTO result = personBusiness.create(request);

        assertSame(response, result);
        assertEquals("Novo", existing.getName());
        assertEquals("novo@email.com", existing.getEmail());

        verify(personRepository).save(existing);
        verify(personMapper).toResponse(existing);
    }

    @Test
    void deveAtualizarPessoaPorIdQuandoEncontrada() {
        Integer id = 1;
        Person entity = new Person();

        PersonRequestDTO request = mock(PersonRequestDTO.class);
        when(request.getName()).thenReturn("Vinicius");
        when(request.getEmail()).thenReturn("vinicius@email.com");
        when(request.getCpf()).thenReturn("999");

        PersonResponseDTO response = mock(PersonResponseDTO.class);

        when(personRepository.findById(id)).thenReturn(Optional.of(entity));
        when(personMapper.toResponse(entity)).thenReturn(response);

        PersonResponseDTO result = personBusiness.update(id, request);

        assertSame(response, result);
        assertEquals("Vinicius", entity.getName());
        assertEquals("vinicius@email.com", entity.getEmail());
        assertEquals("999", entity.getCpf());
        verify(personRepository).save(entity);
    }

    @Test
    void deveLancarExcecaoAoAtualizarPessoaInexistente() {
        Integer id = 99;
        when(personRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> personBusiness.update(id, mock(PersonRequestDTO.class)));
        verify(personRepository, never()).save(any());
    }

    @Test
    void deveDeletarPessoaPorIdQuandoEncontrada() {
        Integer id = 10;
        Person entity = new Person();

        when(personRepository.findById(id)).thenReturn(Optional.of(entity));

        personBusiness.delete(id);

        verify(personRepository).delete(entity);
    }

    @Test
    void deveDeletarPessoaPorCpfQuandoEncontrada() {
        Person entity = new Person();
        when(personRepository.findByCpf("123")).thenReturn(Optional.of(entity));

        personBusiness.deleteByCpf("123");

        verify(personRepository).delete(entity);
    }

    @Test
    void deveBuscarPessoaPorCpf() {
        Person entity = new Person();
        PersonResponseDTO response = mock(PersonResponseDTO.class);

        when(personRepository.findByCpf("123")).thenReturn(Optional.of(entity));
        when(personMapper.toResponse(entity)).thenReturn(response);

        PersonResponseDTO result = personBusiness.findByCpf("123");

        assertSame(response, result);
        verify(personMapper).toResponse(entity);
    }

    @Test
    void deveListarTodasAsPessoas() {
        Person p1 = new Person();
        Person p2 = new Person();

        PersonResponseDTO r1 = mock(PersonResponseDTO.class);
        PersonResponseDTO r2 = mock(PersonResponseDTO.class);

        when(personRepository.findAll()).thenReturn(List.of(p1, p2));
        when(personMapper.toResponse(p1)).thenReturn(r1);
        when(personMapper.toResponse(p2)).thenReturn(r2);

        List<PersonResponseDTO> result = personBusiness.findAll();

        assertEquals(List.of(r1, r2), result);
        verify(personRepository).findAll();
    }

    @Test
    void deveRetornarTrueQuandoCpfExistir() {
        when(personRepository.existsByCpf("123")).thenReturn(true);

        assertTrue(personBusiness.existsByCpf("123"));
        verify(personRepository).existsByCpf("123");
    }

    @Test
    void deveLancarExcecaoAoDeletarPessoaQuandoPossuiInscricao() {
        Integer id = 1;

        Person entity = new Person();
        entity.setCpf("123");

        when(personRepository.findById(id)).thenReturn(Optional.of(entity));
        when(registrationRepository.existsByCpf("123")).thenReturn(true);

        assertThrows(BusinessException.class, () -> personBusiness.delete(id));

        verify(personRepository, never()).delete(any());
    }

    @Test
    void deveDeletarPessoaQuandoNaoPossuiInscricao() {
        Integer id = 1;

        Person entity = new Person();
        entity.setCpf("123");

        when(personRepository.findById(id)).thenReturn(Optional.of(entity));
        when(registrationRepository.existsByCpf("123")).thenReturn(false);

        personBusiness.delete(id);

        verify(personRepository).delete(entity);
    }
}

package com.avaliacaopratica.api.controller;

import com.avaliacaopratica.api.dto.person.PersonRequestDTO;
import com.avaliacaopratica.api.dto.person.PersonResponseDTO;
import com.avaliacaopratica.api.services.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping
    public ResponseEntity<PersonResponseDTO> create(
            @Valid @RequestBody PersonRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personService.createPerson(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody PersonRequestDTO request
    ) {
        return ResponseEntity.ok(personService.updatePerson(id, request));
    }

    @GetMapping
    public ResponseEntity<List<PersonResponseDTO>> findAll() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<PersonResponseDTO> findByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(personService.findByCpf(cpf));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<Void> deleteByCpf(@PathVariable String cpf) {
        personService.deleteByCpf(cpf);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/cpf/{cpf}")
    public ResponseEntity<Boolean> existsByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(personService.existsByCpf(cpf));
    }
}

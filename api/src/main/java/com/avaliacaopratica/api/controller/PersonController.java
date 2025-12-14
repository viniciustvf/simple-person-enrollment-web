package com.avaliacaopratica.api.controller;

import com.avaliacaopratica.api.dto.PersonRequestDTO;
import com.avaliacaopratica.api.dto.PersonResponseDTO;
import com.avaliacaopratica.api.services.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
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

    @GetMapping("/paged")
    public ResponseEntity<Page<PersonResponseDTO>> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(personService.findAllPaginated(pageable));
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

    @GetMapping("/exists/cpf/{cpf}")
    public ResponseEntity<Boolean> existsByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(personService.existsByCpf(cpf));
    }
}

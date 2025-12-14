package com.avaliacaopratica.api.controller;

import com.avaliacaopratica.api.dto.InscritoResponseDTO;
import com.avaliacaopratica.api.dto.RegistrationRequestDTO;
import com.avaliacaopratica.api.dto.RegistrationResponseDTO;
import com.avaliacaopratica.api.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/inscricao")
    public ResponseEntity<RegistrationResponseDTO> create(
            @Valid @RequestBody RegistrationRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registrationService.createRegistration(request));
    }

    @GetMapping("/inscritos/{idCurso}")
    public ResponseEntity<List<InscritoResponseDTO>> getInscritosPorCurso(
            @PathVariable Integer idCurso
    ) {
        return ResponseEntity.ok(
                registrationService.findInscritosByCurso(idCurso)
        );
    }

    @PostMapping("/finalizar-inscricao/{idCurso}")
    public ResponseEntity<String> finalizarInscricoes(
            @PathVariable Integer idCurso
    ) {
        registrationService.finalizarInscricoes(idCurso);
        return ResponseEntity.ok("Finalização das inscrições está em andamento.");
    }

    @GetMapping("/inscritos-finalizados/{idCurso}")
    public ResponseEntity<List<InscritoResponseDTO>> getInscritosFinalizadosPorCurso(
            @PathVariable Integer idCurso
    ) {
        return ResponseEntity.ok(
                registrationService.findInscritosFinalizadosByCurso(idCurso)
        );
    }
}
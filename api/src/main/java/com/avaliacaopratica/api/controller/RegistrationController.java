package com.avaliacaopratica.api.controller;

import com.avaliacaopratica.api.dto.registration.FinishRegistrationRequestDTO;
import com.avaliacaopratica.api.dto.registration.RegisteredResponseDTO;
import com.avaliacaopratica.api.dto.registration.RegistrationRequestDTO;
import com.avaliacaopratica.api.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/inscricao")
    public ResponseEntity<String> create(
            @Valid @RequestBody RegistrationRequestDTO request
    ) {
        registrationService.createRegistration(request);
        return ResponseEntity.ok("Inscrição realizada com sucesso.");
    }

    @PostMapping("/finalizar-inscricao")
    public ResponseEntity<String> finalizarInscricoes(
            @Valid @RequestBody FinishRegistrationRequestDTO request
    ) {
        registrationService.finalizarInscricoes(request);
        return ResponseEntity.ok("Finalização das inscrições está em andamento.");
    }

    @GetMapping("/inscritos/{idCurso}")
    public ResponseEntity<List<RegisteredResponseDTO>> getInscritosPorCurso(
            @PathVariable Integer idCurso
    ) {
        return ResponseEntity.ok(registrationService.findInscritosByCurso(idCurso));
    }

    @GetMapping("/inscritos-finalizados/{idCurso}")
    public ResponseEntity<List<RegisteredResponseDTO>> getInscritosFinalizadosPorCurso(
            @PathVariable Integer idCurso
    ) {
        return ResponseEntity.ok(registrationService.findInscritosFinalizadosByCurso(idCurso));
    }
}
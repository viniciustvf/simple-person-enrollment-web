package com.avaliacaopratica.api.services;

import com.avaliacaopratica.api.dto.registration.FinishRegistrationRequestDTO;
import com.avaliacaopratica.api.dto.registration.RegisteredResponseDTO;
import com.avaliacaopratica.api.dto.registration.RegistrationRequestDTO;
import com.avaliacaopratica.api.dto.registration.RegistrationResponseDTO;

import java.util.List;

public interface RegistrationService {

    void createRegistration(RegistrationRequestDTO request);
    List<RegisteredResponseDTO> findInscritosByCurso(Integer idCurso);
    List<RegisteredResponseDTO> findInscritosFinalizadosByCurso(Integer idCurso);
    void finalizarInscricoes(FinishRegistrationRequestDTO request);

}

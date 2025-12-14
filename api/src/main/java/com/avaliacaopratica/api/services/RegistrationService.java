package com.avaliacaopratica.api.services;

import com.avaliacaopratica.api.dto.InscritoResponseDTO;
import com.avaliacaopratica.api.dto.RegistrationRequestDTO;
import com.avaliacaopratica.api.dto.RegistrationResponseDTO;

import java.util.List;

public interface RegistrationService {

    RegistrationResponseDTO createRegistration(RegistrationRequestDTO request);

    List<InscritoResponseDTO> findInscritosByCurso(Integer idCurso);

    List<InscritoResponseDTO> findInscritosFinalizadosByCurso(Integer idCurso);

    void finalizarInscricoes(Integer idCurso);

}

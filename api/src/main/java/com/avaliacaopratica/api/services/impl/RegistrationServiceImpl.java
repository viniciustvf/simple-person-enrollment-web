package com.avaliacaopratica.api.services.impl;

import com.avaliacaopratica.api.business.RegistrationBusiness;
import com.avaliacaopratica.api.dto.InscritoResponseDTO;
import com.avaliacaopratica.api.dto.RegistrationRequestDTO;
import com.avaliacaopratica.api.dto.RegistrationResponseDTO;
import com.avaliacaopratica.api.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationBusiness registrationBusiness;

    @Override
    public RegistrationResponseDTO createRegistration(RegistrationRequestDTO request) {
        return registrationBusiness.create(request);
    }

    @Override
    public List<InscritoResponseDTO> findInscritosByCurso(Integer idCurso) {
        return registrationBusiness.findInscritosByCurso(idCurso);
    }

    @Override
    public List<InscritoResponseDTO> findInscritosFinalizadosByCurso(Integer idCurso) {
        return registrationBusiness.findInscritosFinalizadosByCurso(idCurso);
    }

    @Override
    public void finalizarInscricoes(Integer idCurso) {
        registrationBusiness.enfileirarFinalizacao(idCurso);
    }
}

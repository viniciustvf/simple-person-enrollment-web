package com.avaliacaopratica.api.services.impl;

import com.avaliacaopratica.api.business.RegistrationBusiness;
import com.avaliacaopratica.api.dto.registration.RegisteredResponseDTO;
import com.avaliacaopratica.api.dto.registration.RegistrationRequestDTO;
import com.avaliacaopratica.api.dto.registration.RegistrationResponseDTO;
import com.avaliacaopratica.api.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationBusiness registrationBusiness;

    @Override
    public void createRegistration(RegistrationRequestDTO request) {
        registrationBusiness.create(request);
    }

    @Override
    public List<RegisteredResponseDTO> findInscritosByCurso(Integer idCurso) {
        return registrationBusiness.findInscritosByCurso(idCurso);
    }

    @Override
    public List<RegisteredResponseDTO> findInscritosFinalizadosByCurso(Integer idCurso) {
        return registrationBusiness.findInscritosFinalizadosByCurso(idCurso);
    }

    @Override
    public void finalizarInscricoes(Integer idCurso) {
        registrationBusiness.enfileirarFinalizacao(idCurso);
    }
}

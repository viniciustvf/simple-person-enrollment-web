import {
  RegistrationRequestDTO,
} from "../../models/registration/RegistrationDTO";
import { InscricaoDTO } from "../../models/registration/InscricaoDTO";
import { InscritoResponseDTO } from "../../models/registration/InscritoResponseDTO";
import { api } from "../../api/api";

const BASE_PATH = "/v1/registrations";

export async function createRegistration(
  payload: RegistrationRequestDTO
): Promise<string> {
  const { data } = await api.post<string>(
    `${BASE_PATH}/inscricao`,
    payload
  );
  return data;
}

export async function findAllRegistrations(): Promise<InscricaoDTO[]> {
  const { data } = await api.get<InscricaoDTO[]>(BASE_PATH);
  return data;
}

export async function findInscritosByCurso(
  idCurso: number
): Promise<InscritoResponseDTO[]> {
  const { data } = await api.get<InscritoResponseDTO[]>(
    `${BASE_PATH}/inscritos/${idCurso}`
  );
  console.log(data);
  return data;
}

export async function finalizarInscricoes(
  idCurso: number
): Promise<string> {
  const { data } = await api.post<string>(
    `${BASE_PATH}/finalizar-inscricao/${idCurso}`
  );
  return data;
}

export async function findInscritosFinalizadosByCurso(
  idCurso: number
): Promise<InscritoResponseDTO[]> {
  const { data } = await api.get<InscritoResponseDTO[]>(
    `${BASE_PATH}/inscritos-finalizados/${idCurso}`
  );
  return data;
}
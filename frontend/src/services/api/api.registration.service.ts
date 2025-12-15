import { api } from "../../api/api";
import { InscricaoDTO } from "../../models/registration/InscricaoDTO";
import { InscritoResponseDTO } from "../../models/registration/InscritoResponseDTO";
import { RegistrationRequestDTO } from "../../models/registration/RegistrationDTO";

const BASE_PATH = "/v1/registrations";

export async function createRegistration(
  payload: RegistrationRequestDTO
): Promise<string> {
  const { data } = await api.post<string>(`${BASE_PATH}/inscricao`, payload);
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
  return data;
}

export async function finalizarInscricoes(idCurso: number): Promise<string> {
  const { data } = await api.post<string>(
    `${BASE_PATH}/finalizar-inscricao`,
    idCurso,
    { headers: { "Content-Type": "application/json" } }
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
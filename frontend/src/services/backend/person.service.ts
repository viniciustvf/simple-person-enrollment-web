import { backend } from "../../api/backend";
import { PageResponse } from "../../models/PageResponse";
import { PersonDTO } from "../../models/person/PersonDTO";

const BASE_PATH = "/v1/person";

export async function findAllPersons(): Promise<PersonDTO[]> {
  const { data } = await backend.get<PersonDTO[]>(BASE_PATH);
  return data;
}

export async function createPerson(payload: unknown): Promise<PersonDTO> {
  const { data } = await backend.post<PersonDTO>(BASE_PATH, payload);
  return data;
}

export async function updatePerson(id: number, pessoa: PersonDTO): Promise<PersonDTO> {
  const { data } = await backend.put<PersonDTO>(`${BASE_PATH}/${id}`, pessoa);
  return data;
}

export async function deletePerson(id: number): Promise<void> {
  await backend.delete(`${BASE_PATH}/${id}`);
}

export async function existsByCpf(cpf: string): Promise<boolean> {
  const cleanCpf = cpf.replace(/\D/g, "");

  const { data } = await backend.get<boolean>(`${BASE_PATH}/exists/cpf/${cleanCpf}`);

  return data;
}

export async function integratePerson(id: number): Promise<PersonDTO> {
  const { data } = await backend.post<PersonDTO>(`${BASE_PATH}/${id}/integrate`);
  return data;
}

export async function findAllPersonsPaged(
  page: number,
  size: number
): Promise<PageResponse<PersonDTO>> {
  const { data } = await backend.get<PageResponse<PersonDTO>>(`${BASE_PATH}/paged`, {
    params: { page, size },
  });
  return data;
}
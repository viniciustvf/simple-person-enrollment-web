import { api } from "../../api/api";
import { PersonApiDTO } from "../../models/PersonApiDTO";

const BASE_PATH = "/person";

export async function findAllPersonsApi(): Promise<PersonApiDTO[]> {
  const { data } = await api.get<PersonApiDTO[]>(BASE_PATH);
  return data;
}

export async function findPersonByCpfApi(
  cpf: string
): Promise<PersonApiDTO> {
  const cleanCpf = cpf.replace(/\D/g, "");

  const { data } = await api.get<PersonApiDTO>(
    `${BASE_PATH}/cpf/${cleanCpf}`
  );

  return data;
}

import axios from "axios";
import { ViaCepResponseModelDTO } from "../../models/ViaCepResponseModelDTO";

const externalApi = axios.create({
  baseURL: "https://viacep.com.br/ws/",
});

export async function fetchAddressByCep(
  cep: string
): Promise<ViaCepResponseModelDTO | null> {
  const cleanCep = cep.replace(/\D/g, "");

  if (cleanCep.length !== 8) {
    return null;
  }

  try {
    const { data } = await externalApi.get<ViaCepResponseModelDTO>(
      `${cleanCep}/json/`
    );

    if (data.erro) {
      return null;
    }

    return data;
  } catch {
    return null;
  }
}
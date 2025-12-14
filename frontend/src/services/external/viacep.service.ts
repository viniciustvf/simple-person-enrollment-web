import axios from "axios";
import { ViaCepResponseModel } from "../../models/ViaCepResponseModel";

const externalApi = axios.create({
  baseURL: "https://viacep.com.br/ws/",
});

export async function fetchAddressByCep(
  cep: string
): Promise<ViaCepResponseModel | null> {
  const cleanCep = cep.replace(/\D/g, "");

  if (cleanCep.length !== 8) {
    return null;
  }

  try {
    const { data } = await externalApi.get<ViaCepResponseModel>(
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
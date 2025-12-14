import { AddressDTO } from "./AddressDTO";

export interface PersonDTO {
  idPerson?: number;
  name: string;
  birth: string | null;
  cpf: string;
  email: string;
  integrationStatus: string;
  address: AddressDTO;
}
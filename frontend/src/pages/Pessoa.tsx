import { useEffect, useState } from "react";
import PessoaForm from "../components/pessoa/PessoaForm";
import PessoaList from "../components/pessoa/PessoaList";
import { PersonDTO } from "../models/PersonDTO";
import { Typography } from "@mui/material";
import {
  createPerson,
  deletePerson,
  findAllPersons,
  reintegratePerson,
  updatePerson,
} from "../services/backend/person.service";
import { useToast } from "../hooks/useToast";

export default function Pessoa() {
  const { toastError, toastSuccess } = useToast();

  const [pessoas, setPessoas] = useState<PersonDTO[]>([]);
  const [pessoaEditando, setPessoaEditando] = useState<PersonDTO | null>(null);

  useEffect(() => {
    loadPersons();
  }, []);

  async function loadPersons() {
    const data = await findAllPersons();
    console.log(data);
    setPessoas(data);
  }

  async function handleAddPessoa(pessoa: PersonDTO) {
    console.log(pessoa);
    if (pessoa.idPerson) {
      const updated = await updatePerson(pessoa.idPerson, pessoa);
      setPessoas((prev) =>
        prev.map((p) => (p.idPerson === updated.idPerson ? updated : p))
      );
      setPessoaEditando(null);
    } else {
      const created = await createPerson(pessoa);
      setPessoas((prev) => [...prev, created]);
    }
  }

  function handleEditPessoa(pessoa: PersonDTO) {
    setPessoaEditando(pessoa);
  }

  async function handleDeletePessoa(index: number) {
    const pessoa = pessoas[index];
    if (!pessoa.idPerson) return;

    await deletePerson(pessoa.idPerson);
    setPessoas((prev) => prev.filter((_, i) => i !== index));
  }

  async function handleReintegratePessoa(pessoa: PersonDTO) {
    if (!pessoa.idPerson) return;
  
    try {
      const updated = await reintegratePerson(pessoa.idPerson);
  
      setPessoas((prev) =>
        prev.map((p) =>
          p.idPerson === updated.idPerson ? updated : p
        )
      );
      loadPersons();
      toastSuccess("Pessoa reenviada para integração!");
    } catch (error) {
      toastError("Erro ao reenviar pessoa para integração.");
      console.error(error);
    }
  }  

  return (
    <div>
      <Typography
        variant="h5"
        color="#015caa"
        fontWeight="bold"
        p={2}
        mb={3}
        mt={3}
      >
        Cadastro de Pessoa
      </Typography>

      <PessoaForm pessoaEditando={pessoaEditando} onCancelEdit={() => setPessoaEditando(null)} onAddPessoa={handleAddPessoa} />
      <PessoaList onEdit={(pessoa) => handleEditPessoa(pessoa)} pessoas={pessoas} onDelete={handleDeletePessoa} onReintegrate={handleReintegratePessoa}/>
    </div>
  );
}

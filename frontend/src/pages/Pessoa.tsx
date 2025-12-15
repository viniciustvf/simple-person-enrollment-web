import { useEffect, useState } from "react";
import {
  Backdrop,
  Box,
  CircularProgress,
  Paper,
  Stack,
  Typography,
} from "@mui/material";
import PersonIcon from "@mui/icons-material/Person";
import PessoaForm from "../components/pessoa/PessoaForm";
import PessoaList from "../components/pessoa/PessoaList";
import { PersonDTO } from "../models/person/PersonDTO";
import {
  createPerson,
  deletePerson,
  findAllPersonsPaged,
  integratePerson,
  updatePerson,
} from "../services/backend/person.service";
import { useToast } from "../hooks/useToast";

export default function Pessoa() {
  const { toastError, toastSuccess } = useToast();

  const [pessoas, setPessoas] = useState<PersonDTO[]>([]);
  const [pessoaEditando, setPessoaEditando] = useState<PersonDTO | null>(null);
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [total, setTotal] = useState(0);

  useEffect(() => {
    loadPersons();
  }, [page, rowsPerPage]);

  async function loadPersons() {
    try {
      setLoading(true);

      const response = await findAllPersonsPaged(page, rowsPerPage);
      setPessoas(response.content);
      setTotal(response.totalElements);
    } catch (error) {
      toastError("Erro ao carregar pessoas.");
      console.error(error);
    } finally {
      setLoading(false);
    }
  }

  async function handleAddPessoa(pessoa: PersonDTO) {
    try {
      setLoading(true);

      if (pessoa.idPerson) {
        await updatePerson(pessoa.idPerson, pessoa);
        toastSuccess("Pessoa atualizada com sucesso!");
      } else {
        await createPerson(pessoa);
        toastSuccess("Pessoa cadastrada com sucesso!");
      }

      setPessoaEditando(null);

      await loadPersons();
    } catch (error) {
      toastError("Erro ao salvar pessoa.");
      console.error(error);
    } finally {
      setLoading(false);
    }
  }

  function handleEditPessoa(pessoa: PersonDTO) {
    setPessoaEditando(pessoa);
  }

  async function handleDeletePessoa(index: number) {
    const pessoa = pessoas[index];
    if (!pessoa?.idPerson) return;

    try {
      setLoading(true);

      await deletePerson(pessoa.idPerson);
      toastSuccess("Pessoa excluída com sucesso!");

      if (pessoas.length === 1 && page > 0) {
        setPage((prev) => prev - 1);
      } else {
        await loadPersons();
      }
    } catch (error) {
      toastError("Erro ao excluir pessoa.");
      console.error(error);
    } finally {
      setLoading(false);
    }
  }

  async function handleIntegratePessoa(pessoa: PersonDTO) {
    if (!pessoa.idPerson) return;

    try {
      setLoading(true);

      await integratePerson(pessoa.idPerson);
      toastSuccess("Pessoa enviada para integração!");

      await loadPersons();
    } catch (error) {
      toastError("Erro ao enviar pessoa para integração.");
      console.error(error);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div>
      <Paper
        elevation={0}
        sx={{
          mt: 3,
          mb: 3,
          p: 2.5,
          borderRadius: 3,
          position: "relative",
          overflow: "hidden",
          border: "1px solid",
          borderColor: "divider",
          background:
            "linear-gradient(135deg, rgba(1,92,170,0.10) 0%, rgba(1,92,170,0.04) 50%, rgba(1,92,170,0.00) 100%)",
        }}
      >
        <Box
          sx={{
            position: "absolute",
            left: 0,
            top: 0,
            bottom: 0,
            width: 6,
            bgcolor: "#015caa",
          }}
        />

        <Stack direction="row" spacing={1.5} alignItems="center">
          <Box
            sx={{
              width: 44,
              height: 44,
              borderRadius: 2,
              display: "grid",
              placeItems: "center",
              bgcolor: "rgba(1,92,170,0.12)",
            }}
          >
            <PersonIcon sx={{ color: "#015caa" }} />
          </Box>

          <Box>
            <Typography
              variant="h5"
              sx={{ color: "#015caa", fontWeight: 800, lineHeight: 1.1 }}
            >
              Cadastro de Pessoa
            </Typography>

            <Typography
              variant="body2"
              sx={{ color: "text.secondary", mt: 0.3 }}
            >
              Cadastre, edite e integre pessoas com validações e consulta de CEP.
            </Typography>
          </Box>
        </Stack>
      </Paper>

      <PessoaForm
        pessoaEditando={pessoaEditando}
        onCancelEdit={() => setPessoaEditando(null)}
        onAddPessoa={handleAddPessoa}
      />

      <PessoaList
        pessoas={pessoas}
        page={page}
        rowsPerPage={rowsPerPage}
        total={total}
        onPageChange={setPage}
        onRowsPerPageChange={(value) => {
          setRowsPerPage(value);
          setPage(0);
        }}
        onEdit={handleEditPessoa}
        onDelete={handleDeletePessoa}
        onIntegrate={handleIntegratePessoa}
      />

      <Backdrop
        open={loading}
        sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }}
      >
        <CircularProgress color="inherit" />
      </Backdrop>
    </div>
  );
}
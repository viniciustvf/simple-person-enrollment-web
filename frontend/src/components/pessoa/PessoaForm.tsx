import { useEffect, useState } from "react";
import { Box, Button, Grid, Paper, TextField } from "@mui/material";
import dayjs, { Dayjs } from "dayjs";
import { DatePicker } from "@mui/x-date-pickers";
import InputMask from "react-input-mask";
import EnderecoForm from "../endereco/EnderecoForm";
import { useToast } from "../../hooks/useToast";
import { formatNameLive, isValidCPF, isValidEmail } from "../../util/validators";
import { PersonDTO } from "../../models/person/PersonDTO";
import { AddressDTO } from "../../models/address/AddressDTO";
import { existsByCpf } from "../../services/backend/person.service";

interface Props {
  onAddPessoa: (pessoa: PersonDTO) => void;
  pessoaEditando?: PersonDTO | null;
  onCancelEdit?: () => void;
}

export default function PessoaForm({
  onAddPessoa,
  pessoaEditando,
  onCancelEdit,
}: Props) {
  const { toastError } = useToast();

  const [nome, setNome] = useState("");
  const [dataNascimento, setDataNascimento] = useState<Dayjs | null>(null);
  const [cpf, setCpf] = useState("");
  const [email, setEmail] = useState("");

  const [endereco, setEndereco] = useState<AddressDTO>({
    cep: "",
    rua: "",
    numero: "",
    cidade: "",
    uf: "",
  });

  useEffect(() => {
    if (pessoaEditando) {
      setNome(pessoaEditando.name || "");
      setCpf(pessoaEditando.cpf || "");
      setEmail(pessoaEditando.email || "");
      setDataNascimento(
        pessoaEditando.birth ? dayjs(pessoaEditando.birth) : null
      );
      setEndereco(
        pessoaEditando.address || {
          cep: "",
          rua: "",
          numero: "",
          cidade: "",
          uf: "",
        }
      );
    }
  }, [pessoaEditando]);

  async function salvarPessoa() {
    const errors: string[] = [];

    if (!nome.trim()) errors.push("O campo Nome é obrigatório.");
    if (!cpf.trim()) errors.push("O campo CPF é obrigatório.");

    if (nome.trim()) {
      const partesNome = nome.trim().split(" ").filter(Boolean);
      if (partesNome.length < 2) {
        errors.push("Informe pelo menos nome e sobrenome.");
      }
    }

    if (dataNascimento && dayjs(dataNascimento).isAfter(dayjs())) {
      errors.push("Data de nascimento não pode ser futura.");
    }

    if (cpf.trim() && !isValidCPF(cpf)) {
      errors.push("CPF inválido.");
    }

    if (email && !isValidEmail(email)) {
      errors.push("E-mail inválido.");
    }

    if (endereco.cep) {
      const cepOk = endereco.cep.replace(/\D/g, "").length === 8;
      if (!cepOk) {
        errors.push("CEP inválido.");
      }

      const camposEndereco = [
        endereco.rua,
        endereco.numero,
        endereco.cidade,
        endereco.uf,
      ];

      if (camposEndereco.some((c) => !c)) {
        errors.push("Preencha os campos do endereço.");
      }

      const ufsValidas = [
        "AC",
        "AL",
        "AP",
        "AM",
        "BA",
        "CE",
        "DF",
        "ES",
        "GO",
        "MA",
        "MT",
        "MS",
        "MG",
        "PA",
        "PB",
        "PR",
        "PE",
        "PI",
        "RJ",
        "RN",
        "RS",
        "RO",
        "RR",
        "SC",
        "SP",
        "SE",
        "TO",
      ];

      if (endereco.uf && !ufsValidas.includes(endereco.uf.toUpperCase())) {
        errors.push("UF inválida.");
      }
    }

    if (errors.length > 0) {
      errors.forEach((msg) => toastError(msg));
      return;
    }

    const cpfLimpo = cpf.replace(/\D/g, "");

    if (!pessoaEditando) {
      const cpfJaExiste = await existsByCpf(cpfLimpo);
      if (cpfJaExiste) {
        toastError("CPF já cadastrado.");
        return;
      }
    }

    const novaPessoa: PersonDTO = {
      idPerson: pessoaEditando?.idPerson,
      name: nome.trim(),
      birth: dataNascimento ? dataNascimento.format("YYYY-MM-DD") : null,
      cpf: cpfLimpo,
      email: email.trim(),
      address: {
        cep: endereco.cep,
        rua: endereco.rua,
        numero: endereco.numero,
        cidade: endereco.cidade,
        uf: endereco.uf,
      },
      integrationStatus: "",
    };

    onAddPessoa(novaPessoa);
    limparFormulario();
  }

  function limparFormulario() {
    setNome("");
    setCpf("");
    setEmail("");
    setDataNascimento(null);
    setEndereco({ cep: "", rua: "", numero: "", cidade: "", uf: "" });
    onCancelEdit?.();
  }

  return (
    <Box display="flex" justifyContent="center" alignItems="center" mt={4}>
      <Paper sx={{ p: 4, width: "100%" }}>
        <Grid container spacing={2}>
          <Grid item xs={6}>
            <TextField
              fullWidth
              label="Nome"
              value={nome}
              onChange={(e) => {
                const formatted = formatNameLive(e.target.value);
                setNome(formatted);
              }}
              size="small"
              required
            />
          </Grid>

          <Grid item xs={6}>
            <DatePicker
              label="Data de nascimento"
              value={dataNascimento}
              onChange={setDataNascimento}
              maxDate={dayjs()}
              sx={{ width: "100%" }}
              slotProps={{ textField: { fullWidth: true, size: "small" } }}
            />
          </Grid>

          <Grid item xs={6}>
            <InputMask
              mask="999.999.999-99"
              value={cpf}
              onChange={(e) => setCpf(e.target.value)}
            >
              {(inputProps) => (
                <TextField
                  {...inputProps}
                  fullWidth
                  label="CPF"
                  size="small"
                  required
                />
              )}
            </InputMask>
          </Grid>

          <Grid item xs={6}>
            <TextField
              fullWidth
              label="Email"
              value={email}
              onChange={(e) => {
                const onlyValidChars = e.target.value
                  .toLowerCase()
                  .replace(/\s/g, "")
                  .replace(/[^a-z0-9@._+-]/g, "");

                setEmail(onlyValidChars);
              }}
              inputProps={{
                style: { textTransform: "lowercase" },
                inputMode: "email",
                autoCapitalize: "none",
                autoCorrect: "off",
                spellCheck: false,
              }}
              size="small"
            />
          </Grid>

          <EnderecoForm endereco={endereco} onChange={setEndereco} />

          <Grid item xs={12}>
            <Button
              variant="contained"
              onClick={salvarPessoa}
              sx={{ backgroundColor: "#015caa", mr: 2 }}
            >
              {pessoaEditando ? "Salvar Alterações" : "Salvar Pessoa"}
            </Button>

            {pessoaEditando && (
              <Button variant="outlined" onClick={limparFormulario}>
                Cancelar
              </Button>
            )}
          </Grid>
        </Grid>
      </Paper>
    </Box>
  );
}
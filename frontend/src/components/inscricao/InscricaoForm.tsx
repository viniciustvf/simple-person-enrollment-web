import { useState } from "react";
import {
  Box,
  Button,
  Grid,
  Paper,
  TextField,
  MenuItem,
  Typography,
} from "@mui/material";
import { CourseDTO } from "../../models/couse/CourseDTO";
import { PersonApiDTO } from "../../models/person/PersonApiDTO";
import { createRegistration } from "../../services/api/api.registration.service";
import InscricaoList from "./InscricaoList";
import { InscricaoDTO } from "../../models/registration/InscricaoDTO";
import { NumericFormat } from "react-number-format";
import { useToast } from "../../hooks/useToast";

interface Props {
    cursos: CourseDTO[];
    pessoas: PersonApiDTO[];
    inscricoes: InscricaoDTO[];
    setInscricoes: React.Dispatch<React.SetStateAction<InscricaoDTO[]>>;
    cursoSelecionado: number | null;
    onCursoSelecionado: (idCurso: number) => void;
    onFinalizar: () => void;
}

export default function InscricaoForm({
  cursos,
  pessoas,
  inscricoes,
  onCursoSelecionado,
  onFinalizar,
  cursoSelecionado
}: Props) {
  const { toastError } = useToast();
  const [pessoa, setPessoa] = useState<PersonApiDTO | null>(null);
  const curso = cursos.find(c => c.idCourse === cursoSelecionado) || null;
  const cursoFinalizado = curso?.courseRegistrationStatus === "FINALIZADA";
  const [nota, setNota] = useState("");

  async function handleInscrever() {
    if (!validarInscricao()) return;
  
    try {
      await createRegistration({
        idCourse: cursoSelecionado!,
        cpf: pessoa!.cpf,
        nota: Number(nota),
      });
  
      onCursoSelecionado(cursoSelecionado!);
      setPessoa(null);
      setNota("");
    } catch (error) {
      console.error("Erro ao criar inscrição:", error);
    }
  }   

  function validarInscricao(): boolean {
    const errors: string[] = [];
  
    if (!curso) {
      errors.push("Selecione um curso.");
    }
  
    if (!pessoa) {
      errors.push("Selecione uma pessoa.");
    }
  
    if (!nota) {
      errors.push("Informe a nota.");
    }
  
    if (errors.length > 0) {
      errors.forEach((msg) => toastError(msg));
      return false;
    }
  
    return true;
  }  

  return (
    <Box mt={4}>
      <Paper sx={{ p: 4 }}>
        <Grid container spacing={3}>
          <Grid item xs={12} md={6}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  select
                  fullWidth
                  label="Curso"
                  value={cursoSelecionado ?? ""}
                  onChange={(e) => {
                    const value = e.target.value;

                    if (value === "") {
                      onCursoSelecionado(0);
                      return;
                    }
                    onCursoSelecionado(Number(value));
                  }}  
                  size="small"
                >
                    <MenuItem value="">
                        <p>Selecione</p>
                    </MenuItem>
                    {cursos.map((c) => (
                    <MenuItem key={c.idCourse} value={c.idCourse}>
                        {c.name}
                        {c.courseRegistrationStatus === "FINALIZADA"
                        ? " (IF)"
                        : ` (${c.numVacancies} vagas)`}
                    </MenuItem>
                    ))}
                </TextField>
              </Grid>

              <Grid item xs={12}>
                <TextField
                  select
                  fullWidth
                  label="Pessoa"
                  disabled={cursoFinalizado}
                  value={pessoa?.idPerson || ""}
                  onChange={(e) =>
                    setPessoa(
                      pessoas.find(
                        (p) => p.idPerson === Number(e.target.value)
                      ) || null
                    )
                  }
                  size="small"
                >
                    <MenuItem value="">
                        <p>Selecione</p>
                    </MenuItem>
                  {pessoas.map((p) => (
                    <MenuItem key={p.idPerson} value={p.idPerson}>
                      {p.name}
                    </MenuItem>
                  ))}
                </TextField>
              </Grid>

              <Grid item xs={3}>
                <NumericFormat
                    customInput={TextField}
                    fullWidth
                    label="Nota"
                    size="small"
                    placeholder="0,0"
                    value={nota}
                    decimalSeparator=","
                    decimalScale={1}
                    allowNegative={false}
                    allowLeadingZeros={false}
                    fixedDecimalScale={false}
                    disabled={cursoFinalizado}
                    isAllowed={(values) => {
                    const { floatValue } = values;

                    if (floatValue === undefined) return true;

                    return floatValue >= 0 && floatValue <= 10;
                    }}
                    onValueChange={(values) => {
                    setNota(values.value);
                    }}
                />
                </Grid>

              <Grid item xs={12}>
                <Button
                  variant="contained"
                  onClick={handleInscrever}
                  disabled={cursoFinalizado}
                  sx={{ backgroundColor: "#015caa", mr: 2 }}
                >
                  Inscrever
                </Button>

                <Button
                    variant="contained"
                    onClick={onFinalizar}
                    disabled={cursoFinalizado}
                    sx={{ backgroundColor: "#015caa" }}
                    >
                    Finalizar Inscrições
                </Button>

              </Grid>
            </Grid>
          </Grid>

          <Grid item xs={12} md={6}>
          <Typography
            variant="h5"
            color="#015caa"
            fontWeight="bold">
            Inscritos
          </Typography>
            <InscricaoList inscricoes={inscricoes} />
          </Grid>
        </Grid>
      </Paper>
    </Box>
  );
}
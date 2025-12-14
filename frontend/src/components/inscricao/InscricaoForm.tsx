import { useEffect, useState } from "react";
import {
  Box,
  Button,
  Grid,
  Paper,
  TextField,
  MenuItem,
  Typography,
} from "@mui/material";
import { CourseDTO } from "../../models/CourseDTO";
import { PersonApiDTO } from "../../models/PersonApiDTO";
import { createRegistration } from "../../services/api/api.registration.service";
import InscricaoList from "./InscricaoList";
import { InscricaoDTO } from "../../models/InscricaoDTO";
import { NumericFormat } from "react-number-format";

interface Props {
  cursos: CourseDTO[];
  pessoas: PersonApiDTO[];
  inscricoes: InscricaoDTO[];
  setInscricoes: React.Dispatch<React.SetStateAction<InscricaoDTO[]>>;
  onCursoSelecionado: (idCurso: number) => void;
  onFinalizar: () => void;
}

export default function InscricaoForm({
  cursos,
  pessoas,
  inscricoes,
  onCursoSelecionado,
  onFinalizar,
}: Props) {
  const [curso, setCurso] = useState<CourseDTO | null>(null);
  const [pessoa, setPessoa] = useState<PersonApiDTO | null>(null);
  const cursoFinalizado = curso?.situacaoCurso === "FINALIZADA";
  const [nota, setNota] = useState("");

  async function handleInscrever() {
    if (!curso || !pessoa || !nota) return;

    try {
      await createRegistration({
        idCourse: curso.idCourse,
        cpf: pessoa.cpf,
        nota: Number(nota),
      });

      onCursoSelecionado(curso.idCourse);
      setPessoa(null);
      setNota("");
    } catch (error) {
      console.error("Erro ao criar inscrição:", error);
    }
  }

  useEffect(() => {
    if (curso?.idCourse) {
      onCursoSelecionado(curso.idCourse);
    }
  }, [curso]);  

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
                  value={curso?.idCourse || ""}
                  onChange={(e) => {
                    const selected =
                      cursos.find(c => c.idCourse === Number(e.target.value)) || null;
                  
                    setCurso(selected);
                  }}  
                  size="small"
                >
                    {cursos.map((c) => (
                    <MenuItem key={c.idCourse} value={c.idCourse}>
                        {c.nome}
                        {c.situacaoCurso === "FINALIZADA"
                        ? " (IF)"
                        : ` (${c.numeroVagas} vagas)`}
                    </MenuItem>
                    ))}
                </TextField>
              </Grid>

              <Grid item xs={12}>
                <TextField
                  select
                  fullWidth
                  label="Pessoa"
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
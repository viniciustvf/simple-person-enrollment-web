import { Box, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from "@mui/material";
import { InscricaoDTO } from "../../models/InscricaoDTO";

interface Props {
  selecionados: InscricaoDTO[];
}

export default function SelectedList({ selecionados }: Props) {
  return (
    <Box mt={4}>
      <Paper sx={{ p: 2 }}>
      <Typography
            variant="h5"
            color="#015caa"
            fontWeight="bold">
            Selecionados
      </Typography>
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell><b>Posição</b></TableCell>
                <TableCell><b>Nome</b></TableCell>
                <TableCell><b>Curso</b></TableCell>
                <TableCell><b>Nota</b></TableCell>
              </TableRow>
            </TableHead>

            <TableBody>
              {selecionados.map((s, idx) => (
                <TableRow key={idx}>
                  <TableCell>{s.posicao}º</TableCell>
                  <TableCell>{s.nome}</TableCell>
                  <TableCell>{s.curso}</TableCell>
                  <TableCell>{s.nota}</TableCell>
                </TableRow>
              ))}
            {selecionados.length === 0 && (
              <TableRow>
                <TableCell colSpan={6} align="center" sx={{ py: 3 }}>
                  Nenhuma pessoa foi selecionada para o curso.
                </TableCell>
              </TableRow>
            )}
            </TableBody>
          </Table>
        </TableContainer>
      </Paper>
    </Box>
  );
}
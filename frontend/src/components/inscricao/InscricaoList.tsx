import { Box, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { InscricaoDTO } from "../../models/registration/InscricaoDTO";

interface Props {
  inscricoes: InscricaoDTO[];
}

export default function InscricaoList({ inscricoes }: Props) {
  return (
    <Box mt={4}>
      <Paper sx={{ p: 2 }}>
        <TableContainer sx={{ maxHeight: 300 }}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell><b>Nome</b></TableCell>
                <TableCell><b>Curso</b></TableCell>
                <TableCell><b>Nota</b></TableCell>
              </TableRow>
            </TableHead>

            <TableBody>
              {inscricoes.map((i, idx) => (
                <TableRow key={idx}>
                  <TableCell>{i.nome}</TableCell>
                  <TableCell>{i.curso}</TableCell>
                  <TableCell>{i.nota}</TableCell>
                </TableRow>
              ))}
            {inscricoes.length === 0 && (
              <TableRow>
                <TableCell colSpan={6} align="center" sx={{ py: 3 }}>
                  Nenhum inscrito no curso.
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

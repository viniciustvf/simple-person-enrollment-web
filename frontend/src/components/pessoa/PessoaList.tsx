import { useState } from "react";
import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TablePagination,
  TableRow,
  Typography,
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import SendIcon from "@mui/icons-material/Send";
import dayjs from "dayjs";
import { PersonDTO } from "../../models/person/PersonDTO";
import { useToast } from "../../hooks/useToast";

interface Props {
  pessoas: PersonDTO[];
  onEdit?: (pessoa: PersonDTO) => void;
  onDelete?: (index: number) => void;
  onIntegrate?: (pessoa: PersonDTO) => void;
  page: number;
  rowsPerPage: number;
  total: number;
  onPageChange: (page: number) => void;
  onRowsPerPageChange: (rows: number) => void;
}

function maskCPF(cpf: string) {
  if (!cpf || cpf.length !== 11) return cpf;
  return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
}

export default function PessoaList({
  pessoas,
  onEdit,
  onDelete,
  onIntegrate,
  page,
  rowsPerPage,
  total,
  onPageChange,
  onRowsPerPageChange,
}: Props) {
  const [openConfirm, setOpenConfirm] = useState(false);
  const [indexToDelete, setIndexToDelete] = useState<number | null>(null);
  const [disabledIntegrateIds, setDisabledIntegrateIds] = useState<number[]>([]);
  const { toastError } = useToast();

  function handleOpenDelete(index: number) {
    setIndexToDelete(index);
    setOpenConfirm(true);
  }

  function handleCloseDelete() {
    setOpenConfirm(false);
    setIndexToDelete(null);
  }

  function handleConfirmDelete() {
    if (indexToDelete !== null) {
      onDelete?.(indexToDelete);
    }
    handleCloseDelete();
  }

  async function handleIntegrateClick(pessoa: PersonDTO) {
    if (!pessoa.idPerson) return;

    const id = pessoa.idPerson;
    setDisabledIntegrateIds((prev) => [...prev, id]);

    try {
      await onIntegrate?.(pessoa);
    } catch {
      toastError("Erro ao integrar pessoa");
      setDisabledIntegrateIds((prev) =>
        prev.filter((existingId) => existingId !== id)
      );
    }
  }

  return (
    <Box mt={4}>
      <Paper sx={{ p: 2 }}>
        <Box
          display="flex"
          alignItems="center"
          justifyContent="space-between"
          mb={2}
        >
          <Typography variant="h5" color="#015caa" fontWeight="bold">
            Pessoas cadastradas
          </Typography>
        </Box>

        <TableContainer sx={{ maxHeight: 350 }}>
          <Table stickyHeader>
            <TableHead>
              <TableRow>
                <TableCell>
                  <b>Nome</b>
                </TableCell>
                <TableCell>
                  <b>CPF</b>
                </TableCell>
                <TableCell>
                  <b>Nascimento</b>
                </TableCell>
                <TableCell>
                  <b>Email</b>
                </TableCell>
                <TableCell>
                  <b>Cidade/UF</b>
                </TableCell>
                <TableCell align="center">
                  <b>Ações</b>
                </TableCell>
              </TableRow>
            </TableHead>

            <TableBody>
              {pessoas.map((pessoa, index) => (
                <TableRow key={pessoa.idPerson ?? index}>
                  <TableCell>{pessoa.name}</TableCell>
                  <TableCell>{maskCPF(pessoa.cpf)}</TableCell>
                  <TableCell>
                    {pessoa.birth
                      ? dayjs(pessoa.birth).format("DD/MM/YYYY")
                      : "-"}
                  </TableCell>
                  <TableCell>{pessoa.email || "-"}</TableCell>
                  <TableCell>
                    {pessoa.address?.cidade}/{pessoa.address?.uf}
                  </TableCell>
                  <TableCell align="center">
                    <IconButton
                      color="primary"
                      onClick={() => onEdit?.(pessoa)}
                    >
                      <EditIcon />
                    </IconButton>

                    <IconButton
                      color="error"
                      onClick={() => handleOpenDelete(index)}
                    >
                      <DeleteIcon />
                    </IconButton>

                    <IconButton
                      color="secondary"
                      title="Enviar para integração"
                      disabled={
                        pessoa.integrationStatus === "Sucesso" ||
                        (pessoa.idPerson !== undefined &&
                          disabledIntegrateIds.includes(pessoa.idPerson))
                      }
                      onClick={() => handleIntegrateClick(pessoa)}
                    >
                      <SendIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}

              {pessoas.length === 0 && (
                <TableRow>
                  <TableCell colSpan={6} align="center" sx={{ py: 3 }}>
                    Nenhuma pessoa cadastrada.
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>

        <TablePagination
          component="div"
          count={total}
          page={page}
          onPageChange={(_, newPage) => onPageChange(newPage)}
          rowsPerPage={rowsPerPage}
          onRowsPerPageChange={(e) =>
            onRowsPerPageChange(parseInt(e.target.value, 10))
          }
          rowsPerPageOptions={[5, 10, 20]}
          labelRowsPerPage="Linhas por página"
        />
      </Paper>

      <Dialog open={openConfirm} onClose={handleCloseDelete}>
        <DialogTitle>Confirmar exclusão</DialogTitle>
        <DialogContent>Deseja realmente remover esta pessoa?</DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDelete}>Cancelar</Button>
          <Button color="error" onClick={handleConfirmDelete}>
            Remover
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}

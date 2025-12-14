import { useEffect, useState } from "react";
import {
  Box,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  IconButton,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
} from "@mui/material";
import { alpha, useTheme } from "@mui/material/styles";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import SendIcon from "@mui/icons-material/Send";
import dayjs from "dayjs";
import { PersonDTO } from "../../models/PersonDTO";

interface Props {
  pessoas: PersonDTO[];
  onEdit?: (pessoa: PersonDTO) => void;
  onDelete?: (index: number) => void;
  onReintegrate?: (pessoa: PersonDTO) => void;
}

function maskCPF(cpf: string) {
  if (!cpf || cpf.length !== 11) return cpf;
  return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
}

export default function PessoaList({ pessoas, onEdit, onDelete, onReintegrate }: Props) {
  const theme = useTheme();
  const [openConfirm, setOpenConfirm] = useState(false);
  const [indexToDelete, setIndexToDelete] = useState<number | null>(null);
  const [disabledReintegrateIds, setDisabledReintegrateIds] = useState<(number)[]>([]);

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

  async function handleReintegrateClick(pessoa: PersonDTO) {
    if (!pessoa.idPerson) return;
  
    const id = pessoa.idPerson;
  
    setDisabledReintegrateIds((prev) => [...prev, id]);
  
    try {
      await onReintegrate?.(pessoa);
    } catch (error) {
      console.error("Erro ao reintegrar:", error);
  
      setDisabledReintegrateIds((prev) => prev.filter((existingId) => existingId !== id));
    }
  }  

  useEffect(() => {
    console.log("disabledReintegrateIds mudou:", disabledReintegrateIds);
  }, [disabledReintegrateIds]);
  

  return (
    <Box
      sx={{
        p: 4,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        gap: 3,
        width: "100%",
      }}
    >
      <TableContainer
        component={Paper}
        elevation={0}
        sx={{
          width: "100%",
          borderRadius: 3,
          overflow: "hidden",
          backgroundColor: alpha(theme.palette.background.paper, 0.6),
          backdropFilter: "blur(6px)",
        }}
      >
        <Table>
          <TableHead>
            <TableRow>
              <TableCell sx={{ fontWeight: "bold" }}>Nome</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>CPF</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Nascimento</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Email</TableCell>
              <TableCell sx={{ fontWeight: "bold" }}>Cidade/UF</TableCell>
              <TableCell sx={{ fontWeight: "bold" }} align="center">
                Ações
              </TableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {pessoas.map((pessoa, index) => (
              <TableRow
                key={pessoa.idPerson}
                sx={{
                  transition: "all 0.1s ease-in-out",
                  backgroundColor:
                    index % 2 === 0
                      ? alpha(theme.palette.background.default, 0.4)
                      : alpha(theme.palette.background.default, 0.7),
                  "&:hover": {
                    backgroundColor: alpha(
                      theme.palette.primary.light,
                      theme.palette.mode === "light" ? 0.15 : 0.25
                    ),
                  },
                }}
              >
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
                  <IconButton color="primary"onClick={() => onEdit?.(pessoa, index)}>
                  <EditIcon />
                  </IconButton>

                  <IconButton color="error" onClick={() => handleOpenDelete(index)}>
                  <DeleteIcon />
                  </IconButton>

                  <IconButton color="secondary" title="Reenviar para integração"   
                            disabled={
                              pessoa.integrationStatus === "Sucesso" ||
                              (pessoa.idPerson !== undefined && disabledReintegrateIds.includes(pessoa.idPerson))
                            }                   
                            onClick={() => handleReintegrateClick(pessoa)}>
                    <SendIcon/>
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

      <Dialog open={openConfirm} onClose={handleCloseDelete}>
        <DialogTitle>Confirmar exclusão</DialogTitle>
        <DialogContent>
          Deseja realmente remover esta pessoa?
        </DialogContent>
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

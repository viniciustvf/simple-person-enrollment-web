import { useEffect } from "react";
import { Box, Grid, Paper, TextField } from "@mui/material";
import InputMask from "react-input-mask";
import { fetchAddressByCep } from "../../services/external/viacep.service";
import { AddressDTO } from "../../models/AddressDTO";

interface Props {
  endereco: AddressDTO;
  onChange: (endereco: AddressDTO) => void;
}

export default function EnderecoForm({ endereco, onChange }: Props) {
  const cepLimpo = endereco.cep.replace(/\D/g, "");
  const cepValido = cepLimpo.length === 8;

  async function buscarCep() {
    try {
      const data = await fetchAddressByCep(endereco.cep);

      onChange({
        ...endereco,
        rua: data.logradouro || endereco.rua,
        cidade: data.localidade || endereco.cidade,
        uf: data.uf || endereco.uf,
      });
    } catch {
      // CEP não encontrado → usuário pode digitar manualmente
    }
  }

  useEffect(() => {
    if (cepValido) {
      buscarCep();
    }
  }, [cepLimpo]);

  return (
    <Box mt={3} width="100%">
      <Paper sx={{ p: 2 }} elevation={2}>
        <Grid container spacing={2} alignItems="center">
          {/* CEP */}
          <Grid item xs={12} sm={3}>
            <InputMask
              mask="99999-999"
              value={endereco.cep}
              onChange={(e) =>
                onChange({
                  ...endereco,
                  cep: e.target.value.replace(/\D/g, ""),
                })
              }
              onBlur={() => cepValido && buscarCep()}
            >
              {() => <TextField fullWidth label="CEP" size="small" />}
            </InputMask>
          </Grid>

          {/* Logradouro */}
          <Grid item xs={12} sm={8}>
            <TextField
              fullWidth
              label="Logradouro"
              value={endereco.rua}
              onChange={(e) =>
                onChange({ ...endereco, rua: e.target.value })
              }
              size="small"
            />
          </Grid>

          {/* Número */}
          <Grid item xs={12} sm={2}>
            <TextField
              fullWidth
              label="Número"
              value={endereco.numero}
              onChange={(e) =>
                onChange({
                  ...endereco,
                  numero: e.target.value.replace(/\D/g, ""),
                })
              }
              size="small"
            />
          </Grid>

          {/* Cidade */}
          <Grid item xs={12} sm={4}>
            <TextField
              fullWidth
              label="Cidade"
              value={endereco.cidade}
              onChange={(e) =>
                onChange({ ...endereco, cidade: e.target.value })
              }
              size="small"
            />
          </Grid>

          {/* UF */}
          <Grid item xs={12} sm={2}>
            <TextField
              fullWidth
              label="UF"
              value={endereco.uf}
              onChange={(e) =>
                onChange({
                  ...endereco,
                  uf: e.target.value.toUpperCase().slice(0, 2),
                })
              }
              size="small"
            />
          </Grid>
        </Grid>
      </Paper>
    </Box>
  );
}
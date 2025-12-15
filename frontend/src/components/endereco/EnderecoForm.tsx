import { useEffect } from "react";
import { Box, Grid, Paper, TextField, MenuItem } from "@mui/material";
import InputMask from "react-input-mask";
import { fetchAddressByCep } from "../../services/external/viacep.service";
import { AddressDTO } from "../../models/address/AddressDTO";

interface Props {
  endereco: AddressDTO;
  onChange: (endereco: AddressDTO) => void;
}

const UFS = [
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
] as const;

export default function EnderecoForm({ endereco, onChange }: Props) {
  const cepLimpo = endereco.cep.replace(/\D/g, "");
  const cepValido = cepLimpo.length === 8;

  async function buscarCep() {
    try {
      const data = await fetchAddressByCep(endereco.cep);

      onChange({
        ...endereco,
        rua: data!.logradouro || endereco.rua,
        cidade: data!.localidade || endereco.cidade,
        uf: (data!.uf || endereco.uf || "").toUpperCase(),
      });
    } catch {
      /* empty */
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

          <Grid item xs={12} sm={8}>
            <TextField
              fullWidth
              label="Logradouro"
              value={endereco.rua}
              onChange={(e) => onChange({ ...endereco, rua: e.target.value })}
              size="small"
            />
          </Grid>

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

          <Grid item xs={12} sm={4}>
            <TextField
              fullWidth
              label="Cidade"
              value={endereco.cidade}
              onChange={(e) =>
                onChange({
                  ...endereco,
                  cidade: e.target.value.replace(/[^A-Za-zÀ-ÿ\s]/g, ""),
                })
              }
              inputProps={{
                inputMode: "text",
                pattern: "[A-Za-zÀ-ÿ\\s]*",
              }}
              size="small"
            />
          </Grid>

          <Grid item xs={12} sm={1}>
            <TextField
              select
              fullWidth
              label="UF"
              value={endereco.uf || ""}
              onChange={(e) =>
                onChange({
                  ...endereco,
                  uf: String(e.target.value).toUpperCase(),
                })
              }
              size="small"
              SelectProps={{
                MenuProps: {
                  PaperProps: {
                    sx: {
                      maxHeight: 200,
                    },
                  },
                },
              }}
            >
              <MenuItem value="" sx={{ py: 0.5, minHeight: 32 }}>
                Selecione
              </MenuItem>

              {UFS.map((uf) => (
                <MenuItem key={uf} value={uf} sx={{ py: 0.5, minHeight: 32 }}>
                  {uf}
                </MenuItem>
              ))}
            </TextField>
          </Grid>
        </Grid>
      </Paper>
    </Box>
  );
}
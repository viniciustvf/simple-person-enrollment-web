import { useEffect, useState } from "react";
import { Typography } from "@mui/material";
import InscricaoForm from "../components/inscricao/InscricaoForm";
import SelectedList from "../components/inscricao/SelectedList";
import { InscricaoDTO } from "../models/registration/InscricaoDTO";
import { CourseDTO } from "../models/couse/CourseDTO";
import { findAllCourses } from "../services/api/api.course.service";
import { findAllPersonsApi } from "../services/api/api.person.service";
import { Backdrop, CircularProgress } from "@mui/material";
import {
  finalizarInscricoes,
  findInscritosByCurso,
  findInscritosFinalizadosByCurso,
} from "../services/api/api.registration.service";
import { PersonApiDTO } from "../models/person/PersonApiDTO";
import { useToast } from "../hooks/useToast";

export default function Inscricao() {
  const { toastInfo, toastError } = useToast();
  const [loadingFinalizar, setLoadingFinalizar] = useState(false);
  const [inscricoes, setInscricoes] = useState<InscricaoDTO[]>([]);
  const [selecionados, setSelecionados] = useState<InscricaoDTO[]>([]);
  const [cursoSelecionado, setCursoSelecionado] = useState<number | null>(null);
  const [pessoas, setPessoas] = useState<PersonApiDTO[]>([]);
  const [cursos, setCursos] = useState<CourseDTO[]>([]);

  useEffect(() => {
    carregarDados();
  }, [cursoSelecionado]);

  async function carregarDados() {
    try {
      const [pessoasApi, cursosApi] = await Promise.all([
        findAllPersonsApi(),
        findAllCourses(),
      ]);
      setPessoas(pessoasApi);
      setCursos(cursosApi);
    } catch (error) {
      console.error("Erro ao carregar dados:", error);
    }
  }

  async function carregarSelecionados(idCurso: number) {
    try {
      const response = await findInscritosFinalizadosByCurso(idCurso);
      const mapped: InscricaoDTO[] = response.map((i, index) => ({
        nome: i.nomePessoa,
        curso: i.nomeCurso,
        nota: i.nota,
        posicao: index + 1,
      }));
      setSelecionados(mapped);
    } catch (error) {
      console.error("Erro ao carregar selecionados:", error);
    }
  }

  async function handleCursoSelecionado(idCurso: number) {
    try {
      setCursoSelecionado(idCurso);

      const [inscritosResponse, selecionadosResponse] = await Promise.all([
        findInscritosByCurso(idCurso),
        findInscritosFinalizadosByCurso(idCurso),
      ]);

      setInscricoes(
        inscritosResponse.map((i) => ({
          nome: i.nomePessoa,
          curso: i.nomeCurso,
          nota: i.nota,
        }))
      );

      setSelecionados(
        selecionadosResponse.map((i, index) => ({
          nome: i.nomePessoa,
          curso: i.nomeCurso,
          nota: i.nota,
          posicao: index + 1,
        }))
      );
    } catch (error) {
      console.error("Erro ao buscar dados do curso:", error);
    }
  }

  async function handleFinalizar() {
    if (!cursoSelecionado) {
      toastError("Selecione um curso antes de finalizar as inscrições.");
      return;
    }
  
    const idCurso = cursoSelecionado;
  
    try {
      setLoadingFinalizar(true);
  
      const message = await finalizarInscricoes(idCurso);
      toastInfo(message);
  
      const [pessoasApi, cursosApi] = await Promise.all([
        findAllPersonsApi(),
        findAllCourses(),
      ]);
  
      setPessoas(pessoasApi);
      setCursos(cursosApi);
  
      await handleCursoSelecionado(idCurso);
  
    } catch (error) {
      console.error("Erro ao finalizar inscrições:", error);
      toastError("Erro ao finalizar inscrições.");
    } finally {
      setLoadingFinalizar(false);
    }
  }  

  async function handleRefreshSelecionados() {
    if (!cursoSelecionado) {
      toastError("Selecione um curso para atualizar os selecionados.");
      return;
    }
  
    await carregarSelecionados(cursoSelecionado);
  }  

  return (
    <div>
      <Typography
        variant="h5"
        color="#015caa"
        fontWeight="bold"
        p={2}
        mb={3}
        mt={3}
      >
        Inscrição de cursos
      </Typography>

      <InscricaoForm
        cursos={cursos}
        pessoas={pessoas}
        inscricoes={inscricoes}
        setInscricoes={setInscricoes}
        onCursoSelecionado={handleCursoSelecionado}
        onFinalizar={handleFinalizar} 
        cursoSelecionado={cursoSelecionado}
        />

      <SelectedList selecionados={selecionados} onRefresh={handleRefreshSelecionados} />
      <Backdrop
            open={loadingFinalizar}
            sx={{ color: "#fff", zIndex: (theme) => theme.zIndex.drawer + 1 }}
        >
        <CircularProgress color="inherit" />
        </Backdrop>
    </div>
  );
}

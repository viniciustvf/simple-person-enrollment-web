# Avaliação Prática – Case On-line

Projeto desenvolvido em 30 horas.

O objetivo é entregar uma solução composta por **três aplicações** (Frontend, Backend WEB e API), integradas entre si, incluindo **cadastro de pessoa**, **consulta de CEP (ViaCEP)**, **inscrição em curso**, **finalização via fila (RabbitMQ)** e **consulta de selecionados**.

---

## ✅ Funcionalidades implementadas

### Aplicação WEB (Backend + Frontend)
- **CRUD de Pessoa**
  - Nome e CPF obrigatórios
  - Validação de nome completo (mín. 2 partes) e formatação (Title Case)
  - CPF com máscara no frontend e armazenado apenas com números
  - E-mail validado e digitado apenas com caracteres permitidos
  - Data de nascimento não pode ser futura
- **Endereço**
  - Busca de CEP via **ViaCEP** (`https://viacep.com.br/`)
  - Caso não encontrado, permite cadastro manual
  - CEP com máscara e armazenado sem caracteres
  - Número apenas numérico
  - Cidade apenas letras/espaços, UF em 2 letras maiúsculas
- **Inscrição em Curso (tela)**
  - Lista cursos vindos da API
  - Lista inscritos do curso selecionado (API)
  - Envia inscrição (CPF + nota + curso) para API
  - Finalização de inscrições (via API + fila)
  - Lista selecionados por curso

### API (Cursos e Inscrições)
- CRUD de Curso (via Postman / requisições REST)
- Inscrição de candidatos
- Finalização das inscrições:
  - Seleciona candidatos com maior nota conforme número de vagas
  - Atualiza status (Selecionado / Não selecionado)
  - Finaliza o curso (Em andamento / Finalizada)
  - Integração feita via fila **RabbitMQ** para não bloquear o usuário
- Endpoint para consulta de inscritos
- Endpoint para consulta de selecionados

---

## 🧱 Tecnologias

### Frontend
- React 18
- Vite
- TypeScript
- Material UI (MUI)
- React Router DOM
- React Hook Form (quando aplicável)
- Axios
- Dayjs
- React Toastify
- Input Mask / NumericFormat
- Tema claro/escuro com MUI

### Backend (WEB)
- Java 21
- Spring Boot 3.3.2
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Security
- MapStruct
- PostgreSQL / H2 (runtime)
- RabbitMQ (integração/fila)

### API
- Java 21
- Spring Boot 3.3.2
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Security
- MapStruct
- PostgreSQL
- RabbitMQ (fila de finalização e integração de pessoa com a API)

---

## 🔌 Endpoints principais

> Observação: os paths podem variar conforme controllers do projeto, mas a API foi modelada conforme o enunciado.

### Cursos
- `GET /v1/courses/curso`  
  Retorna todos os cursos.
- `POST /v1/courses/curso`  
  Cria curso (via Postman).

### Inscrições
- `GET /v1/registrations/inscritos/{idCurso}`  
  Retorna inscritos do curso.
- `GET /v1/registrations/inscritos-finalizados/{idCurso}`  
  Retorna selecionados do curso.
- `POST /v1/registrations/inscricao`  
  Inscreve candidato.
- `POST /v1/registrations/finalizar-inscricao`  
  Enfileira a finalização do curso (processo assíncrono via fila).  
  **Body esperado:** `Integer` (ex: `10`)

---

## 🧪 Regras de negócio (resumo)

### Pessoa
- Nome e CPF obrigatórios
- Nome deve conter pelo menos nome e sobrenome e estar formatado corretamente
- Data de nascimento não pode ser futura
- CPF deve ser válido
- E-mail deve ser válido (e digitável somente com caracteres permitidos)
- Se CEP informado, endereço completo se torna obrigatório
- Não permitir cadastro duplicado por CPF

### Inscrição
- Nota válida entre 0 e 10
- Não permitir inscrição duplicada para o mesmo curso
- Curso finalizado desabilita formulário de inscrição
- Finalização seleciona pela maior nota até o limite de vagas
- Demais ficam como “Não Selecionado”

---

## ▶️ Como rodar o projeto (local)

### Pré-requisitos
- Java 21
- Node.js 18+ (recomendado)
- Docker + Docker Compose (recomendado para PostgreSQL/RabbitMQ)
- Maven

---

### 1) Subir dependências (PostgreSQL + RabbitMQ)

Se você tiver `docker-compose.yml` no projeto, rode:

```bash
docker compose up -d
```

---

### 📝 Autor
**[Vinicius Costa](https://www.linkedin.com/in/vinícius-fernandes-costa/)** 


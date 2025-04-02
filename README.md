# Portal Egresso

## Visão Geral

- Portal Egresso é uma plataforma que permite a gestão e interação entre egressos de uma universidade
- O sistema possibilita que os egressos troquem informações, publiquem oportunidades, compartilhem depoimentos e visualizem relatórios sobre sua formação e empregabilidade
- Além disso, um coordenador administra e homologa todas as informações presentes na plataforma

**Nota:** Este projeto foi um trabalho em grupo desenvolvido durante a disciplina de Laboratório de Programação no curso de Ciência da Computação.

## Tecnologias Utilizadas

- **Frontend:** React
- **Backend:** Spring Boot
- **Banco de Dados:** NeonDB (PostgreSQL na nuvem)
- **Autenticação:** JWT
- **Hospedagem:** Railway
- **Gerenciamento de Containers:** Docker

## Páginas

### 🏠 Home (Público)
- Exibe um resumo com alguns egressos, depoimentos, relatórios e oportunidades.

### 🎓 Painel de Egressos (Público)
- Lista todos os egressos cadastrados.
- Possibilidade de buscar por curso, cargo e nome.

### 👩‍🎓 Perfil do Egresso (Público)
- Exibe informações como nome, cursos, foto, descrição, currículo, LinkedIn, GitHub e cargos.

### 🗣 Painel de Depoimentos (Público)
- Lista todos os depoimentos dos egressos.
- Possibilidade de buscar por ano, recentes e nome.

### 📊 Painel de Relatórios (Público)
- Relatórios disponíveis:
  - Egressos x Curso
  - Egressos x Nível do Curso
  - Curso x Empregabilidade (egressos com cargo e sem cargo)

### 💼 Painel de Oportunidades (Público)
- Local onde os egressos podem postar oportunidades e vagas de emprego.

### 🕹 Dashboard do Coordenador (Restrito ao Coordenador)
- **Gerenciar Cursos:** Criar, atualizar e excluir cursos.
- **Gerenciar Egressos:** Cadastrar, atualizar e excluir egressos.
- **Gerenciar Grupos de Discussão:** Excluir grupos.
- **Homologar Depoimentos e Oportunidades.**
- **Configurações:** Atualizar login e senha.

### 🕹 Dashboard do Egresso (Restrito ao Egresso)
- **Gerenciar Depoimentos:** Criar e excluir depoimentos.
- **Gerenciar Grupos de Discussão:** Criar e excluir grupos.
- **Participar de Grupos de Discussão:** Trocar mensagens com outros egressos.
- **Publicar e Gerenciar Oportunidades.**
- **Configurações:** Atualizar perfil.

## Demonstração
O projeto está hospedado em: [Portal Egresso](https://portal-egresso-frontend-production.up.railway.app/)

**Nota:** A área de coordenador e egresso requer login para acesso.

## Como Rodar o Projeto Localmente

### Requisitos:
- Ter **Docker** instalado.

### Passos:
1. Clone o repositório:
   ```bash
   git clone https://github.com/GiuliaFreulon/portal-egresso
   cd portal-egresso
   ```

2. Crie um arquivo `.env` dentro da pasta `frontend` e adicione:

   ```env
   # Banco de Dados (NeonDB)
   NEON_DATABASE_URL=jdbc:postgresql://ep-snowy-heart-a56k8zj0-pooler.us-east-2.aws.neon.tech/neondb
   NEON_DATABASE_USER=neondb_owner
   NEON_DATABASE_PASSWORD=npg_j6o4aFQAfpeZ

   # JWT
   JWT_SECRET_KEY=JrZcaAHkN81P9vyBn1p9R6tba7miSYRT57mGn1VRHDw=
   
   # API
   VITE_API_URL=http://localhost:8080
   PUBLIC_URL=http://localhost:8080
   ```

    **Nota:** O banco de dados disponibilizado para teste é **público** e pode ser fechado ou modificado a qualquer momento.

3. Inicie o contâiner:
   ```bash
   docker-compose up -d
   ```

O projeto estará rodando em `http://localhost:3000`.

### 🔑 Acessos para Testes
- **Coordenador:**
  - Login: `coordenador@email.com`
  - Senha: `123`
- **Egresso:**
  - Login: `egresso1@email.com`
  - Senha: `123`

## Design no Figma
O layout do projeto está disponível no Figma:
[Figma - Portal Egresso](https://www.figma.com/design/n4hZWAB6irWTbKa7heXH2G/Portal-Egresso?node-id=0-1&t=uD8GYBrewKIjSBk7-1)

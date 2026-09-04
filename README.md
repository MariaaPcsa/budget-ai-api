# Budget AI API

> API financeira inteligente desenvolvida com **Java 21, Spring Boot, Spring AI e OpenAI**, capaz de interpretar comandos em linguagem natural, utilizar **Tool Calling** para executar operações de negócio e processar interações por voz.

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring AI](https://img.shields.io/badge/Spring%20AI-AI%20Integration-blue)](https://spring.io/projects/spring-ai)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Containerization-blue)](https://www.docker.com/)
[![OpenAI](https://img.shields.io/badge/OpenAI-LLM-black)](https://openai.com/)

---

## Sobre o projeto

O **Budget AI API** é uma API REST desenvolvida para explorar a aplicação de **Inteligência Artificial Generativa em um backend Java**, utilizando o ecossistema Spring.

A aplicação permite que o usuário interaja com um assistente financeiro utilizando linguagem natural.

Por exemplo:

```text
"Gastei 50 reais no supermercado"
```

O modelo de IA interpreta a intenção da mensagem e pode solicitar uma ferramenta da aplicação para registrar a despesa.

A operação efetiva permanece sob responsabilidade da aplicação, seguindo o fluxo:

```text
Usuário
   ↓
API
   ↓
Spring AI / ChatClient
   ↓
LLM
   ↓
Tool Calling
   ↓
Use Case
   ↓
Domain
   ↓
Repository
   ↓
PostgreSQL
```

### Princípio central

> **A IA interpreta a intenção. A aplicação executa as regras de negócio.**

Essa separação evita que o LLM tenha responsabilidade direta sobre as regras determinísticas do sistema.

---

# Objetivos

O projeto foi desenvolvido para explorar:

* Desenvolvimento de APIs REST com Java e Spring Boot.
* Integração de LLMs utilizando Spring AI.
* Tool Calling.
* Arquitetura Limpa.
* Domain-Driven Design (DDD).
* Separação entre IA e regras de negócio.
* Persistência com PostgreSQL.
* Processamento de voz utilizando STT/TTS.
* Documentação de APIs com OpenAPI/Swagger.
* Testes automatizados.
* Containerização com Docker.
* Práticas de engenharia de software aplicadas a sistemas com IA.

---

# Funcionalidades

## Gestão de despesas

A API disponibiliza operações para gerenciamento de despesas:

* Criar despesa.
* Consultar todas as despesas.
* Consultar uma despesa por ID.
* Atualizar uma despesa.
* Excluir uma despesa.
* Consultar resumo financeiro.

Categorias disponíveis atualmente:

```text
FOOD
TRANSPORT
HEALTH
ENTERTAINMENT
EDUCATION
OTHER
```

---

## Assistente financeiro com IA

O usuário pode enviar mensagens utilizando linguagem natural.

Exemplo:

```text
Gastei 80 reais no supermercado.
```

A IA pode interpretar:

```json
{
  "amount": 80.00,
  "category": "FOOD",
  "location": "supermercado"
}
```

A partir dessa interpretação, o modelo pode utilizar uma ferramenta da aplicação para executar o caso de uso correspondente.

Outro exemplo:

```text
Quanto gastei hoje?
```

Nesse cenário, o LLM pode selecionar a ferramenta responsável por consultar os gastos realizados no dia.

---

# 🔧 Tool Calling

O **Tool Calling** é um dos principais conceitos explorados no projeto.

O LLM não acessa diretamente o banco de dados nem implementa as regras de negócio.

Ele recebe informações sobre as ferramentas disponíveis e decide qual ferramenta deve ser utilizada de acordo com a intenção do usuário.

### Fluxo

```text
                    ┌──────────────────┐
                    │     Usuário      │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │   REST API       │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │   Spring AI      │
                    │    ChatClient    │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │       LLM        │
                    │     OpenAI       │
                    └────────┬─────────┘
                             │
                       Tool Calling
                             │
              ┌──────────────┴──────────────┐
              ▼                             ▼
     registrar_gasto             consultar_resumo_gastos
              │                             │
              └──────────────┬──────────────┘
                             ▼
                    ┌──────────────────┐
                    │    Use Cases     │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │      Domain      │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │   PostgreSQL     │
                    └──────────────────┘
```

### Ferramentas

As ferramentas disponibilizadas ao assistente incluem:

| Tool                      | Responsabilidade                         |
| ------------------------- | ---------------------------------------- |
| `registrar_gasto`         | Registra uma nova despesa                |
| `consultar_resumo_gastos` | Consulta o resumo acumulado das despesas |
| `consultar_gastos_hoje`   | Consulta os gastos realizados no dia     |

A camada de ferramentas funciona como uma ponte entre a interpretação realizada pelo LLM e os casos de uso da aplicação.

---

# 🎙️ Processamento de voz

O projeto possui um fluxo de interação por voz utilizando **Speech-to-Text (STT)** e **Text-to-Speech (TTS)**.

## Fluxo

```text
Áudio
  ↓
Speech-to-Text
  ↓
Texto
  ↓
Spring AI
  ↓
LLM
  ↓
Tool Calling
  ↓
Use Case
  ↓
Resposta
  ↓
Text-to-Speech
  ↓
Áudio
```

A arquitetura utiliza interfaces para reduzir o acoplamento com provedores externos.

```text
SttService
    │
    └── OpenAiSttService

TtsService
    │
    └── OpenAiTtsService
```

Essa abordagem permite substituir ou adicionar provedores futuramente sem alterar as regras de negócio.

---

# Arquitetura

O projeto utiliza conceitos de **Clean Architecture** e **DDD**, buscando manter as regras de negócio independentes de detalhes externos.

```text
src/main/java/com/budgetai
│
├── application
│   ├── dto
│   ├── mapper
│   ├── service
│   └── usecase
│
├── controller
│
├── domain
│   ├── entity
│   ├── repository
│   ├── service
│   └── valueobject
│
├── exception
│
└── infrastructure
    ├── ai
    ├── config
    ├── integration
    ├── persistence
    ├── stt
    ├── tools
    └── tts
```

## Responsabilidade das camadas

| Camada         | Responsabilidade                          |
| -------------- | ----------------------------------------- |
| Controller     | Exposição dos endpoints HTTP              |
| Application    | Orquestração dos casos de uso             |
| Domain         | Regras e conceitos do negócio             |
| Infrastructure | Integrações externas e detalhes técnicos  |
| Persistence    | Comunicação com o banco                   |
| AI             | Integração com modelos de IA              |
| Tools          | Exposição das operações para Tool Calling |

### Regra arquitetural

```text
Controller
    ↓
Application
    ↓
Domain
    ↓
Repository
    ↓
Infrastructure
```

A infraestrutura fornece implementações para as abstrações utilizadas pelo núcleo da aplicação.

---

# Principais casos de uso

Entre os casos de uso implementados estão:

```text
CreateExpenseUseCase
GetExpensesUseCase
GetExpenseByIdUseCase
UpdateExpenseUseCase
DeleteExpenseUseCase
GetExpenseSummaryUseCase

SaveConversationUseCase
GetConversationHistoryUseCase

VoiceChatUseCase
GenerateSpeechUseCase
```

A ideia é manter cada operação de negócio isolada e testável.

---

# Stack tecnológica

| Tecnologia        | Utilização             |
| ----------------- | ---------------------- |
| Java 21           | Linguagem principal    |
| Spring Boot 3.5   | Framework backend      |
| Spring AI         | Integração com IA      |
| OpenAI            | LLM e serviços de voz  |
| Spring Data JPA   | Persistência           |
| PostgreSQL        | Banco de dados         |
| Maven             | Gerenciamento e build  |
| Lombok            | Redução de boilerplate |
| OpenAPI / Swagger | Documentação da API    |
| Docker            | Containerização        |
| JUnit             | Testes automatizados   |
| JaCoCo            | Cobertura de testes    |

---

# 🔌 API

A aplicação disponibiliza endpoints REST para interação com o assistente, gerenciamento de despesas, histórico de conversas e processamento de voz.

> Para a documentação completa e interativa, utilize o Swagger UI.

## Assistente

### `POST /api/assistant/chat`

Envia uma mensagem em linguagem natural.

#### Request

```json
{
  "message": "Gastei 50 reais no supermercado"
}
```

#### Response

```json
{
  "status": "success",
  "response": "Despesa de R$ 50,00 registrada com sucesso."
}
```

#### Exemplo

```bash
curl -X POST http://localhost:8080/api/assistant/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"Gastei 50 reais no supermercado"}'
```

---

## Assistente por voz

### `POST /api/assistant/voice`

Recebe um arquivo de áudio, realiza a transcrição e processa a mensagem utilizando o assistente.

### Formato

```text
multipart/form-data
```

Parâmetro:

```text
file
```

#### Exemplo

```bash
curl -X POST http://localhost:8080/api/assistant/voice \
  -F "file=@caminho/do/audio.mp3"
```

---

# Despesas

## `GET /api/expenses`

Retorna as despesas cadastradas.

```bash
curl -X GET http://localhost:8080/api/expenses
```

---

## `GET /api/expenses/{id}`

Consulta uma despesa específica.

```bash
curl -X GET http://localhost:8080/api/expenses/1
```

Caso o recurso não exista:

```text
HTTP 404 Not Found
```

---

## `POST /api/expenses`

Cria uma nova despesa.

Exemplo:

```json
{
  "amount": 50.00,
  "category": "FOOD",
  "location": "Supermercado"
}
```

---

## `PUT /api/expenses/{id}`

Atualiza uma despesa existente.

```bash
curl -X PUT http://localhost:8080/api/expenses/1
```

---

## `DELETE /api/expenses/{id}`

Remove uma despesa.

```bash
curl -X DELETE http://localhost:8080/api/expenses/1
```

---

## `GET /api/expenses/summary`

Retorna o resumo financeiro.

Exemplo:

```json
{
  "totalAmount": 250.00,
  "categories": {
    "FOOD": 120.00,
    "TRANSPORT": 130.00
  }
}
```

---

# Conversas

## `GET /api/conversations`

Retorna o histórico de interações persistidas entre usuário e assistente.

Exemplo:

```json
[
  {
    "id": 1,
    "userMessage": "Gastei 50 reais no supermercado",
    "assistantResponse": "Despesa registrada com sucesso!",
    "createdAt": "2026-08-25T09:05:00"
  }
]
```

---

# Text-to-Speech

## `POST /api/tts`

Converte texto em áudio.

Exemplo:

```bash
curl -X POST \
  "http://localhost:8080/api/tts?text=Despesa%20registrada%20com%20sucesso" \
  --output audio.mp3
```

A resposta é disponibilizada como:

```text
audio/mpeg
```

---

# Swagger / OpenAPI

Com a aplicação em execução, a documentação interativa pode ser acessada em:

```text
http://localhost:8080/swagger-ui.html
```

O Swagger permite visualizar e testar os endpoints diretamente pelo navegador.

---

# Configuração

## Pré-requisitos

Antes de executar o projeto, tenha instalado:

* Java 21
* Maven
* Docker
* Docker Compose

O PostgreSQL pode ser executado através do Docker Compose disponibilizado pelo projeto.

Também é necessário configurar a chave da API do provedor de IA.

---

# Variáveis de ambiente

A chave da API e as credenciais do banco **não devem ser armazenadas no código-fonte**.

Use `.env.example` como referencia para criar um arquivo `.env` local, que ja esta ignorado pelo Git. O Docker Compose le esse arquivo automaticamente. Ao executar o Spring Boot diretamente, exporte as mesmas variaveis no terminal.

Configure:

```text
POSTGRES_DB=budget_ai
POSTGRES_USER=postgres
POSTGRES_PASSWORD=your_local_database_password
DB_URL=jdbc:postgresql://localhost:5432/budget_ai
DB_USERNAME=postgres
DB_PASSWORD=your_local_database_password
OPENAI_API_KEY=your_api_key
```

## Windows PowerShell

```powershell
$env:POSTGRES_DB="budget_ai"
$env:POSTGRES_USER="postgres"
$env:POSTGRES_PASSWORD="your_local_database_password"
$env:DB_URL="jdbc:postgresql://localhost:5432/budget_ai"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_local_database_password"
$env:OPENAI_API_KEY="your_api_key"
```

## Linux / macOS

```bash
export POSTGRES_DB="budget_ai"
export POSTGRES_USER="postgres"
export POSTGRES_PASSWORD="your_local_database_password"
export DB_URL="jdbc:postgresql://localhost:5432/budget_ai"
export DB_USERNAME="postgres"
export DB_PASSWORD="your_local_database_password"
export OPENAI_API_KEY="your_api_key"
```

> Nunca versione chaves, tokens, senhas ou outros secrets no Git.

---

# Perfis e acesso do navegador

O perfil `dev` e o padrao local. Ele aplica migrations Flyway e valida o schema com Hibernate. O perfil `prod` tambem valida o schema, desabilita SQL nos logs e exige `APP_CORS_ALLOWED_ORIGINS`.

Ative producao explicitamente:

```text
SPRING_PROFILES_ACTIVE=prod
```

O CORS aceita somente as origens configuradas em `APP_CORS_ALLOWED_ORIGINS`. Para multiplas origens, use valores separados por virgula.

As chamadas STT, TTS e de IA usam `OPENAI_CONNECT_TIMEOUT` e `OPENAI_READ_TIMEOUT`. Os valores padrao sao `5s` e `30s`, respectivamente.

---

# Autenticacao

Os endpoints em `/api/**` exigem um token Bearer, exceto os endpoints de autenticacao e a documentacao OpenAPI.

## Cadastro

`POST /api/auth/register`

```json
{
  "email": "user@example.com",
  "password": "a-strong-password"
}
```

## Login

`POST /api/auth/login`

```json
{
  "email": "user@example.com",
  "password": "a-strong-password"
}
```

As duas operacoes retornam `accessToken`. Envie-o nas chamadas protegidas:

```text
Authorization: Bearer <accessToken>
```

Cada despesa e conversa pertence ao usuario identificado pelo token. O identificador do usuario nao e aceito em payloads, tools ou prompts.

---

# Executando localmente

Clone o projeto:

```bash
git clone https://github.com/MariaaPcsa/budget-ai-api.git
```

Entre no diretório:

```bash
cd budget-ai-api
```

Compile o projeto:

```bash
mvn clean install
```

Execute a aplicação:

```bash
mvn spring-boot:run
```

Após a inicialização, a API estará disponível em:

```text
http://localhost:8080
```

---

# Executando com Docker

Inicie os containers:

```bash
docker compose up -d
```

Verifique o ambiente:

```bash
docker compose ps
```

Visualize os logs:

```bash
docker compose logs -f
```

Para encerrar:

```bash
docker compose down
```

---

# Testes

Execute os testes unitários:

```bash
mvn test
```

Para executar a validação completa do projeto:

```bash
mvn clean verify
```

O projeto utiliza **JaCoCo** para acompanhamento da cobertura de testes.

A configuração atual estabelece um limite mínimo de cobertura de código.

---

# Segurança

Atualmente, o projeto utiliza variáveis de ambiente para evitar o versionamento de credenciais externas.

## Estado atual

* Credenciais externas fora do código-fonte.
* Validação de entradas nos endpoints aplicáveis.
* Tratamento global de exceções.
* Resposta `404 Not Found` para recursos inexistentes.

## Próximas evoluções

* [ ] Spring Security
* [ ] Autenticação JWT
* [ ] OAuth2
* [ ] Gerenciamento de usuários
* [ ] Autorização baseada em usuário
* [ ] Rate limiting
* [ ] Gerenciamento seguro de secrets
* [ ] Auditoria
* [ ] Observabilidade de operações sensíveis

---

# Engenharia de IA

O projeto explora alguns conceitos importantes de engenharia de aplicações com LLMs.

## Separação entre IA e domínio

```text
             LLM
              │
              │ interpretação
              ▼
        Tool Calling
              │
              ▼
          Tool Layer
              │
              ▼
           Use Case
              │
              ▼
            Domain
              │
              ▼
         Persistence
```

O LLM não deve ser considerado uma camada de negócio.

Sua responsabilidade é interpretar a linguagem natural e selecionar as ferramentas disponíveis.

A aplicação continua responsável por:

* validação;
* regras de negócio;
* persistência;
* consistência dos dados;
* tratamento de erros.

---

# Documentação complementar

Para evitar que o README concentre toda a documentação técnica, o projeto possui documentos complementares em `docs/`.

```text
docs/
├── current-state-analysis.md
├── technical-backlog.md
└── roadmap.md
```

### Current State Analysis

Contém a análise técnica do estado atual da aplicação, arquitetura e principais fluxos.

### Technical Backlog

Centraliza melhorias técnicas, decisões e pendências arquiteturais.

### Roadmap

Apresenta a evolução planejada do projeto.

---

# Roadmap

## Concluído

* [x] API REST
* [x] CRUD de despesas
* [x] PostgreSQL
* [x] Spring Boot
* [x] Spring AI
* [x] Integração com OpenAI
* [x] Tool Calling
* [x] Assistente financeiro
* [x] Persistência de conversas
* [x] Arquitetura baseada em Clean Architecture/DDD
* [x] STT
* [x] TTS
* [x] Documentação OpenAPI/Swagger
* [x] Testes unitários
* [x] JaCoCo
* [x] Pipeline de CI

## Próximos passos

* [ ] Autenticação e autorização
* [ ] Gestão de usuários
* [ ] Contexto conversacional avançado
* [ ] Streaming de voz
* [ ] Observabilidade
* [ ] Métricas
* [ ] Distributed tracing
* [ ] Dashboard
* [ ] Frontend Web
* [ ] Aplicativo mobile
* [ ] Deploy AWS
* [ ] Infraestrutura como código com Terraform
* [ ] CI/CD completo

---
# Evoluções futuras

Implementação de MCP Server

Como evolução futura, o projeto poderá avaliar a adoção do Model Context Protocol (MCP) para disponibilizar as funcionalidades do backend como ferramentas que possam ser descobertas e utilizadas por clientes de IA compatíveis com MCP.

Atualmente, o projeto utiliza Spring AI e Tool Calling, permitindo que o LLM selecione ferramentas da aplicação para executar operações de negócio. Essa abordagem já estabelece uma separação clara entre a interpretação realizada pela IA e a execução das regras de negócio.

A evolução para MCP será estudada como uma camada de integração padronizada, sem substituir a responsabilidade do domínio ou dos casos de uso.

Visão arquitetural futura

                    🤖 Cliente de IA
                           │
                           ▼
                      MCP Client
                           │
                           ▼
                    MCP Server
                           │
              ┌────────────┼────────────┐
              ▼            ▼            ▼
       Registrar       Consultar      Consultar
         gasto          gastos          resumo
              │            │            │
              └────────────┼────────────┘
                           ▼
                    Budget AI API
                     Spring Boot
                           │
                           ▼
                      PostgreSQL

Possíveis ferramentas

registrar_gasto
consultar_gastos_hoje
consultar_resumo_gastos

A proposta é manter a mesma regra arquitetural do projeto:

A IA interpreta a intenção. A aplicação executa as regras de negócio.

Nesse cenário, o MCP atuaria como uma camada de integração para disponibilizar ferramentas ao cliente de IA, enquanto os Use Cases, Domain e Persistence continuariam responsáveis pela execução das operações.

Status: 🔵 Planejado — evolução futura

# Arquitetura futura

A evolução planejada considera uma arquitetura preparada para execução em ambiente cloud:

```text
                     ┌──────────────────┐
                     │    Web / Mobile  │
                     └────────┬─────────┘
                              │
                              ▼
                     ┌──────────────────┐
                     │   API Gateway    │
                     └────────┬─────────┘
                              │
                              ▼
                     ┌──────────────────┐
                     │  Budget AI API   │
                     │   Spring Boot    │
                     └────────┬─────────┘
                              │
              ┌───────────────┼────────────────┐
              │               │                │
              ▼               ▼                ▼
        PostgreSQL          Redis            OpenAI
              │                                │
              └────────────────┬───────────────┘
                               │
                               ▼
                       Observabilidade
                    Logs / Metrics / Traces
```

Essa arquitetura representa uma **visão futura**, não necessariamente todos os componentes atualmente implementados.

---

# Status do projeto

**Status:** Em desenvolvimento

**Tipo:** Projeto experimental / laboratório de arquitetura

### Principais áreas de estudo

```text
Java
Spring Boot
Spring AI
IA Generativa
LLMs
Tool Calling
Clean Architecture
DDD
REST API
STT / TTS
PostgreSQL
Docker
Testes
DevOps
Cloud
```

---

# Objetivo educacional

O Budget AI API também funciona como laboratório para estudar a integração entre desenvolvimento backend tradicional e aplicações baseadas em Inteligência Artificial.

Os principais conceitos explorados incluem:

* Desenvolvimento backend com Java.
* Spring Boot.
* Arquitetura de software.
* Clean Architecture.
* Domain-Driven Design.
* Spring AI.
* LLMs.
* Tool Calling.
* Engenharia de prompts.
* Integração com APIs externas.
* Processamento de voz.
* Persistência relacional.
* Docker.
* Testes automatizados.
* CI.
* Documentação de APIs.
* Práticas de DevOps e Cloud.

---

#  Autora

**Maria Correia**

Projeto desenvolvido durante a jornada de estudos em:

```text
Java Backend
Spring Boot
Arquitetura de Software
Inteligência Artificial
Cloud
DevOps
```

---

# Licença

Este projeto possui finalidade educacional.

Consulte o arquivo `LICENSE` para conhecer as condições de uso e distribuição.

---

# Contribuição

Sugestões e melhorias são bem-vindas.

Para criar uma nova branch:

```bash
git checkout -b feature/minha-feature
```

Faça suas alterações:

```bash
git add .
git commit -m "feat: implement minha feature"
```

Envie a branch:

```bash
git push origin feature/minha-feature
```

Depois, abra um Pull Request.

---

## 🔗 Referências

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring AI](https://spring.io/projects/spring-ai)
* [OpenAI](https://openai.com/)
* [PostgreSQL](https://www.postgresql.org/)
* [Docker](https://www.docker.com/)
* [OpenAPI](https://www.openapis.org/)
* Clean Architecture
* Domain-Driven Design

# Budget AI API

> API financeira inteligente construída com **Java + Spring Boot + Spring AI**, capaz de interpretar comandos em linguagem natural, registrar despesas, consultar informações financeiras e utilizar ferramentas para executar operações de negócio.

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring AI](https://img.shields.io/badge/Spring%20AI-AI%20Integration-blue)](https://spring.io/projects/spring-ai)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-336791)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Containerization-2496ED)](https://www.docker.com/)
[![OpenAI](https://img.shields.io/badge/OpenAI-LLM-black)](https://openai.com/)

---

## Sobre o projeto

O **Budget AI API** é uma API REST desenvolvida para demonstrar como construir um assistente financeiro utilizando **Inteligência Artificial Generativa integrada ao ecossistema Spring**.

A aplicação permite que o usuário interaja com o sistema utilizando linguagem natural.

Por exemplo:

```text
"Gastei 50 reais no Starbucks"
```

A IA interpreta a intenção do usuário e pode transformar essa informação em uma operação estruturada:

```json
{
  "amount": 50.00,
  "category": "FOOD",
  "location": "Starbucks"
}
```

A partir dessa interpretação, a aplicação pode utilizar ferramentas de negócio para registrar a despesa ou consultar informações financeiras.

---

## Objetivos

O projeto foi desenvolvido com os seguintes objetivos:

* Demonstrar integração entre **Spring Boot e Inteligência Artificial**.
* Explorar o **Spring AI**.
* Utilizar **Tool Calling / Function Calling**.
* Aplicar conceitos de **Clean Architecture**.
* Aplicar conceitos de **DDD**.
* Criar uma API REST organizada e extensível.
* Separar a inteligência artificial das regras de negócio.
* Preparar a aplicação para interação por voz.
* Criar uma base para evolução futura para uma aplicação mobile.

---

## Funcionalidades

### Gestão de despesas

A API permite trabalhar com despesas financeiras e disponibiliza operações como:

* Cadastro de despesas.
* Consulta de despesas.
* Resumo financeiro.
* Classificação de despesas.
* Identificação automática de categorias através da IA.

Categorias podem incluir:

```text
FOOD
TRANSPORT
HEALTH
ENTERTAINMENT
EDUCATION
OTHER
```

---

### Assistente financeiro com IA

O usuário pode enviar comandos em linguagem natural.

Exemplo:

```text
Quanto gastei hoje?
```

A IA interpreta a intenção e pode utilizar uma ferramenta da aplicação para consultar os dados.

Outro exemplo:

```text
Gastei 80 reais no supermercado.
```

A IA pode interpretar:

```text
Valor: R$ 80,00
Categoria: FOOD
Descrição: supermercado
```

e executar a operação de registro através do mecanismo de ferramentas.

---

## Tool Calling

Um dos principais conceitos explorados neste projeto é o **Tool Calling**.

A IA não deve ser responsável pelas regras de negócio.

Ela atua como um **orquestrador**.

Fluxo simplificado:

```text
                  ┌──────────────────┐
                  │      Usuário     │
                  └────────┬─────────┘
                           │
                           ▼
                  ┌──────────────────┐
                  │ Assistant API    │
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
                  │      OpenAI      │
                  └────────┬─────────┘
                           │
                    Tool Calling
                           │
             ┌─────────────┴─────────────┐
             ▼                           ▼
     registrar_gasto            consultar_despesas
             │                           │
             └─────────────┬─────────────┘
                           ▼
                  ┌──────────────────┐
                  │  Domain / App    │
                  │      Layer       │
                  └────────┬─────────┘
                           │
                           ▼
                  ┌──────────────────┐
                  │   PostgreSQL     │
                  └──────────────────┘
```

#### Ferramentas disponíveis

As ferramentas atualmente disponíveis e mapeadas no Spring AI são:

```text
registrar_gasto         - Registra uma nova despesa
consultar_resumo_gastos - Consulta o resumo acumulado geral
consultar_gastos_hoje   - Consulta o total gasto no dia de hoje
```

A vantagem dessa abordagem é manter as decisões da IA separadas das regras determinísticas da aplicação.

---

## Interação por voz

O projeto também possui uma arquitetura preparada para processamento de voz.

Fluxo planejado:

```text
Áudio
  │
  ▼
Speech-to-Text
  │
  ▼
Texto
  │
  ▼
Spring AI
  │
  ▼
Tool Calling
  │
  ▼
Regra de negócio
  │
  ▼
Resposta
  │
  ▼
Text-to-Speech
  │
  ▼
Áudio
```

A implementação possui abstrações para permitir a utilização de diferentes provedores de STT/TTS.

Exemplo:

```text
SttService
    │
    ├── OpenAiSttService
    └── Future providers

TtsService
    │
    ├── OpenAiTtsService
    └── Future providers
```

Essa estratégia reduz o acoplamento com um fornecedor específico.

---

## Arquitetura

O projeto segue princípios de **Clean Architecture** e **Domain-Driven Design**, mantendo separação entre:

* regras de negócio;
* casos de uso;
* interfaces externas;
* infraestrutura;
* integração com IA;
* persistência.

Estrutura principal:

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
    ├── tts
    └── tools
```

---

## Camadas

### Application

Responsável por orquestrar os casos de uso da aplicação.

Exemplos:

```text
CreateExpenseUseCase
GetExpensesUseCase
GetExpenseSummaryUseCase
GetConversationHistoryUseCase
SaveConversationUseCase
VoiceChatUseCase
GenerateSpeechUseCase
```

---

### Domain

Contém as principais regras e conceitos do negócio.

Exemplos:

```text
Expense
Conversation
ExpenseCategory
ExpenseDomainService
ConversationDomainService
ExpenseSummaryService
```

A camada de domínio deve permanecer independente de detalhes de infraestrutura sempre que possível.

---

### Controller

Responsável pela exposição dos endpoints HTTP.

Controllers principais:

```text
AssistantController
ConversationController
ExpenseController
TtsController
VoiceController
```

---

### Infrastructure

Responsável pelas integrações externas e detalhes técnicos.

Inclui:

```text
Spring AI
OpenAI
PostgreSQL
STT
TTS
configurações HTTP
persistência
```

---

## Stack tecnológica

| Tecnologia        | Utilização                     |
| ----------------- | ------------------------------ |
| Java 21           | Linguagem principal            |
| Spring Boot 3.5   | Framework backend              |
| Spring AI         | Integração com IA              |
| OpenAI            | Modelo de IA e serviços de voz |
| Spring Data JPA   | Persistência                   |
| PostgreSQL        | Banco de dados                 |
| Maven             | Gerenciamento de dependências  |
| Lombok            | Redução de boilerplate         |
| OpenAPI / Swagger | Documentação da API            |
| Docker            | Ambiente de execução           |

---

## API

Nesta seção estão listados todos os endpoints da aplicação com instruções de teste detalhadas utilizando `curl` (com o servidor executando localmente em `http://localhost:8080`).

---

### 1. Assistente Financeiro

#### `POST /api/assistant/chat`
Envia uma mensagem de texto em linguagem natural para o assistente. A IA interpretará e usará ferramentas se necessário.

* **Headers:** `Content-Type: application/json`
* **Request Body:**
  ```json
  {
    "message": "quanto gastei hoje?"
  }
  ```
* **Response (200 OK):**
  ```json
  {
    "status": "success",
    "response": "Você gastou hoje R$ 200.00"
  }
  ```
* **Comando de Teste (`curl`):**
  ```bash
  curl -X POST http://localhost:8080/api/assistant/chat \
    -H "Content-Type: application/json" \
    -d '{"message": "Gastei 50 reais no supermercado"}'
  ```

---

#### `POST /api/assistant/voice`
Envia um arquivo de áudio de voz. A aplicação transcreve o áudio para texto (Speech-to-Text) usando Whisper, processa a mensagem no assistente e retorna a transcrição com a resposta por texto.

* **Headers:** `Content-Type: multipart/form-data`
* **Request Params (Form):**
  * `file`: O arquivo de áudio (ex: `.mp3`, `.wav`, `.m4a`).
* **Response (200 OK):**
  ```json
  {
    "transcript": "gastei 50 reais no supermercado",
    "response": "Despesa de R$ 50.00 no supermercado registrada com sucesso na categoria FOOD!"
  }
  ```
* **Comando de Teste (`curl`):**
  *(Substitua `caminho/do/audio.mp3` por um arquivo de áudio real)*
  ```bash
  curl -X POST http://localhost:8080/api/assistant/voice \
    -F "file=@caminho/do/audio.mp3"
  ```

---

### 2. Despesas (Expenses)

#### `GET /api/expenses`
Retorna uma lista com todas as despesas persistidas no banco de dados.

* **Response (200 OK):**
  ```json
  [
    {
      "id": 1,
      "amount": 50.00,
      "category": "FOOD",
      "location": "Supermercado",
      "createdAt": "2026-08-25T09:00:00"
    }
  ]
  ```
* **Comando de Teste (`curl`):**
  ```bash
  curl -X GET http://localhost:8080/api/expenses
  ```

---

#### `GET /api/expenses/summary`
Retorna o resumo financeiro atualizado, incluindo o total geral gasto e a distribuição das despesas por categoria.

* **Response (200 OK):**
  ```json
  {
    "totalAmount": 250.00,
    "categories": {
      "FOOD": 120.00,
      "TRANSPORT": 130.00
    }
  }
  ```
* **Comando de Teste (`curl`):**
  ```bash
  curl -X GET http://localhost:8080/api/expenses/summary
  ```

---

### 3. Histórico de Conversas (Conversations)

#### `GET /api/conversations`
Retorna a lista completa de interações gravadas no histórico entre o usuário e o assistente.

* **Response (200 OK):**
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
* **Comando de Teste (`curl`):**
  ```bash
  curl -X GET http://localhost:8080/api/conversations
  ```

---

### 4. Text-to-Speech (TTS)

#### `POST /api/tts`
Gera um arquivo de áudio falado a partir de um texto fornecido.

* **Request Params (Query):**
  * `text`: O texto a ser transformado em fala (ex: `"Olá, tudo bem?"`).
* **Response (200 OK):**
  * Retorna o arquivo binário em formato `audio/mpeg` (MP3).
* **Comando de Teste (`curl`):**
  ```bash
  curl -X POST "http://localhost:8080/api/tts?text=Despesa%20registrada%20com%20sucesso" \
    --output audio.mp3
  ```

## Swagger

Com a aplicação executando localmente, acesse a interface interativa do Swagger UI para testar as rotas de forma simplificada:

```text
http://localhost:8080/swagger-ui.html
```

### Como testar as rotas no Swagger UI:

#### Testando a rota de Chat (`POST /api/assistant/chat`)
1. Expanda a seção **assistant-controller** e clique no endpoint `POST /api/assistant/chat`.
2. Clique no botão **Try it out** no lado direito.
3. No painel **Request body**, substitua a mensagem de teste por uma de sua preferência (ex: `{"message": "Gastei 50 reais no supermercado"}`).
4. Clique em **Execute** (botão azul). A resposta da IA aparecerá logo abaixo em *Server response*.

#### Testando a rota de Voz (`POST /api/assistant/voice`)
1. Expanda a seção **voice-controller** e clique no endpoint `POST /api/assistant/voice`.
2. Clique no botão **Try it out**.
3. No campo do parâmetro **file**, clique em **Escolher arquivo** e faça o upload de um arquivo de áudio (como `.mp3`, `.wav` ou `.m4a` contendo um comando falado).
4. Clique em **Execute**. A API transcreverá e executará a operação, exibindo a resposta em JSON.

---

## Evolução e Melhorias Implementadas

Para este desafio de projeto, evoluímos a API inteligente implementando as seguintes melhorias:

#### 1. Eliminação de Bypasses da IA (Foco no Tool Calling Real)
* **Antes**: O serviço `AssistantService` interceptava mensagens de consulta como "quanto gastei hoje" de forma rígida através de condicionais Java hardcoded (`message.toLowerCase().contains(...)`). Isso burlava o orquestrador do Spring AI e impedia a interpretação inteligente do modelo para outras formulações da mesma intenção.
* **Depois**: Removemos os desvios hardcoded. Criamos e registramos ferramentas de consulta nativas (`consultar_resumo_gastos` e `consultar_gastos_hoje`) no componente `ExpenseTools`. Agora, todas as mensagens passam pelo LLM que determina, via **Tool Calling**, quando executar a consulta de resumos ou valores diários.

#### 2. Alinhamento de Categorias de Domínio no Prompt
* **Antes**: O prompt de sistema (`SystemPrompts.FINANCIAL_ASSISTANT`) sugeria a categoria `SHOPPING`, que não existe no enum Java `ExpenseCategory` (as categorias reais são `FOOD`, `TRANSPORT`, `HEALTH`, `ENTERTAINMENT`, `EDUCATION`, `OTHER`). Isso causava inconsistências de fallback no backend.
* **Depois**: Ajustamos o prompt de sistema para listar exatamente as categorias do domínio, garantindo que o modelo passe valores válidos e minimizando categorizações errôneas como `OTHER`.

#### 3. Cobertura de Testes Unitários
* Adicionamos testes unitários cobrindo as novas ferramentas e o novo fluxo em [ExpenseToolsTest.java](file:///c:/Users/maria/Desktop/budget-ai-api/src/test/java/com/budgetai/tools/ExpenseToolsTest.java) e [AssistantServiceTest.java](file:///c:/Users/maria/Desktop/budget-ai-api/src/test/java/com/budgetai/application/service/AssistantServiceTest.java). A suíte de testes do projeto continua com 100% de sucesso e atende perfeitamente ao limite mínimo de cobertura definido pelo JaCoCo (mínimo de 50%).

#### 4. Documentação de Arquitetura e Engenharia de IA
* Completamos os arquivos de documentação estruturada do projeto sob a pasta `docs/`:
  - [current-state-analysis.md](file:///c:/Users/maria/Desktop/budget-ai-api/docs/current-state-analysis.md): Mapeamento técnico detalhado do projeto, fluxos de áudio/texto e arquitetura limpa.
  - [technical-backlog.md](file:///c:/Users/maria/Desktop/budget-ai-api/docs/technical-backlog.md): Registro da melhoria de Tool Calling implementada e próximas pendências de arquitetura.
  - [roadmap.md](file:///c:/Users/maria/Desktop/budget-ai-api/docs/roadmap.md): Atualização das fases do roadmap e conclusão da Fase 1 (Stabilize).

#### 5. Implementação de CRUD Completo de Despesas
* Adição das operações fundamentais de gerenciamento de despesas de forma direta no `ExpenseController` utilizando a arquitetura Clean Architecture/DDD:
  * **Criação manual** (`POST /api/expenses`) utilizando DTOs de validação.
  * **Consulta individual por ID** (`GET /api/expenses/{id}`) com lançamento de `ResourceNotFoundException` gerando retorno HTTP `404 Not Found` caso o ID não exista.
  * **Edição de dados** (`PUT /api/expenses/{id}`) integrando a validação de regras de domínio da camada core (`ExpenseDomainService`).
  * **Exclusão física** (`DELETE /api/expenses/{id}`) com verificação prévia de existência.
* Adição de cobertura completa de testes unitários para todos os casos de uso criados e novas rotas do controlador.

---

## Configuração

### Pré-requisitos

Antes de executar o projeto, instale:

* Java 21
* Maven
* Docker
* Docker Compose
* PostgreSQL, caso não utilize o container disponibilizado pelo projeto

Também é necessário possuir uma chave de API do provedor de IA utilizado pela aplicação.

---

## Variáveis de ambiente

Não coloque chaves de API diretamente no código-fonte.

Configure:

```bash
OPENAI_API_KEY=your_api_key
```

#### Windows PowerShell

```powershell
$env:OPENAI_API_KEY="your_api_key"
```

#### Linux / macOS

```bash
export OPENAI_API_KEY="your_api_key"
```

---

## Executando localmente

Clone o projeto:

```bash
git clone https://github.com/MariaaPcsa/budget-ai-api.git
```

Entre no diretório:

```bash
cd budget-ai-api
```

Compile:

```bash
mvn clean install
```

Execute:

```bash
mvn spring-boot:run
```

---

## Executando com Docker

O projeto possui configuração de Docker Compose.

Para iniciar os serviços:

```bash
docker compose up -d
```

Para verificar os containers:

```bash
docker compose ps
```

Para visualizar os logs:

```bash
docker compose logs -f
```

Para parar o ambiente:

```bash
docker compose down
```

---

## Testes

Execute os testes com:

```bash
mvn test
```

Para uma validação completa:

```bash
mvn clean verify
```

---

## Fluxo principal

### Cadastro de uma despesa através da IA

```text
1. Usuário envia mensagem
          │
          ▼
2. AssistantController
          │
          ▼
3. AssistantService
          │
          ▼
4. Spring AI / ChatClient
          │
          ▼
5. LLM interpreta a intenção
          │
          ▼
6. LLM solicita Tool Calling
          │
          ▼
7. ExpenseTools
          │
          ▼
8. CreateExpenseUseCase
          │
          ▼
9. Domain
          │
          ▼
10. Repository
          │
          ▼
11. PostgreSQL
          │
          ▼
12. Resultado
          │
          ▼
13. Resposta ao usuário
```

---

## Princípios arquiteturais

O projeto foi estruturado seguindo alguns princípios importantes.

#### 1. IA não é regra de negócio

O modelo de IA interpreta a intenção.

As regras continuam dentro da aplicação.

```text
IA
 ↓
Intenção
 ↓
Tool
 ↓
Use Case
 ↓
Domain
```

---

#### 2. Baixo acoplamento

Integrações externas são isoladas em infraestrutura.

Por exemplo:

```text
OpenAiSttService
OpenAiTtsService
OpenAiIntegration
```

Isso facilita a substituição futura de fornecedores.

---

#### 3. Separação de responsabilidades

Cada camada possui uma responsabilidade específica.

```text
Controller → HTTP
Application → Use Cases
Domain → Business Rules
Infrastructure → External Systems
```

---

## Segurança

O projeto atualmente utiliza variáveis de ambiente para proteger credenciais externas.

Nunca faça:

```yaml
api-key: sk-xxxxxxxx
```

Prefira:

```yaml
api-key: ${OPENAI_API_KEY}
```

Também é recomendado adicionar futuramente:

* autenticação;
* autorização;
* JWT;
* gerenciamento de usuários;
* rate limiting;
* proteção de endpoints;
* auditoria;
* gerenciamento seguro de secrets.

---

## Roadmap

### MVP

* [x] API REST
* [x] Cadastro de despesas
* [x] Consulta de despesas
* [x] Resumo financeiro
* [x] Assistente com IA
* [x] Spring AI
* [x] Tool Calling
* [x] PostgreSQL
* [x] OpenAPI / Swagger
* [x] Estrutura Clean Architecture / DDD

### Próximas evoluções

* [ ] Autenticação JWT
* [ ] Gestão de usuários
* [ ] Controle de autorização
* [ ] Persistência completa de conversas
* [ ] Melhorias no contexto conversacional
* [ ] STT completo
* [ ] TTS completo
* [ ] Streaming de voz
* [ ] Dashboard financeiro
* [ ] Frontend web
* [ ] Aplicação mobile
* [ ] Observabilidade
* [ ] Métricas
* [ ] Logs estruturados
* [ ] Tracing distribuído
* [ ] CI/CD com GitHub Actions
* [ ] Deploy em AWS
* [ ] Infraestrutura como código com Terraform

---

## Evolução para produção

Uma possível arquitetura futura:

```text
                    ┌───────────────┐
                    │ Mobile / Web  │
                    └───────┬───────┘
                            │
                            ▼
                    ┌───────────────┐
                    │ API Gateway   │
                    └───────┬───────┘
                            │
                            ▼
                    ┌────────────────────┐
                    │   Budget AI API    │
                    │    Spring Boot     │
                    └─────────┬──────────┘
                              │
             ┌────────────────┼────────────────┐
             │                │                │
             ▼                ▼                ▼
        PostgreSQL          Redis          OpenAI
             │                                 │
             │                                 │
             └────────────────┬────────────────┘
                              ▼
                         Observability
                      Logs / Metrics / Traces
```

---

## Possíveis evoluções arquiteturais

Conforme o projeto crescer, algumas decisões podem ser introduzidas gradualmente:

#### Segurança

```text
Spring Security
JWT
OAuth2
Keycloak
```

#### Observabilidade

```text
Micrometer
Prometheus
Grafana
OpenTelemetry
```

#### Infraestrutura

```text
AWS
Docker
Terraform
CI/CD
GitHub Actions
```

#### Performance

```text
Redis
Cache
Connection Pool
Async Processing
```

#### IA

```text
Tool Calling
Structured Output
Prompt Versioning
AI Observability
Fallback Models
Provider Abstraction
```

---

## Status do projeto

**Status:** 🚧 Em desenvolvimento

**Tipo:** Projeto de estudo / laboratório de arquitetura

**Foco principal:**

```text
Java
Spring Boot
Spring AI
IA Generativa
Clean Architecture
DDD
REST API
Voice AI
Tool Calling
```

---

## Objetivo educacional

Este projeto também funciona como laboratório para estudar conceitos modernos de backend e Inteligência Artificial aplicada.

Principais conhecimentos explorados:

* Desenvolvimento de APIs REST.
* Arquitetura de software.
* Clean Architecture.
* Domain-Driven Design.
* Spring Boot.
* Spring AI.
* LLMs.
* Prompt Engineering.
* Tool Calling.
* Integração com APIs externas.
* Processamento de voz.
* Persistência relacional.
* Docker.
* Documentação de APIs.
* Boas práticas de desenvolvimento backend.

---

## Autora

**Maria Correia**

Projeto desenvolvido como parte da jornada de estudos em:

```text
Java Backend
Spring Boot
Arquitetura de Software
Inteligência Artificial
Cloud
DevOps
```

---

## Licença

Este projeto está disponível para fins educacionais.

Consulte o arquivo `LICENSE` para obter as condições de uso e distribuição.

---

### Contribuição

Sugestões, melhorias e ideias são bem-vindas.

Para contribuir:

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

### Referências

* Spring Boot
* Spring AI
* OpenAI API
* PostgreSQL
* Docker
* OpenAPI
* Domain-Driven Design
* Clean Architecture

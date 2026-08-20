# 🤖 Budget AI API

> API financeira inteligente construída com **Java + Spring Boot + Spring AI**, capaz de interpretar comandos em linguagem natural, registrar despesas, consultar informações financeiras e utilizar ferramentas para executar operações de negócio.

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring AI](https://img.shields.io/badge/Spring%20AI-AI%20Integration-blue)](https://spring.io/projects/spring-ai)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-336791)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Containerization-2496ED)](https://www.docker.com/)
[![OpenAI](https://img.shields.io/badge/OpenAI-LLM-black)](https://openai.com/)

---

## 📌 Sobre o projeto

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

# 🎯 Objetivos

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

# ✨ Funcionalidades

## 💰 Gestão de despesas

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
SHOPPING
EDUCATION
OTHER
```

---

## 🧠 Assistente financeiro com IA

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

# 🔧 Tool Calling

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

### Ferramentas disponíveis

A arquitetura foi preparada para ferramentas como:

```text
registrar_gasto
consultar_despesas
resumo_financeiro
```

A vantagem dessa abordagem é manter as decisões da IA separadas das regras determinísticas da aplicação.

---

# 🎤 Interação por voz

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

# 🏗️ Arquitetura

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

# 🧩 Camadas

## Application

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

## Domain

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

## Controller

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

## Infrastructure

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

# 🛠️ Stack tecnológica

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

# 🔌 API

## Assistant

### `POST /api/assistant/chat`

Envia uma mensagem para o assistente financeiro.

### Request

```json
{
  "message": "quanto gastei hoje?"
}
```

### Response

```json
{
  "response": "Você gastou hoje R$ 200.00"
}
```

---

# 💰 Expenses

## `GET /api/expenses`

Retorna as despesas cadastradas.

---

## `GET /api/expenses/summary`

Retorna um resumo financeiro.

Exemplos de informações que podem ser disponibilizadas:

```text
Total gasto
Gastos por categoria
Gastos no período
Quantidade de despesas
```

---

# 📚 Conversation

A aplicação também possui estrutura para armazenamento e consulta do histórico de conversas entre usuário e assistente.

Isso permite evoluir posteriormente para:

* histórico de interações;
* contexto conversacional;
* sessões;
* auditoria;
* análise das interações com a IA.

---

# 📖 Swagger

Com a aplicação executando localmente:

```text
http://localhost:8080/swagger-ui.html
```

O Swagger permite visualizar e testar os endpoints disponíveis.

---

# ⚙️ Configuração

## Pré-requisitos

Antes de executar o projeto, instale:

* Java 21
* Maven
* Docker
* Docker Compose
* PostgreSQL, caso não utilize o container disponibilizado pelo projeto

Também é necessário possuir uma chave de API do provedor de IA utilizado pela aplicação.

---

# 🔐 Variáveis de ambiente

Não coloque chaves de API diretamente no código-fonte.

Configure:

```bash
OPENAI_API_KEY=your_api_key
```

### Windows PowerShell

```powershell
$env:OPENAI_API_KEY="your_api_key"
```

### Linux / macOS

```bash
export OPENAI_API_KEY="your_api_key"
```

---

# ▶️ Executando localmente

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

# 🐳 Executando com Docker

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

# 🧪 Testes

Execute os testes com:

```bash
mvn test
```

Para uma validação completa:

```bash
mvn clean verify
```

---

# 🔄 Fluxo principal

## Cadastro de uma despesa através da IA

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

# 🧠 Princípios arquiteturais

O projeto foi estruturado seguindo alguns princípios importantes.

### 1. IA não é regra de negócio

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

### 2. Baixo acoplamento

Integrações externas são isoladas em infraestrutura.

Por exemplo:

```text
OpenAiSttService
OpenAiTtsService
OpenAiIntegration
```

Isso facilita a substituição futura de fornecedores.

---

### 3. Separação de responsabilidades

Cada camada possui uma responsabilidade específica.

```text
Controller → HTTP
Application → Use Cases
Domain → Business Rules
Infrastructure → External Systems
```

---

# 🔒 Segurança

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

# 📈 Roadmap

## MVP

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

## Próximas evoluções

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

# ☁️ Evolução para produção

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

# 🚀 Possíveis evoluções arquiteturais

Conforme o projeto crescer, algumas decisões podem ser introduzidas gradualmente:

### Segurança

```text
Spring Security
JWT
OAuth2
Keycloak
```

### Observabilidade

```text
Micrometer
Prometheus
Grafana
OpenTelemetry
```

### Infraestrutura

```text
AWS
Docker
Terraform
CI/CD
GitHub Actions
```

### Performance

```text
Redis
Cache
Connection Pool
Async Processing
```

### IA

```text
Tool Calling
Structured Output
Prompt Versioning
AI Observability
Fallback Models
Provider Abstraction
```

---

# 📊 Status do projeto

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

# 🎓 Objetivo educacional

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

# 👩‍💻 Autora

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

# 📄 Licença

Este projeto está disponível para fins educacionais.

Consulte o arquivo `LICENSE` para obter as condições de uso e distribuição.

---

## ⭐ Contribuição

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

## 📚 Referências

* Spring Boot
* Spring AI
* OpenAI API
* PostgreSQL
* Docker
* OpenAPI
* Domain-Driven Design
* Clean Architecture

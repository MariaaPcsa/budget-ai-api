# Current State Analysis

## 1. Visão geral
O **Budget AI API** é um assistente financeiro inteligente desenvolvido com **Spring Boot** e **Spring AI**. Ele permite que usuários enviem comandos em linguagem natural (via texto ou áudio) para cadastrar despesas e consultar resumos de seus gastos, integrando a inteligência dos Large Language Models (LLMs) com regras de negócio e persistência de dados em um banco relacional.

## 2. Stack tecnológica
- **Linguagem**: Java 21
- **Framework**: Spring Boot 3.5.0
- **Integração com IA**: Spring AI 1.0.0 (starter model OpenAI)
- **Banco de Dados**: PostgreSQL (produção/desenvolvimento) / H2 Database (testes unitários/integração)
- **Acesso a dados**: Spring Data JPA / Hibernate
- **Documentação**: Springdoc OpenAPI / Swagger UI (v2.8.8)
- **Utilitários**: Lombok, Java UUID Generator (v5.1.0)
- **Testes e Cobertura**: JUnit 5, Mockito, JaCoCo (mínimo de 50% de cobertura configurado no build)

## 3. Arquitetura atual
A aplicação segue os princípios de **Clean Architecture** e **Domain-Driven Design (DDD)**, prezando pela separação de conceitos e inversão de dependência:

```text
User / Request
     ↓
Controller (REST Endpoints)
     ↓
Application Layer (Use Cases & DTOs)
     ↓
Domain Layer (Entities, Services & Value Objects)
     ↓
Infrastructure Layer (Database, Spring AI, REST Integrations)
```

## 4. Estrutura de pastas
- `com.budgetai.controller`: Ponto de entrada HTTP (requests, responses, validação de entrada).
- `com.budgetai.application`:
  - `dto`: Objeto de transferência de dados.
  - `mapper`: Conversores entre DTOs e Entidades.
  - `service`: Orquestração de chat (`AssistantService`).
  - `usecase`: Casos de uso de negócio específicos e desacoplados.
- `com.budgetai.domain`:
  - `entity`: Entidades de domínio ricas (`Expense`, `Conversation`).
  - `repository`: Interfaces de repositório de domínio.
  - `service`: Lógica de negócio pura e cálculos do domínio (`ExpenseDomainService`, `ExpenseSummaryService`).
  - `valueobject`: Enumerações e VO's (`ExpenseCategory`).
- `com.budgetai.infrastructure`: Configurações técnicas, integrações com APIs externas (STT/TTS OpenAI) e infraestrutura de banco.
- `com.budgetai.tools`: Métodos anotados com `@Tool` expostos para o LLM.

## 5. Domínio
- **Expense**: Representa uma despesa. Contém id (UUID), descrição, valor (BigDecimal), categoria (`ExpenseCategory`), localização geográfica e data de criação.
- **Conversation**: Armazena o histórico de interações (transcrição do áudio/mensagem do usuário e a resposta correspondente gerada pela IA).
- Regra de Negócio: `ExpenseDomainService` valida que despesas devem ter valor obrigatório maior que zero e descrição não nula.

## 6. Application Layer
Contém os casos de uso que orquestram a lógica da aplicação:
- `CreateExpenseUseCase`: Converte o DTO em entidade, valida usando o serviço de domínio e persiste no repositório.
- `GetExpenseSummaryUseCase`: Retorna um DTO com resumo geral de gastos (total gasto, categoria com maior gasto e quantidade total de despesas).
- `VoiceChatUseCase`: Lida com o fluxo ponta a ponta de receber um arquivo de áudio, convertê-lo em texto usando STT, enviar ao `AssistantService` (IA) para obter resposta, salvar a conversação no PostgreSQL e retornar o texto.
- Outros use cases: `GenerateSpeechUseCase` (TTS), `GetExpensesUseCase`, `GetConversationHistoryUseCase`, `SaveConversationUseCase`.

## 7. Infrastructure
Implementa as portas de saída técnicas:
- `OpenAiSttService` e `FutureSttModule`: Lida com a transcrição de voz para texto (Whisper) enviando requisições multipart/form-data HTTP via `RestTemplate` para o endpoint da OpenAI.
- `OpenAiTtsService` e `FutureTtsModule`: Lida com a geração de voz a partir de texto (TTS) via HTTP `RestTemplate`.
- `AiConfiguration`: Define a configuração do `ChatClient` com suporte a Tool Calling.

## 8. API
Endpoints principais expostos:
- `POST /api/voice/chat`: Recebe áudio MultipartFile, processa a intenção e retorna a resposta de texto.
- `POST /api/tts/generate`: Gera arquivo de áudio a partir de um texto enviado.
- `POST /api/expenses`: Criação manual de despesas.
- `GET /api/expenses/summary`: Retorna o resumo geral de gastos.
- `GET /api/conversations/history`: Histórico de mensagens do chat.

## 9. Fluxo da Inteligência Artificial
Quando uma mensagem textual é recebida (via REST ou transcrita do áudio):
1. Ela é encaminhada ao `AssistantService.processMessage()`.
2. O `ChatClient` do Spring AI é instanciado com o prompt padrão `SystemPrompts.FINANCIAL_ASSISTANT` e as ferramentas (`ExpenseTools`) registradas.
3. O LLM analisa o texto e determina se precisa invocar alguma ferramenta (Tool Calling) ou se pode responder diretamente baseando-se no contexto.
4. O resultado é retornado e armazenado na conversação.

## 10. Fluxo do Tool Calling
Estão expostas no `ExpenseTools` as ferramentas do sistema:
- `registrar_gasto`: Registra uma nova despesa no banco de dados.
- `consultar_resumo_gastos`: Executa o caso de uso e fornece o total geral acumulado de gastos, maior categoria e total de despesas.
- `consultar_gastos_hoje`: Fornece o total exato gasto no dia corrente.

## 11. Banco de dados
- PostgreSQL configurado via Docker Compose (`docker-compose.yml`).
- Tabelas auto-criadas pelo Hibernate para fins de desenvolvimento (`update` mode).

## 12. Segurança
- A chave da OpenAI é configurada por `OPENAI_API_KEY`.
- As credenciais do banco sao lidas pelas variaveis `DB_URL`, `DB_USERNAME` e `DB_PASSWORD` na aplicacao, e `POSTGRES_DB`, `POSTGRES_USER` e `POSTGRES_PASSWORD` no Docker Compose. Um `.env.example` documenta os nomes sem conter segredo.
- Qualquer credencial anteriormente versionada deve ser rotacionada.
- A API possui cadastro, login e autenticacao stateless por JWT. Despesas e conversas sao associadas ao usuario autenticado e os repositorios filtram as consultas por proprietario.

## 13. Testes
- Suíte completa de testes unitários usando JUnit 5 e Mockito cobrindo serviços, casos de uso, controladores e componentes de IA (com mocks do ChatClient).

## 14. Docker
- Docker Compose configurado para levantar um container PostgreSQL na porta 5432.

## 15. Observabilidade
- Logs via SLF4J existem nas classes principais.
- Ainda nao ha metricas de latencia, tokens, custo, tool calls, correlacao de requisicao ou alertas para chamadas de IA.
- As integracoes HTTP externas possuem timeouts de conexao e leitura configuraveis; ainda nao ha retry ou circuit breaker.

## 16. Pontos fortes
- Arquitetura limpa muito bem segmentada que isola regras de domínio de frameworks de IA e banco.
- Separação entre intenção (tratada na IA) e execução de regras de negócio (tratada na aplicação e domínio).

## 17. Problemas encontrados e corrigidos
- **Bypass de IA**: O `AssistantService` continha desvios manuais hardcoded via `lower.contains(...)` para retornar os gastos de hoje. Isso foi resolvido criando a tool `consultar_gastos_hoje` no `ExpenseTools` e removendo as condicionais hardcoded, centralizando toda a decisão lógica na IA.
- **Inconsistência de Categorias**: O prompt de sistema mencionava a categoria `SHOPPING`, que não existe no enum de categorias do domínio (`ExpenseCategory`). Ela foi alinhada para evitar que a IA tentasse registrar gastos sob uma categoria que resultaria em fallback para `OTHER`.
- **Falta de Ferramentas de Consulta**: A IA não possuía formas estruturadas de consultar o resumo financeiro dos usuários através do Tool Calling. Foram criadas as ferramentas `consultar_resumo_gastos` e `consultar_gastos_hoje`.

## 18. Dívidas técnicas
- Flyway versiona o schema de usuarios e a associacao de propriedade. A migration de dados legados atribui registros sem dono ao administrador inicial configurado por ambiente.
- Timeouts explicitos foram configurados. Permanecem pendentes tratamento de erros especificos, retry e circuit breaker, que exigem politica de idempotencia para tools de escrita.
- Faltam versionamento de prompts, avaliacao automatizada, workflow, agente limitado e MCP.

## 19. Riscos
- Risco de consumo elevado de tokens e latência devido ao fluxo síncrono de áudio -> STT -> LLM -> Persistência -> Resposta.
- CORS e restrito por allow-list configuravel e perfis dev/prod separam logs SQL. A identidade por JWT isola despesas e conversas; faltam autorizacao por papel e rate limiting.
- O historico de conversas e persistido, mas ainda nao e usado como contexto limitado e isolado nas chamadas ao modelo.

## 20. Funcionalidades faltantes
- Exportação de relatórios financeiros em PDF/CSV.
- AI Harness com contexto, politicas, guardrails e observabilidade.
- Avaliacao de IA, workflows determinísticos, agentes limitados e MCP.

## 21. Top 10 recomendações
1. Implementar autenticação JWT (Spring Security).
2. Adicionar suporte a múltiplos inquilinos/usuários (Multi-tenant).
3. Migrar para controle de versão do banco de dados com Flyway.
4. Adicionar tratamento robusto de retentativas (Retry) nas chamadas à API da OpenAI.
5. Implementar rate-limiting nos endpoints da API para controle de custos e proteção contra ataques.
6. Adicionar documentação em Swagger para os parâmetros específicos da IA.
7. Otimizar processamento de áudio para streaming assíncrono.
8. Criar alertas de orçamento estourado.
9. Implementar cache para respostas estáticas ou cálculos recorrentes.
10. Adicionar métricas Prometheus e Grafana para latência e uso de tokens.
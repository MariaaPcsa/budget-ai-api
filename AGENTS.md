# AGENTS.md

## 1. Contexto do projeto

Este repositório contém o **Budget AI API**, uma API backend desenvolvida em Java e Spring Boot para gerenciamento financeiro integrado com Inteligência Artificial.

O sistema utiliza IA para interpretar comandos em linguagem natural e executar operações financeiras através de mecanismos como **Spring AI e Tool Calling**.

Exemplo:

```text
"Gastei 50 reais no supermercado"
```

A IA deve interpretar a intenção do usuário, mas as regras de negócio devem permanecer na aplicação.

Arquitetura conceitual:

```text
User
  ↓
Controller
  ↓
Application / Use Case
  ↓
Domain
  ↓
Infrastructure
  ↓
Database / External Services
```

---

# 2. Papel da IA

Ao trabalhar neste projeto, você deve atuar como:

* Senior Backend Engineer;
* Software Architect;
* Java/Spring Boot Specialist;
* AI Engineer;
* Code Reviewer.

Você deve priorizar:

1. qualidade;
2. simplicidade;
3. segurança;
4. manutenibilidade;
5. testabilidade;
6. observabilidade;
7. evolução incremental.

---

# 3. Regra principal

## NÃO REESCREVER O PROJETO

Antes de alterar código:

1. entender a implementação existente;
2. identificar o problema;
3. verificar dependências;
4. avaliar impacto;
5. propor solução;
6. implementar de forma incremental;
7. executar testes;
8. validar o resultado.

Nunca substitua uma arquitetura existente simplesmente porque você prefere outra abordagem.

---

# 4. Desenvolvimento incremental

Sempre prefira:

```text
Small Change
    ↓
Test
    ↓
Validate
    ↓
Next Change
```

Evite:

```text
Rewrite Everything
        ↓
Hope It Works
```

---

# 5. Arquitetura

O projeto utiliza conceitos de:

* Clean Architecture;
* Domain-Driven Design;
* SOLID;
* Separation of Concerns;
* Dependency Inversion.

Respeite a separação:

```text
Controller
    ↓
Application
    ↓
Domain
    ↓
Infrastructure
```

Sempre que possível:

### Controller

Responsável por:

* HTTP;
* request;
* response;
* validação de entrada;
* status HTTP.

Não deve conter regras de negócio.

### Application

Responsável por:

* casos de uso;
* orquestração;
* DTOs;
* coordenação das operações.

### Domain

Responsável por:

* regras de negócio;
* entidades;
* value objects;
* serviços de domínio;
* invariantes.

O domínio não deve depender de:

* Spring;
* banco;
* OpenAI;
* HTTP;
* infraestrutura específica.

### Infrastructure

Responsável por:

* PostgreSQL;
* JPA;
* OpenAI;
* Spring AI;
* STT;
* TTS;
* APIs externas;
* configurações técnicas.

---

# 6. Inteligência Artificial

A IA NÃO deve ser considerada responsável pelas regras de negócio.

A separação deve ser:

```text
LLM
 ↓
Interpreta intenção
 ↓
Tool Calling
 ↓
Application
 ↓
Domain
 ↓
Resultado
```

Nunca coloque regras críticas exclusivamente dentro de prompts.

Por exemplo:

```text
ERRADO:

Prompt decide se uma despesa pode ser criada.
```

Preferir:

```text
Prompt interpreta intenção.

Use Case valida operação.

Domain aplica regra.

Repository persiste.
```

---

# 7. Spring AI

Ao modificar a integração com Spring AI, avaliar:

* ChatClient;
* Tool Calling;
* prompts;
* structured output;
* contexto;
* histórico;
* tratamento de erros;
* timeout;
* retry;
* observabilidade;
* custo das chamadas.

Não adicionar funcionalidades de IA apenas porque são tecnicamente interessantes.

Cada mudança deve ter uma justificativa.

---

# 8. Prompts

Prompts devem ser tratados como parte importante do sistema.

Ao modificar prompts:

* preserve o comportamento existente;
* evite instruções conflitantes;
* defina claramente responsabilidades da IA;
* reduza ambiguidades;
* evite permitir que a IA invente informações;
* não coloque secrets em prompts;
* mantenha regras críticas no código.

Quando apropriado, documente a finalidade do prompt.

---

# 9. Tool Calling

Tools devem representar operações reais da aplicação.

Exemplo:

```text
registrarDespesa
consultarDespesas
obterResumoFinanceiro
```

Uma Tool não deve implementar regras complexas diretamente.

Preferir:

```text
Tool
 ↓
Use Case
 ↓
Domain
```

Evitar:

```text
Tool
 ↓
Business Logic
 ↓
Database
```

---

# 10. Java

Utilize Java moderno e idiomático.

Preferir:

* Java 21;
* records quando apropriado;
* enums;
* interfaces com propósito;
* immutable objects quando possível;
* constructor injection;
* `Optional` com bom senso;
* nomes expressivos;
* métodos pequenos.

Evitar:

* classes gigantes;
* métodos gigantes;
* `if/else` excessivos;
* duplicação;
* magic numbers;
* strings mágicas;
* abstrações desnecessárias;
* código morto.

---

# 11. Spring Boot

Utilizar boas práticas do ecossistema Spring.

Preferir:

```java
@Service
@Repository
@RestController
@Configuration
```

quando a responsabilidade realmente justificar o uso.

Utilizar constructor injection.

Evitar:

```java
@Autowired
private SomeService service;
```

quando constructor injection for suficiente.

---

# 12. Tratamento de exceções

Não utilizar:

```java
catch (Exception e)
```

sem uma justificativa clara.

Utilizar exceções específicas.

Centralizar tratamento HTTP quando apropriado através de:

```text
@RestControllerAdvice
```

Respostas de erro devem ser consistentes.

Nunca retornar stack trace para o cliente.

---

# 13. Validação

Validar entradas nas fronteiras da aplicação.

Utilizar Bean Validation quando apropriado:

```text
@NotNull
@NotBlank
@Positive
@Size
@Valid
```

Regras de negócio importantes devem continuar no domínio.

---

# 14. Banco de dados

Ao alterar o modelo de dados:

1. analisar entidades existentes;
2. analisar relacionamentos;
3. verificar queries;
4. avaliar impacto;
5. considerar índices;
6. considerar migrações;
7. atualizar testes.

Não alterar o schema silenciosamente.

---

# 15. API

Ao criar ou alterar endpoints:

* utilizar HTTP verbs corretamente;
* utilizar status HTTP apropriados;
* validar requests;
* utilizar DTOs;
* documentar com OpenAPI;
* evitar expor entidades diretamente;
* considerar compatibilidade.

Antes de remover ou alterar um endpoint existente, verificar possíveis consumidores.

---

# 16. Segurança

Nunca armazenar no código:

```text
API Keys
Passwords
Tokens
Credentials
Secrets
```

Utilizar:

```text
Environment Variables
Secret Managers
Configuration
```

Nunca imprimir secrets em logs.

Se encontrar uma credencial exposta no código:

1. não copiar o valor;
2. informar que existe uma exposição;
3. recomendar rotação da credencial;
4. corrigir a configuração quando autorizado.

---

# 17. Testes

Toda alteração de comportamento deve possuir testes.

Priorizar:

```text
Unit Tests
Integration Tests
Controller Tests
Repository Tests
Domain Tests
AI Integration Tests
```

Os testes devem verificar comportamento.

Não escrever testes apenas para aumentar cobertura.

---

# 18. Testes de IA

Evitar depender exclusivamente de chamadas reais ao provedor de IA.

Sempre que possível:

```text
Mock
Stub
Fake
Deterministic Test
```

Utilizar chamadas reais apenas em testes de integração específicos.

Nunca colocar API keys reais nos testes.

---

# 19. Docker

Ao alterar Docker ou Docker Compose:

verificar:

* build;
* startup;
* healthcheck;
* environment variables;
* networking;
* volumes;
* PostgreSQL;
* dependências.

O ambiente deve ser reproduzível por outro desenvolvedor.

---

# 20. Observabilidade

Quando apropriado, considerar:

```text
Spring Boot Actuator
Micrometer
OpenTelemetry
Prometheus
Grafana
```

Para funcionalidades de IA, considerar métricas como:

```text
latência
erro
quantidade de chamadas
tokens
tempo de resposta
```

Não adicionar ferramentas sem necessidade.

---

# 21. Performance

Antes de adicionar cache, Redis, filas ou processamento assíncrono:

identifique primeiro o problema.

Não introduza infraestrutura apenas porque ela é popular.

Avaliar:

* SQL;
* N+1;
* chamadas externas;
* chamadas duplicadas à IA;
* latência;
* serialização;
* conexões;
* timeouts.

---

# 22. Documentação

Documentação importante deve ficar em:

```text
README.md
docs/
```

Documentos esperados:

```text
docs/
├── current-state-analysis.md
├── technical-backlog.md
└── roadmap.md
```

Esses documentos devem refletir o estado real do projeto.

Não inventar funcionalidades.

---

# 23. Processo de trabalho

Para tarefas importantes, seguir:

```text
ANALYZE
   ↓
SPECIFY
   ↓
PLAN
   ↓
IMPLEMENT
   ↓
TEST
   ↓
REVIEW
```

### Analyze

Entender o código existente.

### Specify

Definir claramente o comportamento esperado.

### Plan

Definir arquivos e alterações necessárias.

### Implement

Implementar a menor mudança necessária.

### Test

Executar testes e validações.

### Review

Revisar arquitetura, segurança e qualidade.

---

# 24. Antes de modificar código

Responder internamente:

```text
Qual problema estou resolvendo?

Onde o problema realmente está?

Existe código existente que já resolve parte do problema?

Qual é o impacto da mudança?

Existe risco de breaking change?

Quais testes precisam ser alterados?

Quais documentos precisam ser atualizados?
```

---

# 25. Não inventar

Nunca inventar:

* endpoints;
* classes;
* métodos;
* tabelas;
* regras de negócio;
* integrações;
* configurações;
* funcionalidades.

Se algo não existir, declarar:

```text
Não identificado na implementação atual.
```

Diferenciar:

```text
IMPLEMENTADO
PARCIALMENTE IMPLEMENTADO
NÃO IMPLEMENTADO
PROPOSTA
```

---

# 26. Prioridades

Ao identificar problemas, classificar:

### P0 — Crítico

Segurança, perda de dados, sistema quebrado ou risco grave.

### P1 — Alta

Problemas arquiteturais ou funcionais importantes.

### P2 — Média

Melhorias relevantes de qualidade.

### P3 — Baixa

Melhorias futuras.

---

# 27. Critérios de conclusão

Uma tarefa só está concluída quando:

* [ ] código compila;
* [ ] testes passam;
* [ ] novos comportamentos possuem testes;
* [ ] nenhuma regressão conhecida foi introduzida;
* [ ] documentação foi atualizada quando necessário;
* [ ] secrets não foram expostos;
* [ ] arquitetura continua coerente;
* [ ] código está formatado;
* [ ] configuração está correta.

---

# 28. Regra de comunicação

Ao concluir uma tarefa, apresentar:

## Alterações

Quais arquivos foram alterados.

## Motivo

Por que foram alterados.

## Implementação

O que foi feito.

## Testes

Quais testes foram executados.

## Resultado

```text
BUILD: PASS/FAIL
TESTS: PASS/FAIL
SECURITY: PASS/FAIL
```

## Próximos passos

O que ainda precisa ser feito.

---

# 29. Regra final

Não tente impressionar adicionando complexidade.

A melhor solução é aquela que:

```text
resolve o problema
+
é fácil de entender
+
é testável
+
é segura
+
pode evoluir
```

Sempre prefira uma solução simples e correta a uma solução complexa e desnecessária.

O objetivo é transformar o Budget AI API gradualmente em uma aplicação **robusta, profissional, testável e preparada para produção**, preservando o conhecimento e o código já existentes.

---

# 30. Práticas de DevOps e Versionamento (Git Flow)

A partir de agora, o projeto deve seguir boas práticas de versionamento. **Não devemos mais commitar diretamente na branch `main`**.

Ao iniciar o desenvolvimento de qualquer nova tarefa, devemos criar uma branch seguindo os padrões abaixo:

### Padrão de Nomenclatura de Branches
- `feat/`: para novas funcionalidades (ex: `feat/adicionar-endpoint-relatorio`).
- `fix/`: para correção de bugs (ex: `fix/erro-no-calculo-despesas`).
- `chore/`: para tarefas de manutenção, dependências ou configurações (ex: `chore/atualizar-spring-boot`).
- `refactor/`: para refatoração de código sem adição de funcionalidade (ex: `refactor/simplificar-controller`).
- `docs/`: para alterações na documentação (ex: `docs/atualizar-readme`).
- `test/`: para adição ou modificação de testes (ex: `test/testes-unitarios-tools`).

### Fluxo de Trabalho (Workflow)
1. **Sincronizar:** Garantir que a branch local está atualizada (`git pull origin main`).
2. **Criar Branch:** Criar a nova branch a partir da `main` (`git checkout -b tipo/nome-da-branch`).
3. **Desenvolver:** Implementar as alterações em pequenos commits, com mensagens claras e semânticas.
4. **Push e Pull Request:** Enviar a branch para o remoto (`git push origin tipo/nome-da-branch`) e realizar o merge via Pull Request (ou merge localmente se aprovado explicitamente).
5. **Deletar:** Após o merge, deletar a branch para manter o repositório limpo.

---

# 31. AI Harness

O AI Harness coordena a integração da aplicação com modelos de IA. Ele não substitui os casos de uso nem contém regras financeiras.

Responsabilidades do Harness:

* selecionar prompt e registrar sua versão;
* montar contexto limitado, rastreável e autorizado;
* aplicar políticas de ferramentas, permissões e confirmações;
* encaminhar handoffs entre workflow, avaliador ou agente;
* registrar latência, modelo, uso de tokens, custo, ferramentas chamadas e falhas;
* aplicar guardrails para prompt injection, saída insegura e chamadas não autorizadas.

Preservar sempre o fluxo:

```text
Entrada
  ↓
AI Harness
  ↓
LLM / Tools
  ↓
Application / Use Case
  ↓
Domain
```

Tools, MCP Tools e agentes são adaptadores para casos de uso existentes. Eles não podem acessar repositórios nem implementar regras de negócio diretamente.

## Contexto e memória

* Contexto conversacional deve ser limitado por janela, associado ao usuário e observável.
* Não enviar histórico, dados financeiros ou instruções de outras conversas sem autorização explícita.
* Memória de longo prazo exige política de retenção, finalidade, remoção e isolamento por usuário antes de ser implementada.

## Workflows, avaliadores e agentes

* Preferir workflows determinísticos quando as etapas forem conhecidas.
* Avaliadores de IA são sinais de qualidade; não autorizam transações nem substituem validações de domínio.
* Um agente só pode ser introduzido para um objetivo que não seja atendido por workflow, com allow-list de tools, limite de passos, timeout, limite de custo e trilha de auditoria.
* Escritas financeiras por agente exigem confirmação humana ou regra explícita aprovada no caso de uso.

## MCP

* MCP Server deve adaptar ferramentas, resources e prompts para a camada Application.
* MCP Client deve aceitar apenas servidores autorizados, com timeout, autenticação, política de permissões e auditoria.
* Nenhum MCP Tool pode expor credenciais, dados de outro usuário ou acesso irrestrito à infraestrutura.

---

# 32. Histórico de evolução de IA

Toda alteração de prompt, tool, avaliação, workflow, agente, MCP ou política do Harness deve registrar:

1. contexto e decisão em ADR quando houver impacto arquitetural;
2. comportamento alterado, versão e compatibilidade;
3. testes, métricas ou casos de avaliação usados como evidência;
4. impacto de segurança, custo e dados;
5. plano de rollback quando a mudança afetar produção.

Os registros ficam em `CHANGELOG.md`, `docs/decisions/` e `docs/ai-change-history.md`. A documentação só pode marcar um recurso como implementado quando houver evidência no código e testes correspondentes.

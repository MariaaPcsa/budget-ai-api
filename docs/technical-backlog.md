# Technical Backlog

## P0 — Crítico (Corrigido)

### BACK-001 — Desvio de LLM por Condicionais Hardcoded e Mapeamento Incompleto de Tools

**Problema:** O `AssistantService` interceptava requisições textuais com desvios manuais em `if/else` (ex.: "quanto gastei hoje") para chamar diretamente o banco de dados. Isso impedia a IA de interpretar e coordenar consultas de forma flexível via Tool Calling se a frase diferisse das strings exatas.

**Impacto:** Quebra do modelo conceitual de agente inteligente. Redução extrema na inteligência do assistente e inconsistência nos fluxos de voz.

**Solução proposta:** Criação das tools de consulta (`consultar_resumo_gastos` e `consultar_gastos_hoje`) expostas ao Spring AI e remoção das condicionais hardcoded do serviço.

**Arquivos afetados:**
- [AssistantService.java](file:///c:/Users/maria/Desktop/budget-ai-api/src/main/java/com/budgetai/application/service/AssistantService.java)
- [ExpenseTools.java](file:///c:/Users/maria/Desktop/budget-ai-api/src/main/java/com/budgetai/tools/ExpenseTools.java)
- [SystemPrompts.java](file:///c:/Users/maria/Desktop/budget-ai-api/src/main/java/com/budgetai/infrastructure/ai/SystemPrompts.java)

**Risco:** Baixo. Foi coberto por testes unitários em `ExpenseToolsTest` e `AssistantServiceTest`.

---

## P1 — Alta prioridade

### BACK-002 — Versionamento de Banco de Dados com Migrações (Flyway)

**Problema:** O schema do banco de dados PostgreSQL é gerado dinamicamente via Hibernate (`spring.jpa.hibernate.ddl-auto=update`), o que é inadequado e perigoso para ambientes de produção.

**Impacto:** Dificuldade na rastreabilidade de mudanças do schema e risco de perda/corrupção de dados em deploys.

**Solução proposta:** Integrar a dependência do Flyway no `pom.xml` e criar scripts SQL versionados em `src/main/resources/db/migration`.

**Arquivos afetados:**
- `pom.xml`
- `src/main/resources/application.properties`
- `src/main/resources/db/migration/V1__create_tables.sql`

**Risco:** Médio (necessário rodar testes com o banco atual).

---

## P2 — Média prioridade

### BACK-003 — Monitoramento de Token e Custo de Chamadas

**Problema:** Atualmente as chamadas para a API OpenAI não têm auditoria ou medição da quantidade de tokens consumidos nem do tempo de resposta (latência).

**Impacto:** Impossibilidade de prever e auditar custos financeiros do provedor de IA.

**Solução proposta:** Configurar o Micrometer / Prometheus para coletar logs e métricas nativas do Spring AI de tokens consumidos por chamada.

**Arquivos afetados:**
- `pom.xml`
- [AiConfiguration.java](file:///c:/Users/maria/Desktop/budget-ai-api/src/main/java/com/budgetai/infrastructure/ai/AiConfiguration.java)

---

## P3 — Baixa prioridade

### BACK-004 — Configuração de Circuit Breaker nas Integrações Externas

**Problema:** A conexão com OpenAI (STT, TTS, Chat) pode falhar por problemas de rede ou cota excedida.

**Impacto:** Interrupção total do processamento sem fallback amigável ou retentativas.

**Solução proposta:** Integrar Spring Cloud Circuit Breaker / Resilience4j para tratar timeout e erros 5xx de forma automática com retries inteligentes.
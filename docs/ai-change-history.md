# Historico de mudancas de IA

Este arquivo e o indice duravel para alteracoes de prompt, tools, contexto, avaliacao, workflows, agentes, MCP e politicas do AI Harness. Registre uma entrada antes ou junto da implementacao; use ADR para decisoes arquiteturais.

## Registro inicial

| Data | Area | Versao | Status | Mudanca | Evidencia | Rollback |
| --- | --- | --- | --- | --- | --- | --- |
| 2026-09-03 | Tool Calling | atual | Implementado | Tools para registrar gasto, consultar resumo e consultar gastos do dia | Testes de `ExpenseTools` e `AssistantService` | Reverter a alteracao que introduziu a tool, preservando use cases |
| 2026-09-03 | AI Harness / MCP | proposta | Planejado | Definida governanca e fronteira arquitetural | ADR-0002 e `docs/ai-harness.md` | Nao aplicavel; sem mudanca de runtime |
| 2026-09-03 | Seguranca de configuracao | atual | Implementado | Credenciais do PostgreSQL passaram a ser lidas de variaveis de ambiente | `mvn test` e `docker compose config` com valores temporarios | Restaurar apenas nomes de variaveis ou valores de ambiente; nunca versionar credenciais |
| 2026-09-03 | Identidade e isolamento | v1 | Implementado | JWT identifica o usuario e todas as consultas de despesas e conversas sao filtradas por proprietario | Testes de use case e `JwtServiceTest` | Remover a feature somente com migration reversa e plano de preservacao de dados |
| 2026-09-03 | Configuracao de integracoes | v1 | Implementado | Perfis dev/prod, CORS por allow-list e timeouts configuraveis para chamadas externas | `CorsConfigTest` e `mvn verify` | Restaurar propriedades por ambiente, sem reintroduzir origem curinga com credenciais |

## Template de nova entrada

| Campo | Obrigatorio |
| --- | --- |
| Data, area, versao e status | Sim |
| Problema e comportamento alterado | Sim |
| Prompt, tool, resource ou contrato afetado | Quando aplicavel |
| Testes, conjunto de avaliacao e metricas | Sim |
| Impacto em seguranca, dados e custo | Sim |
| ADR, PR, changelog e plano de rollback | Quando aplicavel |
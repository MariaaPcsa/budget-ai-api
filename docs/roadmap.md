# Roadmap

## Fase 1 — Stabilize

### Objetivo

Estabilizar o projeto existente e implementar o suporte correto ao Tool Calling para consultas financeiras básicas.

### Prioridades

- [x] Corrigir problemas críticos (Bypass de IA com hardcode removido)
- [x] Corrigir configuração (Alinhamento de categorias do prompt com enums Java)
- [x] Melhorar tratamento de erros (Tratamento seguro de fallbacks na IA)
- [x] Criar testes essenciais (Testes das novas ferramentas unitariamente)
- [x] Atualizar documentação (Análise do estado atual e backlog técnico completos)

---

## Fase 2 — Architecture

### Objetivo

Fortalecer a arquitetura da aplicação.

### Prioridades

- [ ] Integrar Flyway para versionamento estruturado do schema de banco
- [ ] Aplicar isolamento de DTOs nas chamadas de infraestrutura
- [ ] Reduzir acoplamento nas integrações de STT/TTS com interfaces abstratas
- [ ] Introduzir tratamento de exceções global específico para erros de IA externa

---

## Fase 3 — AI Harness Foundation

### Objetivo

Tornar a integracao com IA rastreavel, segura e preparada para evolucao.

### Prioridades

- [x] Definir governanca, ADRs e historico de mudancas de IA
- [ ] Definir contexto de execucao com correlacao, modelo, prompt e tool utilizada
- [ ] Medir latencia, tokens, custo e resultado de tools por requisicao
- [ ] Versionar prompts e registrar criterios de mudanca
- [ ] Configurar timeouts e erros especificos para chamadas externas
- [ ] Otimizar a janela de contexto com isolamento por usuario

---

## Fase 4 — Evaluation and Workflows

### Objetivo

Medir a qualidade de respostas e executar etapas conhecidas de forma deterministica.

### Prioridades

- [ ] Criar conjunto de casos financeiros para regressao de prompt e Tool Calling
- [ ] Avaliar precisao, relevancia, clareza, completude e aderencia a instrucoes
- [ ] Avaliar modelo avaliador como sinal nao autoritativo de qualidade
- [ ] Implementar workflow: classificacao -> tool autorizada -> use case -> validacao -> resposta
- [ ] Usar avaliador-otimizador apenas para respostas nao transacionais

---

## Fase 5 — Bounded Agents

### Objetivo

Avaliar um agente apenas para objetivos que nao sejam resolvidos por workflow.

### Prioridades

- [ ] Criar piloto read-only com allow-list, timeout, limite de passos e custo
- [ ] Registrar trace, handoffs e resultado de cada etapa
- [ ] Exigir confirmacao para qualquer escrita financeira

---

## Fase 6 — MCP

### Objetivo

Expor capacidades autorizadas da aplicacao por Model Context Protocol.

### Prioridades

- [ ] Implementar MCP Server co-localizado adaptando use cases existentes
- [ ] Expor MCP Tools, Resources read-only e Prompts versionados
- [ ] Validar com testes de integracao e MCP Inspector
- [ ] Implementar MCP Client somente para servidores externos aprovados

---

## Fase 7 — Production Ready

### Objetivo

Preparar a aplicação para produção.

### Prioridades

- [ ] Segurança (Adicionar autenticação e isolamento de dados por usuário)
- [ ] Rate limiting (Proteção contra estouro de cotas da OpenAI)
- [ ] Dockerização otimizada em múltiplos estágios (multi-stage builds)
- [ ] Testes de integração automatizados usando Testcontainers com PostgreSQL real
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

## Fase 3 — AI Engineering

### Objetivo

Tornar a integração com IA mais robusta.

### Prioridades

- [ ] Implementar Structured Output para retornos textuais mais previsíveis
- [ ] Configurar retentativas (Retry) com backoff exponencial para API OpenAI
- [ ] Medir latência e uso de tokens por requisição de chat
- [ ] Otimizar tamanho de janelas de contexto enviando apenas histórico recente

---

## Fase 4 — Production Ready

### Objetivo

Preparar a aplicação para produção.

### Prioridades

- [ ] Segurança (Adicionar autenticação e isolamento de dados por usuário)
- [ ] Rate limiting (Proteção contra estouro de cotas da OpenAI)
- [ ] Dockerização otimizada em múltiplos estágios (multi-stage builds)
- [ ] Testes de integração automatizados usando Testcontainers com PostgreSQL real
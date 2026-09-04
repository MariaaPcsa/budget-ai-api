# AI Harness

## Objetivo

O AI Harness e a camada de coordenacao que torna a integracao com LLM observavel, limitada e evolutiva. Ele nao e uma nova camada de dominio e nao possui regras financeiras.

## Estado atual

O fluxo existente e:

```text
Controller -> AssistantService -> ChatClient + Prompt + ExpenseTools -> Use Cases -> Domain -> PostgreSQL
```

O projeto possui Tool Calling, mas ainda nao possui contexto reutilizado pela LLM, registro de versoes de prompt, avaliacao, workflow, agente, MCP ou metricas de custo/tokens.

## Arquitetura alvo

```text
Controller
  -> AI Harness
    -> contexto, prompt, politica de tools, guardrails e observabilidade
    -> LLM e tools autorizadas
      -> Application / Use Cases
        -> Domain
          -> Repositories e servicos externos
```

## Responsabilidades

| Componente | Responsabilidade | Nao pode fazer |
| --- | --- | --- |
| LLM | Interpretar intencao e gerar linguagem | Validar ou persistir regra financeira |
| Tool / MCP Tool | Adaptar um contrato para um use case | Acessar repositorio diretamente |
| AI Harness | Contexto, politicas, rastreio e handoffs | Assumir regras de dominio |
| Workflow | Executar etapas conhecidas | Agir fora da sequencia autorizada |
| Agente | Escolher proxima acao em objetivo aberto | Executar escrita sem limites e confirmacao |
| Domain | Invariantes e regras financeiras | Depender de Spring AI, HTTP ou MCP |

## Regra de decisao

Use workflow quando as etapas forem conhecidas: classificar, selecionar tool permitida, executar use case, validar e responder. Avalie um agente somente quando o objetivo exigir decidir entre proximas acoes e houver beneficio mensuravel sobre um workflow.

Qualquer agente devera iniciar read-only, com tools permitidas explicitamente, limite de passos, timeout, limite de custo, registro de trace e aprovacao para escrita.

## MCP planejado

O MCP Server sera um adaptador interno inicialmente. Tools chamarao use cases autorizados; resources serao somente leitura e filtrados por permissao; prompts serao versionados. O MCP Client sera introduzido apenas quando a API precisar consumir uma capacidade externa confiavel.

## Criterios para cada fase

- Contrato e ownership definidos.
- Testes determinísticos sem dependencia de chamada real ao provedor, quando possivel.
- Metricas e erros observaveis.
- Avaliacao de seguranca, custo, privacidade e compatibilidade.
- Registro em ADR, changelog e historico de IA quando aplicavel.
# ADR-0001: Manter regras financeiras fora da LLM

## Status

Implementado.

## Contexto

A API interpreta comandos financeiros em linguagem natural. A interpretacao nao pode decidir validacoes, persistencia ou invariantes de despesas.

## Decisao

Usar Spring AI para interpretar a intencao e chamar tools. Cada tool adapta a solicitacao a casos de uso existentes; a aplicacao e o dominio continuam responsaveis pela operacao e validacao financeira.

## Alternativas consideradas

- Permitir que o prompt determine regras financeiras: rejeitado por ser nao deterministico e dificil de testar.
- Fazer o controller acessar o banco para responder a comandos: rejeitado por acoplamento e por ignorar a interpretacao da LLM.

## Consequencias

- Tools devem permanecer pequenas e delegar para use cases.
- Mudancas de tool ou prompt precisam de testes de contrato e registro no historico de IA.
- A LLM nao recebe autoridade para ignorar validacoes do dominio.

## Validacao

Os testes de `ExpenseTools` e `AssistantService` cobrem o fluxo atual de Tool Calling.
# ADR-0002: Evoluir por AI Harness e MCP co-localizado inicialmente

## Status

Aceito para planejamento. A implementacao depende de aprovacao de etapa especifica.

## Contexto

O projeto precisa evoluir com contexto, politicas, observabilidade, avaliacao, workflows, agentes e MCP sem deslocar regras financeiras para infraestrutura de IA.

## Decisao

Introduzir um AI Harness na fronteira entre Application e Infrastructure. Quando MCP for implementado, o MCP Server iniciara no mesmo deploy da API e adaptara apenas use cases autorizados. O MCP Client sera uma fase posterior, para consumo de servidores externos aprovados.

## Alternativas consideradas

- Transformar toda a aplicacao em agente: rejeitado; workflows determinísticos sao mais simples e auditaveis para operacoes conhecidas.
- Criar MCP Server separado desde o inicio: adiado; aumenta deploy, autenticacao e testes sem consumidor externo comprovado.

## Consequencias

- O dominio permanece independente de Spring AI e MCP.
- Agentes devem ser limitados, auditaveis e nunca autorizar escritas financeiras por conta propria.
- MCP Tools, Resources e Prompts devem ter contratos, autorizacao e testes de integracao antes de exposicao.

## Validacao

Cada fase devera definir testes unitarios, de integracao e criterios operacionais antes de implementar.
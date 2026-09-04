# Changelog

Todas as mudancas relevantes deste projeto sao registradas neste arquivo.
O formato segue Keep a Changelog e o versionamento adotado sera semantico.

## [Unreleased]

### Added

- Governanca documental para a evolucao do AI Harness.
- Registro de decisoes arquiteturais e historico de mudancas de IA.
- Autenticacao stateless por JWT, cadastro e login de usuarios.
- Isolamento de despesas e conversas pelo usuario autenticado.
- Migracoes Flyway para schema de usuarios e propriedade dos dados.

### Changed

- Credenciais do PostgreSQL foram removidas de configuracoes versionadas e passaram a ser exigidas por variaveis de ambiente.
- Configuracoes de desenvolvimento e producao foram separadas por perfil, com CORS restrito a origens configuradas e timeouts para integracoes HTTP externas.

### Planned

- Observabilidade de chamadas de IA, versionamento de prompts, avaliacao, workflows, agentes limitados e MCP.

## [1.0.0]

### Added

- API financeira com Spring Boot, PostgreSQL e Spring AI.
- Tool Calling para registrar gastos e consultar resumo financeiro.
- Entrada por texto e transcricao de audio.
- Testes automatizados e verificacao de cobertura com JaCoCo.
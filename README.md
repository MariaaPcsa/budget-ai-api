📊 Budget AI API

Sistema inteligente de assistente financeiro com IA + Voz (STT/TTS) + Tools automáticas, construído com Spring Boot + Spring AI.

🚀 Visão do Projeto

O Budget AI API é uma API que funciona como um assistente financeiro inteligente capaz de:
https://docs.spring.io/spring-ai/reference/api/audio/speech.html

Interpretar mensagens em linguagem natural
Identificar e registrar gastos automaticamente
Responder com IA usando Spring AI
Preparar pipeline de voz (Speech-to-Text e Text-to-Speech)
Estrutura pronta para integração com OpenAI, Google e AWS
🧠 Arquitetura

Fluxo principal do sistema:

🎤 Áudio (STT)
      ↓
🧠 Spring AI (LLM + Tools)
      ↓
💾 Persistência (PostgreSQL)
      ↓
🔊 TTS (resposta em áudio)
⚙️ Tecnologias
Java 21
Spring Boot 3.5
Spring AI (ChatClient + Tools)
Spring Data JPA
PostgreSQL
Lombok
OpenAPI (Swagger)
REST API
Arquitetura em camadas (Clean Architecture)
📦 Funcionalidades
💰 Gestão de despesas
Registrar gastos automaticamente
Listar despesas
Resumo financeiro (total + categoria principal)
🧠 IA Financeira
Interpretação de mensagens naturais
Extração de dados estruturados
Execução automática de tools

Exemplo:

"gastei 50 reais no Starbucks"

Resultado:

{
  "amount": 50,
  "category": "FOOD",
  "location": "Starbucks"
}
🎤 STT (Speech-to-Text)

Módulo preparado para:

OpenAI Whisper
Google Speech-to-Text
AWS Transcribe
FutureSttModule.transcribeAudio()
🔊 TTS (Text-to-Speech)

Módulo preparado para:

OpenAI TTS
ElevenLabs
Azure Speech
FutureTtsModule.generateAudio()
🧩 Tools (IA Functions)

A IA pode executar ações automaticamente via tools:

📌 registrar_gasto
@Tool(name = "registrar_gasto")

Permite que a IA registre despesas diretamente no sistema.

🧪 Endpoints
Chat com IA
POST /api/assistant/chat

Request:

{
  "message": "gastei 30 reais no mercado"
}

Response:

{
  "status": "success",
  "response": "Gasto registrado com sucesso"
}
Despesas
GET /api/expenses
GET /api/expenses/summary
🧠 IA Configuração

Configuração Spring AI:

spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        model: gpt-4o-mini


no terminal, defina a variável de ambiente para a chave da API do OpenAI:
export OPENAI_API_KEY="sua_chave_aqui"
```bash

$env:OPENAI_API_KEY = "sua_have_aqui"
mvn spring-boot:run

🏗️ Estrutura do Projeto
com.budgetai
├── application
│   ├── dto
│   ├── service
│   ├── usecase
│   └── mapper
│
├── domain
│   ├── entity
│   ├── repository
│   ├── service
│   └── valueobject
│
├── infrastructure
│   ├── ai
│   ├── config
│   ├── stt
│   ├── tts
│   └── integration
│
├── tools
└── controller

🔐 Variáveis de Ambiente

## ⚙️ Configuração

## application.yml

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/budgetai
    username: postgres
    password: postgres

  jpa:
    hibernate:
      ddl-auto: update

  ai:
    openai:
      api-key: SUA_API_KEY
      chat:
        options:
          model: gpt-4o-mini
```

---

# ▶️ Executando o Projeto

## Instalar dependências

```bash
mvn clean install
```

## Rodar aplicação

```bash
mvn spring-boot:run
```

---

# 📘 Swagger

Acesse:

```bash
http://localhost:8080/swagger-ui.html
```

---

# 🤖 Exemplo de Uso

## Entrada

```text
Gastei 50 reais no Starbucks
```

## Resultado esperado

```json
{
  "amount": 50,
  "category": "FOOD",
  "location": "Starbucks"
}
```

---

# 🧪 Executando Testes

```bash
mvn test
```

OPENAI_API_KEY=your_key_here
OPENAI_MODEL=gpt-4o-mini

🧪 Próximos Passos

 Integração real OpenAI Whisper (STT)
 Integração OpenAI TTS
 Autenticação (JWT + Keycloak)
 Dockerização
 Deploy (AWS / Render / Railway)
 Streaming de voz em tempo real
📌 Status do Projeto

🚧 Em desenvolvimento (MVP funcional com IA + Tools)

👨‍💻 Autor

Projeto desenvolvido como sistema de aprendizado avançado em:

Arquitetura Spring Boot moderna
IA aplicada (Spring AI)
Sistemas conversacionais
Automação financeira inteligente

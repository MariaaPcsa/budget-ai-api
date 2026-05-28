# 💰 Budget AI API

API financeira inteligente desenvolvida com Java, Spring Boot e Spring AI.

O sistema permite registrar despesas através de linguagem natural utilizando IA generativa.

---

# 🚀 Tecnologias

* Java 21
* Spring Boot 3.5
* Spring AI
* OpenAI GPT-4o-mini
* PostgreSQL
* Maven
* Swagger OpenAPI
* JUnit 5
* Mockito
* Lombok

---

# 🧠 Funcionalidades

* Registro inteligente de gastos
* Interpretação de linguagem natural
* Integração com IA generativa
* Extração de dados estruturados
* Resumo financeiro
* Swagger UI
* Arquitetura limpa
* Testes unitários

---

# 📂 Estrutura do Projeto

```bash
src/main/java/com/budgetai
│
├── application
│   ├── dto
│   ├── mapper
│   ├── service
│   └── usecase
│
├── controller
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
│   ├── integration
│   ├── stt
│   └── tts
│
└── tools
```

---

# ⚙️ Configuração

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

---

# 🧠 Integração IA

O sistema utiliza:

* Spring AI
* OpenAI ChatClient
* Tools Calling
* Prompt Engineering

---

# 🔮 Futuras Melhorias

* Speech To Text
* Text To Speech
* Dashboard financeiro
* Categorias automáticas
* Relatórios PDF
* Autenticação JWT
* Histórico de conversas
* Multiusuário

---

# 👨‍💻 Autor

Projeto desenvolvido por Maria Correia.

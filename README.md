📊 Budget AI API














🚀 Intelligent Financial Assistant with AI + Voice

Budget AI API is an intelligent financial assistant built with Spring Boot + Spring AI, capable of understanding natural language, processing voice input, and automatically managing personal finances using AI tools.

It works like a conversational financial agent:

🎤 User speaks or types
🧠 AI interprets intent
💾 System records or queries data
🔊 Response is returned in text (and future audio)

✨ Key Features
💰 Smart Expense Management
Automatic expense registration from natural language
Category detection (FOOD, TRANSPORT, HEALTH, etc.)
Daily and monthly summaries
Structured financial insights
🧠 AI-Powered Assistant
Natural language understanding (Spring AI + GPT)
Tool execution (function calling)
Context-aware responses
Financial reasoning engine
🎤 Voice Interface (STT)
Speech-to-Text integration ready
OpenAI Whisper compatible
Extensible to Google / AWS Speech APIs
🔊 Text-to-Speech (TTS)
AI-generated voice responses
OpenAI TTS integration ready
Extensible voice providers
⚙️ Tool-Based Architecture

AI can directly execute system actions:

registrar_gasto
consultar_despesas
resumo_financeiro
🧠 System Architecture
🏗️ Tech Stack
Java 21
Spring Boot 3.5
Spring AI (ChatClient + Tools)
Spring Data JPA
PostgreSQL
Lombok
OpenAPI / Swagger
RESTful API Architecture
Clean Architecture principles
📦 Core Capabilities
💬 Natural Language Processing
"Gastei 50 reais no Starbucks"

➡️

{
  "amount": 50,
  "category": "FOOD",
  "location": "Starbucks"
}
📊 Financial Insights
Total spent today
Category breakdown
Spending trends
Conversational summaries
🧪 API Endpoints
🤖 Assistant
POST /api/assistant/chat

Request

{
  "message": "quanto gastei hoje?"
}

Response

{
  "response": "Você gastou hoje R$ 200.00"
}
💰 Expenses
GET /api/expenses
GET /api/expenses/summary
⚙️ Configuration
application.yml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}

      chat:
        options:
          model: gpt-4o-mini
          temperature: 0.2
OpenAI Integration Settings
openai:
  api:
    key: ${OPENAI_API_KEY}

    stt-model: whisper-1
    base-url: https://api.openai.com/v1/audio/transcriptions

    tts:
      model: gpt-4o-mini-tts
      voice: alloy
      base-url: https://api.openai.com/v1/audio/speech
▶️ Getting Started
1. Clone the project
git clone https://github.com/MariaaPcsa/budget-ai-api
cd budget-ai-api
2. Set environment variables
export OPENAI_API_KEY=your_key_here

Windows:

$env:OPENAI_API_KEY="your_key_here"
3. Run the application
mvn clean install
mvn spring-boot:run
📘 API Documentation

Swagger UI:

http://localhost:8080/swagger-ui.html
🧪 Testing
mvn test
🧱 Project Structure
com.budgetai
├── application        # Use cases & DTOs
├── domain             # Core business logic
├── infrastructure     # External integrations (AI, DB, STT, TTS)
├── tools              # AI tools/functions
└── controller         # REST endpoints
🧠 AI Design Principles
Tool-based reasoning (Function Calling)
Stateless API design
Domain-driven structure
AI as orchestrator, not logic owner
Externalized intelligence (OpenAI / Spring AI)
🧪 Roadmap
 AI assistant core
 Expense registration
 STT integration (mock/ready)
 TTS integration (mock/ready)
 JWT Authentication
 Keycloak integration
 Docker deployment
 Real-time voice streaming
 Mobile frontend
📌 Project Status

🚧 Active Development — MVP Functional

👨‍💻 Author

Built as a study project focused on:

Modern Spring Boot architecture
AI-powered systems (Spring AI)
Conversational agents
Voice interfaces
Financial automation systems

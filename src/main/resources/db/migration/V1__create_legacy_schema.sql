CREATE TABLE IF NOT EXISTS expenses (
    id UUID PRIMARY KEY,
    description VARCHAR(255),
    amount NUMERIC(38, 2),
    category VARCHAR(255),
    location VARCHAR(255),
    created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tb_conversations (
    id BIGSERIAL PRIMARY KEY,
    user_message TEXT,
    ai_response TEXT,
    created_at TIMESTAMP
);
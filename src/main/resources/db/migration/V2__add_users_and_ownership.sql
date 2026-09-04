CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

ALTER TABLE expenses ADD COLUMN IF NOT EXISTS user_id UUID;
ALTER TABLE tb_conversations ADD COLUMN IF NOT EXISTS user_id UUID;

CREATE INDEX IF NOT EXISTS idx_expenses_user_id ON expenses (user_id);
CREATE INDEX IF NOT EXISTS idx_conversations_user_id ON tb_conversations (user_id);

ALTER TABLE expenses
    ADD CONSTRAINT fk_expenses_user
    FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE tb_conversations
    ADD CONSTRAINT fk_conversations_user
    FOREIGN KEY (user_id) REFERENCES users (id);
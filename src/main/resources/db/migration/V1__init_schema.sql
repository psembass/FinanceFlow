CREATE TABLE users (
                       id          BIGSERIAL PRIMARY KEY,
                       email       VARCHAR(255) NOT NULL UNIQUE,
                       name        VARCHAR(255) NOT NULL,
                       created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE categories (
                            id      BIGSERIAL PRIMARY KEY,
                            name    VARCHAR(100) NOT NULL,
                            type    VARCHAR(10)  NOT NULL CHECK (type IN ('INCOME', 'EXPENSE'))
);

CREATE TABLE transactions (
                              id          BIGSERIAL   PRIMARY KEY,
                              amount      NUMERIC(19,2) NOT NULL CHECK (amount > 0),
                              description VARCHAR(500),
                              date        DATE          NOT NULL,
                              type        VARCHAR(10)   NOT NULL CHECK (type IN ('INCOME', 'EXPENSE')),
                              user_id     BIGINT        NOT NULL REFERENCES users(id),
                              category_id BIGINT        NOT NULL REFERENCES categories(id),
                              created_at  TIMESTAMPTZ    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_date    ON transactions(date);
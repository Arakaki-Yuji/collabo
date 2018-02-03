CREATE TABLE IF NOT EXISTS users (
       id SERIAL PRIMARY KEY,
       email VARCHAR(100) NOT NULL UNIQUE,
       account_name VARCHAR(100) NOT NULL UNIQUE,
       password VARCHAR(100) NOT NULL,
       created_at TIMESTAMP WITH TIME ZONE NOT NULL,
       updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

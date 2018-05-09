CREATE TABLE IF NOT EXISTS issues (
       id SERIAL PRIMARY KEY,
       project_id INTEGER NOT NULL REFERENCES projects(id),
       user_id INTEGER NOT NULL REFERENCES users(id),
       title VARCHAR(255) NOT NULL,
       description TEXT NOT NULL DEFAULT '',
       created_at TIMESTAMP WITH TIME ZONE NOT NULL,
       updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

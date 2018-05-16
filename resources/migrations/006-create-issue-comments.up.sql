CREATE TABLE IF NOT EXISTS issue_comments (
       id SERIAL PRIMARY KEY,
       issue_id INTEGER NOT NULL REFERENCES issues(id),
       user_id INTEGER NOT NULL REFERENCES users(id),
       comment TEXT NOT NULL DEFAULT '',
       created_at TIMESTAMP WITH TIME ZONE NOT NULL,
       updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

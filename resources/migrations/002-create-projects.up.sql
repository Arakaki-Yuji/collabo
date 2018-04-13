CREATE TABLE IF NOT EXISTS projects (
       id SERIAL PRIMARY KEY,
       title VARCHAR(255) NOT NULL,
       description TEXT NOT NULL DEFAULT '',
       crated_at TIMESTAMP WITH TIME ZONE NOT NULL,
       updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS project_owners (
       user_id integer NOT NULL REFERENCES users(id),
       project_id integer NOT NULL REFERENCES projects(id),
       created_at TIMESTAMP WITH TIME ZONE NOT NULL,
       updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
       UNIQUE(user_id, project_id)
);


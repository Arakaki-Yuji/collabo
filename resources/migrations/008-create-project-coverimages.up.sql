CREATE TABLE IF NOT EXISTS project_coverimages (
       project_id integer PRIMARY KEY REFERENCES projects(id) ON DELETE CASCADE,
       filename VARCHAR(100) NOT NULL,
       created_at TIMESTAMP WITH TIME ZONE NOT NULL,
       updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

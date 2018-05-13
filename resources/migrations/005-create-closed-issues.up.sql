CREATE TABLE closed_issues (
  issue_id INTEGER PRIMARY KEY REFERENCES issues(id),
  closed_at TIMESTAMP WITH TIME ZONE NOT NULL
);

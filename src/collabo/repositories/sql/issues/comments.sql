-- :name issue-comments-by-project-ids
-- :doc comments return specified by project-ids
SELECT issue_comments.id, issues.id, projects.id FROM issue_comments
       JOIN issues ON issues.id = issue_comments.issue_id
       JOIN projects ON projects.id = issues.project_id
       WHERE projects.id = :project_id;


-- :name delete-issue-comments-by-project-id :! :n
-- :doc delete comments related to project-id
DELETE FROM issue_comments WHERE id IN (
       SELECT issue_comments.id FROM issue_comments
              JOIN issues ON issues.id = issue_comments.issue_id
              JOIN projects ON projects.id = issues.project_id
              WHERE projects.id = :project_id);


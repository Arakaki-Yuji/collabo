-- :name find-users-by-ids
SELECT * FROM users WHERE id IN (:v*:ids);

-- :name find-one-user-by-id
SELECT * FROM users WHERE id = :id LIMIT 1;

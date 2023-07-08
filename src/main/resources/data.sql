INSERT INTO Users (username, email, password_hash, role, is_active, created_at)
VALUES
('admin', 'admin@example.com', 'password', 'ADMIN', TRUE, CURRENT_TIMESTAMP),
('user', 'user@example.com', 'password', 'USER', TRUE, CURRENT_TIMESTAMP);

INSERT INTO URLS (short_url, original_url, click_count, created_by, updated_by, created_at, updated_at)
VALUES ('goo','https://google.com', 3, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO URLS (short_url, original_url, click_count, created_by, updated_by, created_at, updated_at)
VALUES ('yaa','https://yahoo.com', 6, 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO USER_URL_OWNERSHIP (user_id ,url_id ,created_by ,updated_by ,created_at ,updated_at )
VALUES (1, 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO USER_URL_OWNERSHIP (user_id ,url_id ,created_by ,updated_by ,created_at ,updated_at )
VALUES (1, 2, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO USER_URL_OWNERSHIP (user_id ,url_id ,created_by ,updated_by ,created_at ,updated_at )
VALUES (2, 2, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO Users (username, email, password_hash, role, is_active, created_at)
VALUES
('admin', 'admin@example.com', 'password', 'ADMIN', TRUE, CURRENT_TIMESTAMP),
('user', 'user@example.com', 'password', 'USER', TRUE, CURRENT_TIMESTAMP);

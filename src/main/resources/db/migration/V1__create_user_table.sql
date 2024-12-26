CREATE TABLE IF NOT EXISTS "USER" (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_on DATE NOT NULL,
    is_active BOOLEAN NOT NULL
);
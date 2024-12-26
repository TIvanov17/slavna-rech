CREATE TABLE IF NOT EXISTS "CONNECTION" (
    connection_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    created_on DATE NOT NULL,
    connection_type VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL
);
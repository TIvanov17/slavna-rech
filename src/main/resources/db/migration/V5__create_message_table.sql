CREATE TABLE IF NOT EXISTS MESSAGE (
    message_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    connection_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_on TIMESTAMP NOT NULL,
    is_active BOOLEAN NOT NULL,
    PRIMARY KEY (message_id),
    FOREIGN KEY (sender_id, connection_id) REFERENCES MEMBER(user_id, connection_id)
);
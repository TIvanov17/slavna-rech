CREATE TABLE IF NOT EXISTS MEMBER_MESSAGE_INFO (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    message_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    connection_id BIGINT NOT NULL,
    message_status VARCHAR(255) NOT NULL,
    seen_on_date TIMESTAMP,
    FOREIGN KEY (message_id) REFERENCES MESSAGE(message_id),
    FOREIGN KEY (user_id, connection_id) REFERENCES MEMBER(user_id, connection_id)
);

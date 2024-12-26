CREATE TABLE IF NOT EXISTS MEMBER (
    user_id BIGINT NOT NULL,
    connection_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    join_date DATE NOT NULL,
    is_active BOOLEAN NOT NULL,
    PRIMARY KEY (user_id, connection_id),
    FOREIGN KEY (user_id) REFERENCES "USER"(user_id),
    FOREIGN KEY (connection_id) REFERENCES "CONNECTION"(connection_id),
    FOREIGN KEY (role_id) REFERENCES Role(role_id)
);
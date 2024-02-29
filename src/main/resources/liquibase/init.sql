CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(20) NOT NULL,
                       email VARCHAR(50) NOT NULL,
                       password VARCHAR(120) NOT NULL,
                       UNIQUE KEY username_unique (username),
                       UNIQUE KEY email_unique (email)
);

CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(20) NOT NULL
);

CREATE TABLE user_roles (
                            user_id BIGINT,
                            role_id BIGINT,
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (role_id) REFERENCES roles(id),
                            PRIMARY KEY (user_id, role_id)
);
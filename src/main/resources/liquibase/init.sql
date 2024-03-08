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

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

CREATE TABLE transactions (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              transaction_date BIGINT,
                              transaction_type BIGINT,
                              amount BIGINT,
                              description VARCHAR(255),
                              category_name VARCHAR(255),
                              user_id BIGINT NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE transaction_category_type (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           name VARCHAR(255),
                                           description VARCHAR(255)
);

CREATE TABLE transaction_category_parent (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             name VARCHAR(255),
                                             description VARCHAR(255),
                                             transactionType BIGINT
);

CREATE TABLE transaction_category_child (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            name VARCHAR(255),
                                            description VARCHAR(255),
                                            transaction_category_parent_id BIGINT NOT NULL,
                                            FOREIGN KEY (transaction_category_parent_id) REFERENCES transaction_category_parent(id)
);

INSERT INTO transaction_category_type( name , description) VALUES('Khoản chi','Khoản chi tiêu');
INSERT INTO transaction_category_type( name , description) VALUES('Khoản thu','Khoản thu nhập');
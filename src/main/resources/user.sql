CREATE DATABASE userws ;

USE userws;

CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    user_password VARCHAR(100) NOT NULL,
    user_role ENUM('VISITOR', 'EDITOR', 'ADMIN') NOT NULL
);

INSERT INTO user (user_name, user_password, user_role) VALUES
    ('john', 'password123', 'EDITOR'),
    ('alice', 'pass456', 'ADMIN'),
    ('bob', 'secret789', 'EDITOR');

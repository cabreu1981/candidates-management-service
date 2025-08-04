-- === Tabla de usuarios ===
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- === Tabla de roles ===
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- === Tabla intermedia usuarios-roles (muchos a muchos) ===
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- === Tabla de clientes ===
CREATE TABLE clients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    birth_date DATE NOT NULL
);

-- === Insertar roles ===
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');

-- === Insertar usuario admin (password: admin123) ===
INSERT INTO users (username, password)
VALUES ('admin', '$2a$10$7/GeDgdzvf.7Scy3Avs6LOjLPslvCEhdKOeeMGkbnXKImF2CuvUEW');

-- === Insertar usuario user (password: user123) ===
INSERT INTO users (username, password)
VALUES ('user', '$2a$10$WqkZ30PjXZxVOTaxAxI7feDE0O1p1t9r9k2U7wKvibzVKNZ/hOK4K');

-- === Asociar admin al rol ADMIN ===
INSERT INTO user_roles (user_id, role_id)
VALUES (
    (SELECT id FROM users WHERE username = 'admin'),
    (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')
);

-- === Asociar user al rol USER ===
INSERT INTO user_roles (user_id, role_id)
VALUES (
    (SELECT id FROM users WHERE username = 'user'),
    (SELECT id FROM roles WHERE name = 'ROLE_USER')
);

-- === Insertar clientes ===
INSERT INTO clients (first_name, last_name, age, birth_date)
VALUES ('Carlos', 'Cabrera', 25, '1990-01-01');
INSERT INTO clients (first_name, last_name, age, birth_date)
VALUES ('Juan', 'Perez', 30, '1991-01-01');
INSERT INTO clients (first_name, last_name, age, birth_date)
VALUES ('Pedro', 'Perez', 35, '1992-01-01');
INSERT INTO clients (first_name, last_name, age, birth_date)
VALUES ('Maria', 'Perez', 40, '1993-01-01');
INSERT INTO clients (first_name, last_name, age, birth_date)
VALUES ('Juan', 'Perez', 45, '1994-01-01');
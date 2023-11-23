INSERT INTO users (email, name, password, date_created)
VALUES ('admin@mail.com', 'administrator', '$2a$10$WMbE5Z875BtBmuf6U2IViuzGO/ePB78YjBINW9vNFYDd0aTsKb1oy', '2023-09-17 09:36:26.076091');

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');

INSERT INTO roles (name)
VALUES ('ROLE_USER');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);
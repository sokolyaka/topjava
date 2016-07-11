DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories)
VALUES
  (100000, '1999-01-08 10:23', 'breakfast', 500 ),
  (100000, '1999-01-08 14:25', 'lunch', 500 ),
  (100000, '1999-01-08 18:26', 'dinner', 500 ),
  (100000, '1999-01-09 01:04', 'at night', 600 );
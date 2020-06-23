DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, calories, user_id, datetime) VALUES
('Завтрак', 200, 100000, '2020-01-30 09:20:00'),
('Обед', 800, 100000, '2020-01-30 13:00:00'),
('Ужин', 1000, 100000, '2020-01-30 20:35:00'),
('Еда на граничное значение', 50, 100000, '2020-01-31 00:00:00'),
('Обед', 500, 100000, '2020-01-31 08:15:00'),
('Ужин', 800, 100000, '2020-01-31 14:45:00')


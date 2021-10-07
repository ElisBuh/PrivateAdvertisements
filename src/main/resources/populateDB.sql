DELETE FROM comments;
DELETE FROM photographs;
DELETE FROM advertisements;
DELETE FROM messages;
DELETE FROM users_chats;
DELETE FROM credit_cards;
DELETE FROM roles_users;
DELETE FROM roles;
DELETE FROM addresses;
DELETE FROM countries;
DELETE FROM cities;
DELETE FROM personal_info;
DELETE FROM users;
DELETE FROM chats;
DELETE FROM categories;


ALTER SEQUENCE user_seq RESTART WITH 100000;
ALTER SEQUENCE role_seq RESTART WITH 100000;
ALTER SEQUENCE country_seq RESTART WITH 100000;
ALTER SEQUENCE city_seq RESTART WITH 100000;
ALTER SEQUENCE ad_seq RESTART WITH 100000;
ALTER SEQUENCE chat_seq RESTART WITH 100000;


INSERT INTO roles (name_role)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');

INSERT INTO countries (name)
VALUES ('Беларусь'),
       ('Польша');

INSERT INTO cities (name)
VALUES ('Минск'),
       ('Гродно'),
       ('Варшава'),
       ('Брест');

INSERT INTO addresses (country_id, city_id, post_index, street,  number_house, number_flat)
VALUES (100000, 100001, 23001, 'Пушкина', 12, 39),
       (100000, 100003, 23001, 'Ленина', 12, 39),
       (100001, 100002, 23001, 'Смоленская', 12, 39);

INSERT INTO personal_info (first_name, last_name, date_birthday, number_phone, sex)
VALUES ('Петр', 'Петров', '1989-01-24', 1002003, 'MAN'),
       ('Иван', 'Иванов', '1956-05-24', 3006006, 'MAN'),
       ('Анна', 'Малинина', '2003-04-24', 5008009, 'WOMAN');

INSERT INTO users (login, password, rating, address_id, personal_info_id)
VALUES ('petr@gmail.com','1234',50,100000,100003),
       ('anna@gmail.com','1234',50,100001,100005);

INSERT INTO credit_cards (user_id, type, number)
VALUES (100006,'VISA',4000111122223333),
       (100007,'MASTERCARD',5000444455556666);

INSERT INTO roles_users (user_id, role_id)
VALUES (100006, 100000),
       (100007, 100001);

INSERT INTO chats (name)
VALUES ('Chat');

INSERT INTO messages (user_id, chat_id, content)
VALUES (100006, 100000, 'Hi'),
       (100007, 100000, 'Hello');

INSERT INTO users_chats (chat_id, user_id)
VALUES (100000, 100006),
       (100000, 100007);

INSERT INTO categories (category)
VALUES ('Техника'),
       ('Книги');

INSERT INTO advertisements (title, content, cost, user_id, category_id)
VALUES ('Ноутбук', 'Хороший ноутбук, черный', 1268.25, 100006, 100000),
       ('Война и Мир', 'Интересное чтиво', 10.18, 100007, 100001);

INSERT INTO photographs (ad_id, path)
VALUES (100002, 'foto/Schenker_VIA14_Laptop_asv2021-01.jpg'),
       (100003, 'foto/220px-Толстой_Л._Н._Война_и_мир,_Т._1._Обложка_изд.1912г,Россия.jpg'),
       (100002, 'foto/Schenker_VIA14_Laptop_asv2021-01.jpg'),
       (100003, 'foto/220px-Толстой_Л._Н._Война_и_мир,_Т._1._Обложка_изд.1912г,Россия.jpg');

INSERT INTO comments (ad_id, user_id, content)
VALUES (100002, 100006, 'Хороший товар'),
       (100003, 100007, 'Супер');



-- INSERT INTO books (name_book, name_author, date, price)
-- VALUES ('Война и Мир', 'Лев Толстой', '2020-01-30', 25),
--        ('Горе от ума', 'Александр Грибоедов', '2020-02-12', 12),
--        ('Великий Гэтсби', 'Фрэнсис Скотт Фицджеральд', '2020-01-15', 22),
--        ('Идиот', 'Федор Достоевский', '2020-05-13', 41),
--        ('Грозовой перевал', 'Эмили Бронте', '2020-04-01', 31),
--        ('Маленький принц', 'Сент-Экзюпери А.Д', '2020-06-11', 38),
--        ('Коллекционер', 'Джон Фаулз', '2020-09-25', 69),
--        ('Мастер и Маргарита', 'Михаил Булгаков', '2020-06-06', 73),
--        ('Дон Кихот', 'Мигель де Сервантес', '2020-11-24', 64);
--
-- INSERT INTO storage (book_id)
-- VALUES (100002),
--        (100007),
--        (100005);
--
-- UPDATE books
-- SET status_book  = 'INSTOCK',
--     data_receipt = '2020-10-24'
-- WHERE id = 100002;
--
-- UPDATE books
-- SET status_book  = 'INSTOCK',
--     data_receipt = '2020-05-15'
-- WHERE id = 100007;
--
-- UPDATE books
-- SET status_book  = 'INSTOCK',
--     data_receipt = '2020-4-19'
-- WHERE id = 100005;
--
-- INSERT INTO requests (book_id, count_request)
-- VALUES (100001, 2),
--        (100006, 1);
--
--
-- INSERT INTO orders (name_client, book_id, cost)
-- VALUES ('Роман', 100002, 12),
--        ('Саша', 100007, 69),
--        ('Ира', 100005, 31);
--
-- UPDATE orders
-- SET status_order  = 'COMPLETED',
--     date_complete = '2021-01-24'
-- WHERE book_id = 100002;
--
-- UPDATE orders
-- SET status_order = 'CANCEL'
-- WHERE book_id = 100005;
--
-- INSERT INTO roles (name)
-- VALUES ('ROLE_ADMIN'),
--        ('ROLE_USER');

DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS users_chats;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS photographs;
DROP TABLE IF EXISTS credit_cards;
DROP TABLE IF EXISTS roles_users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS advertisements;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS personal_info;
DROP TABLE IF EXISTS chats;



DROP SEQUENCE IF EXISTS ad_seq;
DROP SEQUENCE IF EXISTS user_seq;
DROP SEQUENCE IF EXISTS role_seq;
DROP SEQUENCE IF EXISTS country_seq;
DROP SEQUENCE IF EXISTS city_seq;
DROP SEQUENCE IF EXISTS chat_seq;

CREATE SEQUENCE user_seq START WITH 100000;
CREATE SEQUENCE role_seq START WITH 100000;
CREATE SEQUENCE country_seq START WITH 100000;
CREATE SEQUENCE city_seq START WITH 100000;
CREATE SEQUENCE chat_seq START WITH 100000;
CREATE SEQUENCE ad_seq START WITH 100000;



CREATE TABLE countries
(
    id   integer DEFAULT nextval('country_seq'),
    name text UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE cities
(
    id   integer DEFAULT nextval('city_seq'),
    name text UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE addresses
(
    id           integer DEFAULT nextval('user_seq'),
    country_id   integer NOT NULL,
    city_id      integer NOT NULL,
    post_index   integer NOT NULL,
    street       text    NOT NULL,
    number_house integer NOT NULL,
    number_flat  integer,
    PRIMARY KEY (id),
    FOREIGN KEY (country_id) REFERENCES countries (id),
    FOREIGN KEY (city_id) REFERENCES cities (id)
);

CREATE TABLE personal_info
(
    id            integer DEFAULT nextval('user_seq'),
    first_name    text      NOT NULL,
    last_name     text      NOT NULL,
    date_birthday timestamp NOT NULL,
    number_phone  integer   NOT NULL,
    sex           text      NOT NULL,
    PRIMARY KEY (id),
    CHECK (sex IN ('FEMALE', 'MALE'))
);

CREATE TABLE users
(
    id               integer   DEFAULT nextval('user_seq'),
    login            text                    NOT NULL,
    password         text                    NOT NULL,
    rating           integer   DEFAULT 100   NOT NULL,
    enabled          bool      DEFAULT true  NOT NULL,
    date_registered  timestamp DEFAULT now() NOT NULL,
    address_id       integer,
    personal_info_id integer,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES addresses (id) ON DELETE CASCADE,
    FOREIGN KEY (personal_info_id) REFERENCES personal_info (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX users_unique_login_idx ON users (login);

CREATE TABLE roles
(
    id        integer DEFAULT nextval('role_seq'),
    name_role text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE roles_users
(
    user_id integer,
    role_id integer,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE credit_cards
(
    id      integer DEFAULT nextval('user_seq'),
    user_id integer NOT NULL,
    type    text    NOT NULL,
    number  DECIMAL NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE categories
(
    id       integer DEFAULT nextval('ad_seq'),
    category text,
    PRIMARY KEY (id)
);


CREATE TABLE advertisements
(
    id                   integer   DEFAULT nextval('ad_seq'),
    title                text           NOT NULL,
    content              text           NOT NULL,
    cost                 decimal(10, 2) NOT NULL,
    date_publication     timestamp DEFAULT now(),
    date_publication_off timestamp,
    status               text      DEFAULT 'NEW',
    user_id              integer,
    category_id          integer,
    top_rating           bool      DEFAULT false,
    date_top_off         timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE photographs
(
    id    integer DEFAULT nextval('ad_seq'),
    ad_id integer NOT NULL,
    path  text    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (ad_id) REFERENCES advertisements (id) ON DELETE CASCADE
);

CREATE TABLE comments
(
    id               integer   DEFAULT nextval('ad_seq'),
    ad_id            integer NOT NULL,
    user_id          integer NOT NULL,
    content          text    NOT NULL,
    date_publication timestamp DEFAULT now(),
    PRIMARY KEY (id),
    FOREIGN KEY (ad_id) REFERENCES advertisements (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE

);

CREATE TABLE chats
(
    id   integer DEFAULT nextval('chat_seq'),
    name text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users_chats
(
    chat_id integer NOT NULL,
    user_id integer NOT NULL,
    FOREIGN KEY (chat_id) REFERENCES chats (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE messages
(
    id          integer   DEFAULT nextval('chat_seq'),
    user_id     integer NOT NULL,
    chat_id     integer NOT NULL,
    content     text    NOT NULL,
    date_create timestamp DEFAULT now(),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (chat_id) REFERENCES chats (id) ON DELETE CASCADE
);

-- CREATE TABLE roles
-- (
--     id   integer DEFAULT nextval('role_id'),
--     name varchar(20) NOT NULL,
--     PRIMARY KEY (id)
-- );
--
-- CREATE TABLE users
-- (
--     id        integer DEFAULT nextval('user_id'),
--     login     text NOT NULL UNIQUE,
--     passwords text NOT NULL,
--     role_id   integer default '100000',
--     PRIMARY KEY (id),
--     Foreign Key (role_id) REFERENCES roles (id)
--
-- )
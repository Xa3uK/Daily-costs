DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users
(
    id     serial PRIMARY KEY,
    name varchar(40) NOT NULL,
    login varchar(20) UNIQUE NOT NULL,
    password varchar(20) NOT NULL,
    balance decimal NOT NULL
);

DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products
(
    id     serial PRIMARY KEY,
    name varchar(100) NOT NULL,
    category varchar(100) NOT NULL,
    price decimal NOT NULL
);

DROP TABLE IF EXISTS purchases CASCADE;
CREATE TABLE purchases
(
    id     serial PRIMARY KEY,
    product_id int NOT NULL,
    date date NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS revenues CASCADE;
CREATE TABLE revenues
(
    id     serial PRIMARY KEY,
    user_id int NOT NULL,
    amount decimal NOT NULL,
    date date NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

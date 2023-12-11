CREATE TABLE IF NOT EXISTS pizza_info (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) UNIQUE,
    price FLOAT(2) NOT NULL,
    ingridients TEXT NOT NULL,
    discription TEXT,
    image BYTEA
);

INSERT INTO pizza_info(name, price, ingridients) VALUES
('pipperony', 500.00, 'tomato cheese'),
('arriva', 544.00, 'cheese arriva-cream'),
('meatballs', 532.00, 'meatballs cheese barbecy-cream');
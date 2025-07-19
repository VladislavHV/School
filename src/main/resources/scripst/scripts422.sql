CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    price NUMERIC NOT NULL
);

CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    age INT NOT NULL,
    has_license BOOLEAN NOT NULL,
    car_id INT,
    CONSTRAINT fk_car FOREIGN KEY (car_id) REFERENCES car(id)
);
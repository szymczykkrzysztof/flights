CREATE TABLE IF NOT EXISTS passenger
(
    id         UUID PRIMARY KEY,
    first_name TEXT        NOT NULL,
    last_name  TEXT        NOT NULL,
    email      TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS flight
(
    id          UUID PRIMARY KEY,
    number      CHAR(6) UNIQUE              NOT NULL,
    origin      CHAR(3)                     NOT NULL,
    destination CHAR(3)                     NOT NULL,
    departure   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    arrival     TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS booking
(
    id        UUID PRIMARY KEY,
    flight    UUID REFERENCES flight (id) ON DELETE CASCADE    NOT NULL,
    passenger UUID REFERENCES passenger (id) ON DELETE CASCADE NOT NULL,
    seat      CHAR(3)                                          NOT NULL,
    price     DECIMAL(7, 2)                                    NOT NULL,
    currency  CHAR(3)                                          NOT NULL,
    created   TIMESTAMP WITHOUT TIME ZONE                      NOT NULL,
    UNIQUE (flight, seat)
);
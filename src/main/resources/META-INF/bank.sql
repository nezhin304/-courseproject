-- CREATE TABLE
--   roles
-- (
--   id          BIGSERIAL             NOT NULL,
--   role        CHARACTER VARYING(10) NOT NULL,
--   customer_id BIGINT                NOT NULL,
--   PRIMARY KEY (id),
--   CONSTRAINT customer_role_fkey FOREIGN KEY (customer_id) REFERENCES "customers" ("id")
--
-- );


CREATE TABLE
  customers
(
  id      BIGSERIAL             NOT NULL,
  name    CHARACTER VARYING(20) NOT NULL,
  surname CHARACTER VARYING(20) NOT NULL,
  phone   CHARACTER VARYING(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (phone)
);

CREATE TABLE
  cards
(
  id              BIGSERIAL             NOT NULL PRIMARY KEY,
  NUMBER          CHARACTER VARYING(19) NOT NULL,
  customer_id     BIGINT                NOT NULL,
  CONSTRAINT customer_card_fkey FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE,
  UNIQUE (NUMBER)
);

CREATE TABLE
  bank_accounts
(
  id      BIGSERIAL             NOT NULL PRIMARY KEY,
  account CHARACTER VARYING(10) NOT NULL,
  deposit NUMERIC(15, 6)        NOT NULL,
  credit  NUMERIC(15, 6)        NOT NULL,
  state   BOOLEAN               NOT NULL,
  card_id BIGINT                NOT NULL,
  CONSTRAINT bank_accounts_card FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE,
  UNIQUE (account),
  UNIQUE (card_id)
);

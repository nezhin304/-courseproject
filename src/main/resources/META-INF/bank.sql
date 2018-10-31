CREATE TABLE
    customers
    (
        id BIGSERIAL NOT NULL,
        name CHARACTER VARYING(20) NOT NULL,
        surname CHARACTER VARYING(20) NOT NULL,
        phone CHARACTER VARYING(20) NOT NULL,
        PRIMARY KEY (id),
        UNIQUE (phone)
    );
    
CREATE TABLE
    roles
    (
        id BIGSERIAL NOT NULL,
        role CHARACTER VARYING(10) NOT NULL,
        customer_id BIGINT NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT customer_role_fkey FOREIGN KEY (customer_id) REFERENCES "customers" ("id")
    );

CREATE TABLE
    cards
    (
        id BIGSERIAL NOT NULL PRIMARY KEY,
        NUMBER CHARACTER VARYING(19) NOT NULL,
        customer_id BIGINT NOT NULL,
        bank_account_id BIGINT NOT NULL,
        CONSTRAINT customer_card_fkey FOREIGN KEY (customer_id) REFERENCES customers(id),
        UNIQUE (NUMBER)
    );

CREATE TABLE
    bank_accounts
    (
        id BIGSERIAL NOT NULL PRIMARY KEY,
        account CHARACTER VARYING(10) NOT NULL,
        deposit NUMERIC(15,6) NOT NULL,
        credit NUMERIC(15,6) NOT NULL,
        card_id BIGINT NOT NULL
    );
    
ALTER TABLE bank_accounts
        ADD CONSTRAINT bank_account_card_fkey
        FOREIGN KEY (card_id)
        REFERENCES cards(id);
        
ALTER TABLE cards
        ADD CONSTRAINT card_bank_account_fkey
        FOREIGN KEY (bank_account_id)
        REFERENCES bank_accounts(id);
        
        
        
        
        
    


create table transactions (
    transaction_id bigint AUTO_INCREMENT NOT NULL primary key,
    account_id bigint NOT NULL,
    operation_type_id int NOT NULL,
    amount double NOT NULL,
    event_date timestamp,
    CONSTRAINT account_id_account_fk
           FOREIGN KEY (account_id)
           REFERENCES accounts (account_id)
           ON UPDATE NO ACTION ON DELETE NO ACTION
);
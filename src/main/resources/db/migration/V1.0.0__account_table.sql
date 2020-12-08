create table accounts (
    account_id bigint AUTO_INCREMENT NOT NULL primary key,
    document_number bigint NOT NULL,
    available_credit_limit double NOT NULL
)
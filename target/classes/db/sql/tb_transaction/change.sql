create table if not exists tb_transaction (
invoice int8 not null,
amount int8 not null,
currency varchar(3) not null,
card_id varchar(255) not null,
cardholder_id varchar(255) not null,
created_at timestamp default CURRENT_TIMESTAMP,
updated_at timestamp,
created_by varchar(255),
updated_by varchar(255),
primary key (invoice)
);
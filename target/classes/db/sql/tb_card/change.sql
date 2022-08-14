create table if not exists tb_card (
pan varchar(255) not null,
expiry varchar(255) not null,
created_at timestamp default CURRENT_TIMESTAMP,
updated_at timestamp,
created_by varchar(255),
updated_by varchar(255),
primary key (pan)
);
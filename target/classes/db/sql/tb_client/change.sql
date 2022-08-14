create table if not exists tb_client (
 email varchar(255) not null,
 name varchar(255) not null,
 created_at timestamp default CURRENT_TIMESTAMP,
 updated_at timestamp,
 created_by varchar(255),
 updated_by varchar(255),
 primary key (email)
);
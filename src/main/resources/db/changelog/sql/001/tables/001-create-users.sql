create table if not exists users(
    id bigserial primary key,
    first_name varchar(64) not null,
    last_name varchar(64) not null,
    birth_date date not null
);
create table users
(
    id         bigserial primary key,
    username   varchar(36) not null,
    email      varchar(36) not null,
    password   varchar(80) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table users_roles
(
    user_id bigserial not null references users (id),
    role_id bigserial not null references roles (id),
    primary key (user_id, role_id)
);

create table posts
(
    id         bigserial primary key,
    user_id    bigserial,
    title      varchar   not null,
    text       varchar   not null,
    image_path varchar not null ,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table subscriptions
(
    id                 bigserial primary key,
    subscribe_username varchar not null,
    created_at         timestamp default current_timestamp,
    updated_at         timestamp default current_timestamp
);

create table users_subscriptions
(
    user_id         bigserial not null references users (id),
    subscription_id bigserial not null references subscriptions (id),
    primary key (user_id, subscription_id)
);

ALTER TABLE posts
    ADD CONSTRAINT FK_USER_ID FOREIGN KEY (user_id) REFERENCES users (id);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');



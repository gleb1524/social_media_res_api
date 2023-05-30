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
    content    varchar not null,
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

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, email, password)
values ('bob', 'bob@mail.com', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i'),
       ('john', 'john@mail.com', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);




create table users
(
    id         bigserial primary key unique,
    username   varchar(36) not null unique,
    email      varchar(36) not null unique,
    password   varchar(80) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    CONSTRAINT id UNIQUE (id)
);

create table roles
(
    id         bigserial primary key unique,
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
    id         bigserial primary key unique,
    user_id    bigserial,
    title      varchar not null,
    text       varchar not null,
    image_path varchar not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table subscriptions
(
    id         bigserial primary key unique,
    user_id    bigserial references users (id),
    friend_id  bigserial references users (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table chats
(
    id           bigserial primary key unique,
    chat_path    varchar not null,
    sender_id    bigserial references users (id),
    recipient_id bigserial references users (id),
    created_at   timestamp default current_timestamp,
    updated_at   timestamp default current_timestamp
);

create table friendships
(
    id           bigserial primary key unique,
    sender_id    bigserial references users (id),
    recipient_id bigserial references users (id),
    created_at   timestamp default current_timestamp,
    updated_at   timestamp default current_timestamp
);

ALTER TABLE posts
    ADD CONSTRAINT FK_USER_ID FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE subscriptions
    ADD CONSTRAINT FK_USER_ID_ON_SUB FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE subscriptions
    ADD CONSTRAINT FK_FRIEND_ID_ON_SUB FOREIGN KEY (friend_id) REFERENCES users (id);

ALTER TABLE chats
    ADD CONSTRAINT FK_SENDER_ID_ON_CHAT FOREIGN KEY (sender_id) REFERENCES users (id);

ALTER TABLE chats
    ADD CONSTRAINT FK_RECIPIENT_ID_ON_CHAT FOREIGN KEY (recipient_id) REFERENCES users (id);

ALTER TABLE friendships
    ADD CONSTRAINT FK_SENDER_ID_ON_FSH FOREIGN KEY (sender_id) REFERENCES users (id);

ALTER TABLE friendships
    ADD CONSTRAINT FK_RECIPIENT_ID_ON_FSH FOREIGN KEY (recipient_id) REFERENCES users (id);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users(id, username, email, password) values (111,'user','user','user'),(222,'friend','friend','friend');
insert into users_roles(user_id, role_id) values (111,1),(222,2);
insert into subscriptions (id, user_id, friend_id) values (1,111,222);
insert into subscriptions (id, user_id, friend_id) values (2,222,111);
insert into friendships (id, sender_id, recipient_id) values (1,111,222);
insert into posts (id, user_id, title, text, image_path) VALUES (1,222,'title','text','resources\3A\00\1A6F4F95E5B9EEAE4B2F626C313C01D3\image.txt');





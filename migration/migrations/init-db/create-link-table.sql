create table links
(
    id          integer primary key generated always as identity,
    link        varchar(255) not null,
    chat_id     integer      not null,
    last_update timestamp    not null,
    state       json,
    foreign key (chat_id) references chats (id),
    unique (chat_id, link)
);
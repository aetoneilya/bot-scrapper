create table chats
(
    id integer primary key generated always as identity,
    chat_id integer not null
);
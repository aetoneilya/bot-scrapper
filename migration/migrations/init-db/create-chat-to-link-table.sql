create table chats_to_links
(
    chat_id integer not null,
    link_id integer not null,
    foreign key (chat_id) references chats (id),
    foreign key (link_id) references links (id),
    unique (chat_id, link_id)
);
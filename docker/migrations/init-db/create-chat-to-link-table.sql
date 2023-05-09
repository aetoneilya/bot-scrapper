create sequence if not exists chats_to_links_seq start 1 increment 1;

create table chats_to_links
(
    id      integer primary key default nextval('chats_to_links_seq'),
    chat_id integer not null,
    link_id integer not null,
    foreign key (chat_id) references chats (id) on delete cascade,
    foreign key (link_id) references links (id) on delete cascade,
    unique (chat_id, link_id)
);

create or replace function remove_unreferenced_links() returns trigger as
$$
begin
    delete from links where not exists (select from chats_to_links where chats_to_links.link_id = links.id);
    return null;
end;
$$ language plpgsql;

create trigger remove_unreferenced_links
    after delete
    on chats_to_links
execute procedure remove_unreferenced_links();
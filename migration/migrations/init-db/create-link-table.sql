create sequence if not exists link_seq start 1 increment 1;

create table links
(
    id          integer primary key default nextval('link_seq'),
    link        varchar(255) not null unique,
    last_update timestamp    not null,
    state       json
);
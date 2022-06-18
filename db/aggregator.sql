create database aggregator;

create table if not exists post (
    id serial primary key,
    name text,
    description text,
    link text unique,
    created timestamp
);
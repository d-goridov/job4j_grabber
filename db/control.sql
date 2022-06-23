CREATE TABLE company
(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

insert into company(id, name) values (1, 'ibm');
insert into company(id, name) values (2, 'intel');
insert into company(id, name) values (3, 'amd');
insert into company(id, name) values (4, 'nvidia');
insert into company(id, name) values (5, 'microsoft');

insert into person(id, name, company_id) VALUES (1, 'Petr', 1);
insert into person(id, name, company_id) VALUES (2, 'Oleg', 1);
insert into person(id, name, company_id) VALUES (3, 'Olga', 2);
insert into person(id, name, company_id) VALUES (4, 'Maria', 3);
insert into person(id, name, company_id) VALUES (5, 'Ivan', 3);
insert into person(id, name, company_id) VALUES (6, 'Nikolay', 2);
insert into person(id, name, company_id) VALUES (7, 'Nikita', 4);
insert into person(id, name, company_id) VALUES (8, 'Elena', 5);
insert into person(id, name, company_id) VALUES (9, 'Boris', 5);
insert into person(id, name, company_id) VALUES (10, 'Maxim',3);
insert into person(id, name, company_id) VALUES (11, 'Zaur', 2);


select p.name, c.name from person p join company c on c.id = p.company_id
where company_id <> 5;

select c.name, count(*)  from company c
join person p on c.id = p.company_id
group by c.name
having count(*) = (select count(*)
from person p
group by company_id
order by count(*) desc
limit 1);
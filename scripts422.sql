create table drivers
(
    id         bigint primary key,
    name       varchar(256) not null,
    age        integer      not null check ( age > 0 ),
    hasLicence boolean      not null default false,
    car_id     bigint references cars (id) not null
);


create table cars
(
    id    bigint primary key,
    brand varchar(256),
    model varchar(256),
    price numeric
);

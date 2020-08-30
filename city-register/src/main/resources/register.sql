-- 1. Район
-- 2. Улица
-- 3. Адрес - район, улица, дом, корпус, квартира
-- 4. Персона - ФИО, дата рождения, Паспорт(серия/номер/дата выдачи), свидетльство о рождении(номер/дата выдачи)
-- 5. Связь персоны и адреса - Персона, Адрес и диапазон дат, вид регистрации

drop table if exists cr_address_person;
drop table if exists cr_person;
drop table if exists cr_address;
drop table if exists cr_district;
drop table if exists cr_street;

create table cr_district(
    district_code integer  not null,
    district_name varchar(300),
    PRIMARY KEY (district_code)
);

insert into cr_district (district_code, district_name)
values (1, 'Выборгский');

create table cr_street
(
    street_code integer not null,
    street_name varchar(300),
    PRIMARY KEY (street_code)
);

insert into cr_street (street_code, street_name)
values (1, 'Сампоньевский проспект');

create table cr_address(
    address_id SERIAL,
    district_code integer  not null,
    street_code integer not null,
    building           varchar(10)  not null,
    extension          varchar(10),
    apartment          varchar(10),
    primary key (address_id),
    foreign key (district_code) references cr_district (district_code) on DELETE restrict,
    foreign key (street_code) references cr_street (street_code) on DELETE restrict
);

insert into cr_address (street_code, district_code, building, extension, apartment)
values (1, 1, '10', '2', '121'),
(1, 1, '274', null , 4 );


create table cr_person(
    person_id SERIAL,
    sur_name           varchar(100) not null,
    given_name         varchar(100) not null,
    patronymic         varchar(100) not null,
    date_of_birth      date         not null,
    passport_series    varchar(10),
    passport_number    varchar(10),
    passport_date      date,
    certificate_number varchar(10),
    certificate_date   date,
    primary key (person_id)
);

insert into cr_person (sur_name, given_name, patronymic, date_of_birth,
                        passport_series, passport_number, passport_date,
                        certificate_number, certificate_date)
values ('Петров', 'Андрей', 'Сергеевич', '1990-05-25', '1234', '123456', '2016-06-03', null, null),
        ('Петрова', 'Ирина', 'Викторовна', '1998-06-12', '5678', '456789', '2014-6-25', null, null),
        ('Петров', 'Павел', 'Андреевич', '2019-02-01', null, null, null, '456123' , '2019-02-03'),
        ('Петрова', 'Ольга', 'Андреевна', '2020-03-14', null, null, null, '798123' , '2020-03-16');


create table cr_address_person(
    person_address_id SERIAL,
    address_id integer not null,
    person_id integer not null,
    start_date date not null,
    end_date date,
    temporal boolean default false,
    foreign key (address_id) references cr_address (address_id) on DELETE restrict,
    foreign key (person_id) references cr_person (person_id) on DELETE restrict
);

insert into cr_address_person (address_id, person_id, start_date, end_date)
values (1, 1, '2010-05-28', null ),
        (2, 2, '2016-07-22', null ),
        (1, 3, '2019-02-05', null ),
        (1, 4, '2020-03-19', null );
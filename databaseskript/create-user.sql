create database if not exists MytestDB;
use MytestDB;
create table if not EXISTS users
(
    Id              integer UNIQUE auto_increment,
    FullName        varchar(255),
    PhoneNumber     varchar(255),
    LoginName       varchar(255),
    Password        varchar(255),
    CONSTRAINT U_User_ID_PK PRIMARY KEY (Id)
);

create table User_roles (
  LoginName         varchar(255) not null,
  role_name         varchar(255) not null,
  primary key (LoginName, role_name)
);

#inserter en record av en bruker inn i databasen otra.
insert into users (FullName,
                       PhoneNumber,
                       LoginName,
                       Password)
values (
        'Abdul Rahman Kasim',
        '41384997',
        'Abdul00',
        '12345');

insert into users (FullName,
                       PhoneNumber,
                       LoginName,
                       Password)
values (
        'AbdulKasim',
        '98643913',
        'Abduladmin',
        '123');

insert into user_roles (LoginName, role_name) values ('Abdul00','user');
insert into user_roles (LoginName, role_name) values ('Abduladmin','administrator');
insert into user_roles (LoginName, role_name) values ('Abduladmin','user');



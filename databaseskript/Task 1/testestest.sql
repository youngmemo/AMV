create database if not exists MytestDB;
use MytestDB;
create table if not EXISTS ansatt
(
    Id            integer UNIQUE auto_increment,
    Fornavn       varchar(255),
    Etternavn     varchar(255),
    Epost         varchar(255),
    Adresse       varchar(255),
    Post_Nummer   varchar(10),
    Ansatt_Nummer varchar(25),
    Passord       varchar(255),
    CONSTRAINT PRIMARY KEY (Id)
);

insert into ansatt (Fornavn,
                    Etternavn,
                    Epost,
                    Adresse,
                    Post_Nummer,
                    Ansatt_Nummer,
                    Passord)
values (
           'Mehmet', 'Eksi', 'mehmeteksi99@hotmail.com', 'Løvsangerveien 28A', '4626', '415', 'GamlePassord123');


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
           'Mehmet Eksi',
           '98460882',
           'memouser',
           '123');

insert into users (FullName,
                   PhoneNumber,
                   LoginName,
                   Password)
values (
           'Memo Exi',
           '92858332',
           'memoadmin',
           '123');

insert into user_roles (LoginName, role_name) values ('memouser','user');
insert into user_roles (LoginName, role_name) values ('memoadmin','administrator');
insert into user_roles (LoginName, role_name) values ('memoadmin','user');

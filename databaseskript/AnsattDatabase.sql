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

insert into ansatt (Id, Fornavn, Etternavn, Epost, Adresse, Post_Nummer, Ansatt_Nummer, Passord)
values ('','Mehmet', 'Eksi', 'Mehmeteksi99@hotmail.com', 'Løvsangerveien 28A', '4626', '415', 'GamlePassord123');


create database if not exists MytestDB;
use MytestDB;
create table if not EXISTS Ansatt
(
    Id              integer UNIQUE auto_increment,
    Fornavn         varchar(255),
    Etternavn       varchar(255),
    Epost           varchar(255),
    Adresse         varchar(255),
    Post_Nummer     varchar(10),
    Ansatt_Nummer   varchar(25),
    Passord         varchar(255),
    CONSTRAINT U_Ansatt_ID_PK PRIMARY KEY (Id)

);

INSERT INTO Ansatt (Fornavn, Etternavn, Epost, Adresse, Post_Nummer, Ansatt_Nummer, Passord)
values ('Abdul Rahman', 'Kasim', 'Abdulrq@uia.no', 'Tranestien9', '4626', '99', 'Abdul123');
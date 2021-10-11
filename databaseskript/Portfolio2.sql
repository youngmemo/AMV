create database if not exists MytestDB;
use MytestDB;
create table if not EXISTS Lisensiertutstyr
 (
                        Lisens_ID integer UNIQUE auto_increment NOT NULL ,
                        Utstyr_kommentar varchar (30) NOT NULL ,
                        Utstyr_ID integer (30) NOT NULL ,
                        PRIMARY KEY (Lisens_ID),
                        FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID)

);
insert into Lisensiertutstyr(Lisens_ID, Utstyr_kommentar, Utstyr_ID)
values ('99', 'Utstyret er i god stand', '10');

create table Administrator (

    Ansatt_ID integer UNIQUE auto_increment NOT NULL,
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt (Ansatt_ID)


);

insert into Administrator(Ansatt_ID)
values ('20')
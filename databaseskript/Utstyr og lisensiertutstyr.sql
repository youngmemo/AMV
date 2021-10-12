create database if not exists MytestDB;
use MytestDB;
create table if not EXISTS Utstyr
(
    Utstyr_ID               integer UNIQUE auto_increment,
    Utstyr_Navn             varchar(50) NOT NULL,
    Utstyr_Beskrivelse      varchar(50) NOT NULL,
    Kategori_ID             integer NOT NULL,
    PRIMARY KEY (Utstyr_ID),
    FOREIGN KEY (Kategori_ID) REFERENCES Kategori(Kategori_ID)
);

INSERT INTO Utstyr (Utstyr_Navn, Utstyr_Beskrivelse, Kategori_ID) values('Eksentersliper', 'børsteløs motor som gjør den vedlikeholdsfri og gir den lengre levetid og battertid.', '1');
INSERT INTO Utstyr (Utstyr_Navn, Utstyr_Beskrivelse, Kategori_ID) values('Båndsliper', 'denne effektive båndsliperen garanterer et jevnt og pent resultat. 650W med en bånddimensjon på 76x457 mm.', '1');
INSERT INTO Utstyr (Utstyr_Navn, Utstyr_Beskrivelse, Kategori_ID) values('vinkelsliper', '150 mm batteridrevet vinkelsliper med turtallsvalg og økt brukerbeskyttelse', '1');
INSERT INTO Utstyr (Utstyr_Navn, Utstyr_Beskrivelse, Kategori_ID) values('Meislemaskin', 'topp ytelse ved boring og meisling med 1500 wats motor og 12,5 joule enkeltslagenergi', '1');
INSERT INTO Utstyr (Utstyr_Navn, Utstyr_Beskrivelse, Kategori_ID) values('Slagdrill', 'Slagdrill med på 18 V med 13 mm hurtigchuch og batteriindikator', '1');
INSERT INTO Utstyr (Utstyr_Navn, Utstyr_Beskrivelse, Kategori_ID) values('Kantklipper', 'elektrisk kantklipper som hjelper deg med de utfordrende kantene langs veier, trær,murer', '1');
INSERT INTO Utstyr (Utstyr_Navn, Utstyr_Beskrivelse, Kategori_ID) values('Personløfter', 'personløft som er en enkel og sikker utstyr som du kan bruke til å komme deg opp i høyden', '2');
INSERT INTO Utstyr (Utstyr_Navn, Utstyr_Beskrivelse, Kategori_ID) values('Gaffeltruck', 'en kraftig og rubust gaffeltruck med dieselmotor som kan brukes i alle lagermiljøer og står for kvalitet, effektivitet og pålitelighet', '2');
INSERT INTO Utstyr (Utstyr_Navn, Utstyr_Beskrivelse, Kategori_ID) values('Motorisert trillebår', 'motorisert trillebår som er ypperlig for transport av varer etc.', '1');
INSERT INTO Utstyr (Utstyr_Navn, Utstyr_Beskrivelse, Kategori_ID) values('Spikerpistol, stor' , 'dykkertlengde mellom 15-50 mm. utstyrt med ergonomisk håndtak for komfortabel arbeidsstilling', '1');


create table Lisensiertutstyr
 (
                        Lisens_ID integer UNIQUE auto_increment NOT NULL,
                        Utstyr_kommentar varchar (30) NOT NULL ,
                        Utstyr_ID integer NOT NULL ,
                        PRIMARY KEY (Lisens_ID),
                        FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID)

);

INSERT INTO Lisensiertutstyr(Utstyr_kommentar, Utstyr_ID) values ('trengs fullført og bestått kurs', '7');
INSERT INTO Lisensiertutstyr(Utstyr_kommentar, Utstyr_ID) values ('trengs truckførerbevis', '8');

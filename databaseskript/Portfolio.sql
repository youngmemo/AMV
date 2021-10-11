/*ansatt, utstyr og fprespørsel */
create database if not exists MytestDB;
use MytestDB;
create table if not EXISTS Ansatt
(
    Ansatt_ID               integer UNIQUE auto_increment,
    Mobilnummer             integer NOT NULL,
    Fornavn                 varchar(30) NOT NULL,
    Etternavn               varchar(30) NOT NULL,
    Epost                   varchar(255) NOT NULL,
    Adresse                 varchar(50) NOT NULL,
    Ansattnummer            integer NOT NULL,
    PRIMARY KEY (Ansatt_ID)
);

create table if not EXISTS Utstyr
(
    Utstyr_ID               integer UNIQUE auto_increment,
    Utstyr_Navn             varchar(50) NOT NULL,
    Utstyr_Beskrivelse      varchar(50) NOT NULL,
    Kategori_ID             integer NOT NULL,
    PRIMARY KEY (Utstyr_ID),
    FOREIGN KEY (Kategori_ID) REFERENCES Kategori(Kategori_ID)
);

create table if not EXISTS Foresporsel
(
    Foresporsel_ID          integer UNIQUE auto_increment,
    Ansatt_ID               integer NOT NULL,
    Utstyr_ID               integer NOT NULL,
    PRIMARY KEY (Foresporsel_ID),
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID),
    FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID)

)
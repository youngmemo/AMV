create database if not exists MytestDB;
use MytestDB;
create table if not EXISTS Kategori
(
    Kategori_ID       integer UNIQUE auto_increment NOT NULL,
    Kategori_Verktoy varchar (50) NOT NULL,
    Kategori_Kjoretoy varchar (50) NOT NULL,
    Utstyr_ID integer (50) NOT NULL,
    PRIMARY KEY (Kategori_ID) ,
    FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr (UtstyrID)


    );

insert into Kategori(Kategori_Verktoy,
                     Kategori_Kjoretoy,
                     Kategori_ID,
                     Utstyr_ID)
values (
           'trym',
           'gotunegrym',
           'grupsikis',
           '10');

create table if not EXISTS Betaling
(
    Ansatt_ID  integer UNIQUE auto_increment NOT NULL,
    Utstyr_ID integer (50) NOT NULL,
    Betalingsmetode_ID varchar (50) NOT NULL,
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID),
    FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID),
    FOREIGN KEY (Betalingsmetode_ID) REFERENCES Betalingsmetode(Betalingsmetode_ID)
    );

insert into Betaling(Ansatt_ID,
                     Utstyr_ID,
                     Betalingsmetode_ID)

values ('SerefBey',
        '10',
        'Kontant');

create database if not exists mytestdb;
use mytestdb;
CREATE OR REPLACE TABLE Ansatt
(
    Ansatt_ID               SMALLINT UNIQUE AUTO_INCREMENT,
    Fornavn                 VARCHAR(30)     NOT NULL,
    Etternavn               VARCHAR(30)     NOT NULL,
    Mobilnummer             INTEGER         NOT NULL,
    Epost                   VARCHAR(255)    NOT NULL,
    Ansattnummer            SMALLINT        NOT NULL,
    Passord                 VARCHAR(255)    NOT NULL,
    Adresse                 VARCHAR(50)     NOT NULL,
    Bynavn                  VARCHAR(50)     NOT NULL,
    Postnummer              SMALLINT        NOT NULL,
    PRIMARY KEY (Ansatt_ID)
);

CREATE OR REPLACE TABLE Kategori
(
    Kategori_ID             SMALLINT UNIQUE AUTO_INCREMENT,
    Kategori                VARCHAR(50)     NOT NULL,
    PRIMARY KEY (Kategori_ID)
);

CREATE OR REPLACE TABLE Betalingsmetode
(
    Betalingsmetode_ID      SMALLINT UNIQUE AUTO_INCREMENT,
    Metode                  VARCHAR(80)     NOT NULL,
    PRIMARY KEY (Betalingsmetode_ID)
);

CREATE OR REPLACE TABLE Utstyr
(
    Utstyr_ID               SMALLINT UNIQUE AUTO_INCREMENT,
    Utstyr_Navn             VARCHAR(50)     NOT NULL,
    Utstyr_Beskrivelse      VARCHAR(1000)   NOT NULL,
    Kategori_ID             SMALLINT,
    PRIMARY KEY (Utstyr_ID),
    FOREIGN KEY (Kategori_ID) REFERENCES Kategori(Kategori_ID) ON DELETE SET NULL
);

CREATE OR REPLACE TABLE Foresporsel
(
    Foresporsel_ID          SMALLINT UNIQUE AUTO_INCREMENT,
    Ansatt_ID               SMALLINT        NOT NULL,
    Utstyr_ID               SMALLINT        NOT NULL,
    Start_Dato              DATE            NOT NULL,
    Slutt_Dato              DATE            NOT NULL,
    PRIMARY KEY (Foresporsel_ID),
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID) ON DELETE CASCADE,
    FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE Status
(
    Status_ID               SMALLINT UNIQUE AUTO_INCREMENT,
    /* date skrives på format dd.mm.åååå */
    Foresporsel_ID          SMALLINT        NOT NULL,
    Levert                  SMALLINT,

    PRIMARY KEY (Status_ID),
    FOREIGN KEY (Foresporsel_ID) REFERENCES Foresporsel(Foresporsel_ID) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE LisensiertUtstyr
(
    Lisens_ID               SMALLINT UNIQUE AUTO_INCREMENT,
    Utstyr_Kommentar        VARCHAR (1000)  NOT NULL DEFAULT 'Ingen kommentar lagt til',
    Utstyr_ID               SMALLINT        NOT NULL,
    PRIMARY KEY (Lisens_ID),
    FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE Forslag
(
    Forslag_ID              SMALLINT UNIQUE AUTO_INCREMENT,
    Forslag_Utstyr          VARCHAR(150)    NOT NULL,
    Forslag_Kommentar       VARCHAR(1000)   NOT NULL,
    Ansatt_ID               SMALLINT,
    PRIMARY KEY (Forslag_ID),
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID) ON DELETE SET NULL
);

CREATE OR REPLACE TABLE Rapport
(
    Rapport_ID              SMALLINT UNIQUE AUTO_INCREMENT,
    Rapport_Tittel          VARCHAR(150)    NOT NULL,
    Rapport_Kommentar       VARCHAR(1000)   NOT NULL,
    Utstyr_ID               SMALLINT,
    Ansatt_ID               SMALLINT,
    PRIMARY KEY (Rapport_ID),
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID) ON DELETE SET NULL,
    FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID) ON DELETE SET NULL
);

CREATE OR REPLACE TABLE Betaling
(
    Ansatt_ID               SMALLINT        NOT NULL,
    Utstyr_ID               SMALLINT        NOT NULL,
    Betalingsmetode_ID      SMALLINT,
    FOREIGN KEY(Betalingsmetode_ID) REFERENCES Betalingsmetode(Betalingsmetode_ID) ON DELETE SET NULL
);

CREATE OR REPLACE TABLE Brukerrettigheter
(
    Ansatt_ID               SMALLINT        NOT NULL,
    Rettighet               VARCHAR(50)     NOT NULL,
    Kommentar               VARCHAR(1000),
    FOREIGN KEY(Ansatt_ID)  REFERENCES Ansatt(Ansatt_ID) ON DELETE CASCADE
);


CREATE OR REPLACE TABLE Files(
    Id                      SMALLINT UNIQUE auto_increment,
    Name                    VARCHAR(255)    NOT NULL,
    Content                 LONGBLOB        NOT NULL,
    ContentType             VARCHAR(255)    NOT NULL,
    PRIMARY KEY (Id)
);

INSERT INTO Kategori (Kategori)
VALUES ('Verktøy'),
       ('Kjøretøy');

INSERT INTO Ansatt (Ansattnummer, Fornavn, Etternavn, Epost, Passord, Mobilnummer, Adresse, Bynavn, Postnummer)
VALUES  (1, 'Mehmet', 'Eksi', 'mehmeteksi99@hotmail.com', 'meheks1', 46464646, 'Markens gate 19', 'Kristiansand', 4600),
        (2, 'Osamah', 'Almaliki', 'osamah2001@hotmail.com', 'osaalm1', 46276313, 'Holbergs gate 8', 'Kristiansand', 4601),
        (3, 'Tina', 'Ruud', 'tinamruud@gmail.com', 'tinruu1', 88745219, 'Henrik wergelandsgate 1', 'Kristiansand', 4602),
        (4, 'Abdul Rahman', 'Kasim', 'abdul-rahman-kasim@hotmail.com', 'abdrah1', 44444444, 'Vestre strandgate 42', 'Kristiansand', 4603),
        (5, 'Ømer', 'Fener', 'omer99fe@hotmail.com', 'omefen1', 56565656, 'Dronningens gate 31', 'Kristiansand', 4604),
        (6, 'Berat', 'Gunes', 'beratg12@hotmail.com', 'bergun1', 77777777, 'Kongens gate 1', 'Kristiansand', 4605),
        (7, 'Terje', 'Gjøsæter', 'terje.gjøsæter@uia.no', 'tegjo1', 12345678, 'Gyldenløvens gate 12', 'Kristiansand', 4606),
        (8, 'Geir', 'Hausvik', 'geir.i.hausvik@uia.no', 'geihau1', 89898989, 'Tollbodgaten 60', 'Kristiansand', 4607),
        (9, 'Espen', 'Limi', 'espen.limi@uia.no','esplim1', 87654321, 'Rådhusgaten 7', 'Kristiansand', 4608),
        (10, 'Janis', 'Gailis', 'janis.gailis@uia.no', 'jangai1', 38141562, 'Elvegaten 10', 'Kristiansand', 4609);

INSERT INTO Utstyr (Utstyr_Navn, Utstyr_Beskrivelse, Kategori_ID)
VALUES  ('Eksentersliper', 'Børsteløs motor som gjør den vedlikeholdsfri og gir den lengre levetid og battertid.', 1),
        ('Båndsliper', 'Denne effektive båndsliperen garanterer et jevnt og pent resultat. 650W med en bånddimensjon på 76x457 mm.', 1),
        ('Vinkelsliper', '150 mm batteridrevet vinkelsliper med turtallsvalg og økt brukerbeskyttelse', 1),
        ('Meislemaskin', 'Topp ytelse ved boring og meisling med 1500 wats motor og 12,5 joule enkeltslagenergi', 1),
        ('Slagdrill', 'Slagdrill med på 18 V med 13 mm hurtigchuch og batteriindikator', 1),
        ('Kantklipper', 'Elektrisk kantklipper som hjelper deg med de utfordrende kantene langs veier, trær,murer', 1),
        ('Personløfter', 'Personløft som er en enkel og sikker utstyr som du kan bruke til å komme deg opp i høyden', 2),
        ('Gaffeltruck', 'En kraftig og rubust gaffeltruck med dieselmotor som kan brukes i alle lagermiljøer og står for kvalitet, effektivitet og pålitelighet', 2),
        ('Motorisert trillebår', 'Motorisert trillebår som er ypperlig for transport av varer etc.', 1),
        ('Spikerpistol, stor' , 'Dykkertlengde mellom 15-50 mm. utstyrt med ergonomisk håndtak for komfortabel arbeidsstilling', 1);

INSERT INTO Rapport (Rapport_Tittel, Rapport_Kommentar, Utstyr_ID, Ansatt_ID)
VALUES  ('Vinkelsliper skadet', 'Bladet til vinkelsliperen knakk', 3,10),
        ('Gaffeltruck funker ikke', 'Den vil ikke løfte opp plankene mine', 8,5),
        ('Personløfter punktert', 'Dekket til personløfter ble punktert når jeg skulle løfte opp noe', 7,4),
        ('Motorisert trillebår ødelagt','Den ble ødelagt når prøvde å borre stein', 9,3),
        ('Slagdrill fungerer ikke','Det er noe problem med motoren i selve drillen', 5,1),
        ('Kantklipper lager problemer','Kantklipperen klipper skeivt og det er veldig irriterende for meg', 6,8),
        ('Spikerpistol er ødelagt','To spikere sitter fast så jeg får ikke ut den ene', 10,7),
        ('Meislemaskinen er skadet','Den falt på en stor stein så ble den skadet', 4,2),
        ('Båndsliper funker ikke','Bladet til båndsliperen gikk i stykker', 2,6),
        ('Eksentersliper vil ikke slipe','Den vil ikke slipe det jeg vil at den skal slipe', 1,9);

INSERT INTO Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
VALUES  ('MAG-sveisemaskin','Er ofte jeg trenger MAG-sveisemaskin',1),
        ('TIG-sveisemaskin','Er ofte jeg trenger TIG-sveisemaskin',2),
        ('Enkelstige 4100 mm','Vi har ingen stiger å låne akkurat nå, å ha noen hadde vært greit',3),
        ('Mørtelblander','Vi har alt annet utstyr til muring, men ikke en mørtelblander',4),
        ('Høytrykksvasker','Høytrykksvaskere er dyrt, hadde vært greit å fått lånt fra bedriften',5),
        ('Bosch Nibbler','Ønsker en Nibbler her på jobb, veldig god utstyr',6),
        ('Termisk kamera','Viktig å se hvor varmt ting er.',7),
        ('Støv/Våtsuger','Blir for mye vann i terrassen etter vask av tepper med madammen',8),
        ('Tørrsuger','Kan anta mange av oss maler hjemme, hadde vært greit å få tørket malinga fortere',9),
        ('Rotasjonslaser','Greit når man jobber hjemme.',10);

INSERT INTO Betalingsmetode(Metode)
VALUES  ('Kontant'),
        ('Faktura');

INSERT INTO LisensiertUtstyr(Utstyr_Kommentar, Utstyr_ID)
VALUES ('Trengs fullført og bestått kurs', 7),
       ('Trengs truckførerbevis', 8);

INSERT INTO Foresporsel(Ansatt_ID, Utstyr_ID, Start_Dato, Slutt_Dato)
VALUES  (1,1,'2021-10-08', '2021-10-12'),
        (2,1,'2021-10-02', '2022-10-16'),
        (3,3,'2021-10-08', '2020-10-12'),
        (1,4,'2021-10-08', '2020-10-16'),
        (5,1,'2021-10-03', '2020-10-12'),
        (1,1,'2021-10-10', '2021-10-14'),
        (7,7,'2021-10-08', '2020-10-18'),
        (1,8,'2021-10-08', '2022-10-19'),
        (9,9,'2021-10-08', '2021-10-11'),
        (10,10,'2021-10-08', '2021-10-13');

INSERT INTO Status (Foresporsel_ID, Levert)
VALUES  (1,1),
        (2,1),
        (3,1),
        (4,0),
        (5,0),
        (6,1),
        (7,0),
        (8,1),
        (9,1),
        (10,1);


INSERT INTO Rapport (Rapport_Tittel, Rapport_Kommentar, Utstyr_ID, Ansatt_ID)
VALUES  ('Vinkelsliper skadet', 'Bladet til vinkelsliperen knakk', 3,10),
        ('Gaffeltruck funker ikke', 'Den vil ikke løfte opp plankene mine', 8,5),
        ('Personløfter punktert', 'Dekket til personløfter ble punktert når jeg skulle løfte opp noe', 7,4),
        ('Motorisert trillebår ødelagt','Den ble ødelagt når prøvde å borre stein', 9,3),
        ('Slagdrill fungerer ikke','Det er noe problem med motoren i selve drillen', 5,1),
        ('Kantklipper lager problemer','Kantklipperen klipper skeivt og det er veldig irriterende for meg', 6,8),
        ('Spikerpistol er ødelagt','To spikere sitter fast så jeg får ikke ut den ene', 10,7),
        ('Meislemaskinen er skadet','Den falt på en stor så ble den skadet', 4,2),
        ('Båndsliper funker ikke','Bladet til båndsliperen gikk i stykker', 2,6),
        ('Eksentersliper vil ikke slipe','Den vil ikke slipe det jeg vil at den skal slipe', 1,9);

INSERT INTO Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
VALUES  ('MAG-sveisemaskin','Er ofte jeg trenger MAG-sveisemaskin',1),
        ('TIG-sveisemaskin','Er ofte jeg trenger TIG-sveisemaskin',2),
        ('Enkelstige 4100 mm','Vi har ingen stiger å låne akkurat nå, å ha noen hadde vært greit',3),
        ('Mørtelblander','Vi har alt annet utstyr til muring, men ikke en mørtelblander',4),
        ('Høytrykksvasker','Høytrykksvaskere er dyrt, hadde vært greit å fått lånt fra bedriften',5),
        ('Bosch Nibbler','Ønsker en Nibbler her på jobb, veldig god utstyr',6),
        ('Termisk kamera','Viktig å se hvor varmt ting er.',7),
        ('Støv/Våtsuger','Blir for mye vann i terrassen etter vask av tepper med madammen',8),
        ('Tørrsuger','Kan anta mange av oss maler hjemme, hadde vært greit å få tørket malinga fortere',9),
        ('Rotasjonslaser','Greit når man jobber hjemme.',10);

INSERT INTO Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode_ID)
VALUES  (3, 1, 1),
        (8, 2, 1),
        (5, 4, 2),
        (8, 9, 1),
        (8, 2, 2),
        (5, 6, 2),
        (8, 7, 1),
        (5, 5, 2),
        (3, 2, 2),
        (1, 2, 2);

INSERT INTO Brukerrettigheter (Ansatt_ID, Rettighet, Kommentar)
VALUES  (1, 'normal', ''),  (2, 'normal', ''),
        (3, 'normal', ''),  (4, 'normal', ''),
        (5, 'normal', ''),  (6, 'normal', ''),
        (7, 'normal', ''),  (8, 'normal', ''),
        (9, 'normal', ''),  (10, 'normal', ''),

        (4, 'lisens', 'Har lov til å kjøre personlift'),
        (5, 'lisens', 'Har tilgang til gaffeltruck, har tatt truckførerkurs og vist bevis'),
        (7, 'lisens', 'Administrator skal ha tilgang her'),
        (9, 'lisens', 'Administrator skal ha tilgang her'),
        (10, 'lisens', 'superbruker skal ha tilgang her'),

        (7, 'administrator', ''),
        (9, 'administrator', ''),
        (10, 'administrator', 'superbruker skal ha tilgang her'),

        (10, 'superbruker', 'Grunnleggeren av systemet');


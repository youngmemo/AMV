drop database if exists mytestdb;
create database if not exists mytestdb;
use mytestdb;

CREATE OR REPLACE TABLE Kategori
(
    Kategori_ID             SMALLINT UNIQUE AUTO_INCREMENT,
    Kategori                VARCHAR(50)     NOT NULL,
    CONSTRAINT PRIMARY KEY (Kategori_ID)
);

CREATE OR REPLACE TABLE Utstyr
(
    Utstyr_ID               SMALLINT UNIQUE AUTO_INCREMENT,
    Utstyr_Navn             VARCHAR(50)     NOT NULL,
    Utstyr_Beskrivelse      VARCHAR(1000),
    Kategori_ID             SMALLINT,
    CONSTRAINT PRIMARY KEY (Utstyr_ID),
    CONSTRAINT FOREIGN KEY (Kategori_ID) REFERENCES Kategori(Kategori_ID) ON DELETE SET NULL
);

CREATE OR REPLACE TABLE LisensiertUtstyr
(
    Lisens_ID               SMALLINT UNIQUE AUTO_INCREMENT,
    Utstyr_Kommentar        VARCHAR (1000)  NOT NULL DEFAULT 'Ingen kommentar lagt til',
    Utstyr_ID               SMALLINT        NOT NULL,
    CONSTRAINT PRIMARY KEY (Lisens_ID),
    CONSTRAINT FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE Ansatt
(
    Ansatt_ID               SMALLINT UNIQUE AUTO_INCREMENT,
    Fornavn                 VARCHAR(30)     NOT NULL,
    Etternavn               VARCHAR(30)     NOT NULL,
    Mobilnummer             INTEGER         NOT NULL,
    Epost                   VARCHAR(255)    NOT NULL,
    Passord                 VARCHAR(255)    NOT NULL,
    Adresse                 VARCHAR(50)     NOT NULL,
    Bynavn                  VARCHAR(50)     NOT NULL,
    Postnummer              SMALLINT        NOT NULL,
    CONSTRAINT PRIMARY KEY (Ansatt_ID)
);

CREATE OR REPLACE TABLE Betalingsmetode
(
    Betalingsmetode_ID      SMALLINT UNIQUE AUTO_INCREMENT,
    Metode                  VARCHAR(80)     NOT NULL,
    CONSTRAINT PRIMARY KEY (Betalingsmetode_ID)
);

CREATE OR REPLACE TABLE LisensiertAnsatt
(
    Ansatt_ID               SMALLINT        NOT NULL,
    Lisens_ID               SMALLINT        NOT NULL,
    CONSTRAINT FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID)           ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (Lisens_ID) REFERENCES LisensiertUtstyr(Lisens_ID) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE Foresporsel
(
    Foresporsel_ID          SMALLINT UNIQUE AUTO_INCREMENT,
    Ansatt_ID               SMALLINT        NOT NULL,
    Utstyr_ID               SMALLINT        NOT NULL,
    Start_Dato              DATE            NOT NULL,
    Slutt_Dato              DATE            NOT NULL,
    Akseptert               SMALLINT        DEFAULT 0,
    CONSTRAINT PRIMARY KEY (Foresporsel_ID),
    CONSTRAINT FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID) ON DELETE CASCADE,
    CONSTRAINT FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE Status
(
    Status_ID               SMALLINT UNIQUE AUTO_INCREMENT,
    /* date skrives på format dd.mm.åååå */
    Foresporsel_ID          SMALLINT        NOT NULL,
    Levert                  SMALLINT,
    CONSTRAINT PRIMARY KEY (Status_ID),
    CONSTRAINT FOREIGN KEY (Foresporsel_ID) REFERENCES Foresporsel(Foresporsel_ID) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE Forslag
(
    Forslag_ID              SMALLINT UNIQUE AUTO_INCREMENT,
    Forslag_Utstyr          VARCHAR(150)    NOT NULL,
    Forslag_Kommentar       VARCHAR(1000)   NOT NULL,
    Ansatt_ID               SMALLINT,
    CONSTRAINT PRIMARY KEY (Forslag_ID),
    CONSTRAINT FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID) ON DELETE SET NULL
);

CREATE OR REPLACE TABLE Rapport
(
    Rapport_ID              SMALLINT UNIQUE AUTO_INCREMENT,
    Rapport_Tittel          VARCHAR(150)    NOT NULL,
    Rapport_Kommentar       VARCHAR(1000)   NOT NULL,
    Utstyr_ID               SMALLINT,
    Ansatt_ID               SMALLINT,
    Lest_Rapport            SMALLINT        DEFAULT 0,
    CONSTRAINT PRIMARY KEY (Rapport_ID),
    CONSTRAINT FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID) ON DELETE SET NULL,
    CONSTRAINT FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID) ON DELETE SET NULL
);

CREATE OR REPLACE TABLE Brukerrettigheter
(
    Ansatt_ID               SMALLINT        NOT NULL,
    Rettighet               VARCHAR(50)     NOT NULL,
    Kommentar               VARCHAR(1000),
    CONSTRAINT FOREIGN KEY(Ansatt_ID)  REFERENCES Ansatt(Ansatt_ID) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE Betaling
(
    Ansatt_ID               SMALLINT        NOT NULL,
    Utstyr_ID               SMALLINT        NOT NULL,
    Betalingsmetode_ID      SMALLINT,
    Foresporsel_ID          SMALLINT        NOT NULL,
    CONSTRAINT FOREIGN KEY(Betalingsmetode_ID) REFERENCES Betalingsmetode(Betalingsmetode_ID) ON DELETE SET NULL,
    CONSTRAINT FOREIGN KEY(Foresporsel_ID) REFERENCES Foresporsel(Foresporsel_ID) ON DELETE CASCADE
);

INSERT INTO Kategori (Kategori)
VALUES ('Verktøy'),
       ('Kjøretøy');

INSERT INTO Ansatt (Fornavn, Etternavn, Epost, Passord, Mobilnummer, Adresse, Bynavn, Postnummer)
VALUES  ('Mehmet', 'Eksi', 'mehmeteksi99@hotmail.com', 'meheks1', 46464646, 'Markens gate 19', 'Kristiansand', 4600),
        ('Osamah', 'Almaliki', 'osamah2001@hotmail.com', 'osaalm1', 46276313, 'Holbergs gate 8', 'Kristiansand', 4601),
        ('Tina', 'Ruud', 'tinamruud@gmail.com', 'tinruu1', 88745219, 'Henrik wergelandsgate 1', 'Kristiansand', 4602),
        ('Abdul Rahman', 'Kasim', 'abdul-rahman-kasim@hotmail.com', 'abdrah1', 44444444, 'Vestre strandgate 42', 'Kristiansand', 4603),
        ('Ømer', 'Fener', 'omer99fe@hotmail.com', 'omefen1', 56565656, 'Dronningens gate 31', 'Kristiansand', 4604),
        ('Berat', 'Gunes', 'beratg12@hotmail.com', 'bergun1', 77777777, 'Kongens gate 1', 'Kristiansand', 4605),
        ('Terje', 'Gjøsæter', 'terje.gjøsæter@uia.no', 'tegjo1', 12345678, 'Gyldenløvens gate 12', 'Kristiansand', 4606),
        ('Geir', 'Hausvik', 'geir.i.hausvik@uia.no', 'geihau1', 89898989, 'Tollbodgaten 60', 'Kristiansand', 4607),
        ('Espen', 'Limi', 'espen.limi@uia.no','esplim1', 87654321, 'Rådhusgaten 7', 'Kristiansand', 4608),
        ('Janis', 'Gailis', 'janis.gailis@uia.no', 'jangai1', 38141562, 'Elvegaten 10', 'Kristiansand', 4609),
        ('Test', 'Bruker', 'testbruker@uia.no', 'test',  38141563, 'Test 10', 'Test', 0000);

INSERT INTO Utstyr (Utstyr_Navn, Utstyr_Beskrivelse, Kategori_ID)
VALUES  ('Eksentersliper 230 VAC', 'Børsteløs motor som gjør den vedlikeholdsfri og gir den lengre levetid og battertid.', 1),
        ('Båndsliper 230 VAC', 'Denne effektive båndsliperen garanterer et jevnt og pent resultat. 650W med en bånddimensjon på 76x457 mm.', 1),
        ('Vinkelsliper', '150 mm batteridrevet vinkelsliper med turtallsvalg og økt brukerbeskyttelse', 1),
        ('Meislemaskin', 'Topp ytelse ved boring og meisling med 1500 wats motor og 12,5 joule enkeltslagenergi', 1),
        ('Slagdrill Milwaukee', 'Slagdrill med på 18 V med 13 mm hurtigchuch og batteriindikator', 1),
        ('Kantklipper (bensin)', 'Elektrisk kantklipper som hjelper deg med de utfordrende kantene langs veier, trær,murer', 1),
        ('Personløfter 230 VAC', 'Personløft som er en enkel og sikker utstyr som du kan bruke til å komme deg opp i høyden', 2),
        ('Gaffeltruck', 'En kraftig og rubust gaffeltruck med dieselmotor som kan brukes i alle lagermiljøer og står for kvalitet, effektivitet og pålitelighet', 2),
        ('Motorisert trillebår', 'Motorisert trillebår som er ypperlig for transport av varer etc.', 1),
        ('Spikerpistol, Milwaukee stor' , 'Dykkertlengde mellom 15-50 mm. utstyrt med ergonomisk håndtak for komfortabel arbeidsstilling', 1),
        ('Spikerpistol, Milwaukee mellom', 'Beskrivelse', 1),
        ('Spikerpistol, Milwaukee liten', 'Beskrivelse', 1),
        ('Spikerpistol liten (luft)', 'Beskrivelse', 1),
        ('Skruautomat', 'Beskrivelse', 1),
        ('Fein Multimaskin', 'Beskrivelse', 1),
        ('Flisekutter, keramiske fliser', 'Beskrivelse', 1),
        ('Høvel 230 VAC', 'Beskrivelse', 1),
        ('Gjære-/kombisag 230 VAC', 'Beskrivelse', 1),
        ('Vedkløyver (bensin)', 'Beskrivelse', 1),
        ('Tilhenger boggi, RC987', 'Beskrivelse', 1),
        ('Tilhenger liten, RC8834', 'Beskrivelse', 1),
        ('Kompressor 230 VAC', 'Beskrivelse', 1),
        ('Hoppetusse" (bensin)', 'Beskrivelse', 1),
        ('Flisekutter for trevirke', 'Beskrivelse', 1),
        ('Strømaggregat 3,7 kW', 'Beskrivelse', 1),
        ('Dekkmaskin', 'Beskrivelse', 1),
        ('Bildiagnose', 'Beskrivelse', 1),
        ('Leirduekaster', 'Beskrivelse', 1),
        ('Leica snekker laser', 'Beskrivelse', 1),
        ('Skaphenger', 'Beskrivelse', 1),
        ('Bluetooth høyttaler SOUNDBOKS', 'Beskrivelse', 1);


INSERT INTO Rapport (Rapport_Tittel, Rapport_Kommentar, Utstyr_ID, Ansatt_ID, Lest_Rapport)
VALUES  ('Vinkelsliper skadet', 'Bladet til vinkelsliperen knakk', 3,10,0),
        ('Gaffeltruck funker ikke', 'Den vil ikke løfte opp plankene mine', 8,5,0),
        ('Personløfter punktert', 'Dekket til personløfter ble punktert når jeg skulle løfte opp noe', 7,4,0),
        ('Motorisert trillebår ødelagt','Den ble ødelagt når prøvde å borre stein', 9,3,0),
        ('Slagdrill fungerer ikke','Det er noe problem med motoren i selve drillen', 5,1,0),
        ('Kantklipper lager problemer','Kantklipperen klipper skeivt og det er veldig irriterende for meg', 6,8,0),
        ('Spikerpistol er ødelagt','To spikere sitter fast så jeg får ikke ut den ene', 10,7,0),
        ('Meislemaskinen er skadet','Den falt på en stor stein så ble den skadet', 4,2,0),
        ('Båndsliper funker ikke','Bladet til båndsliperen gikk i stykker', 2,6,0),
        ('Eksentersliper vil ikke slipe','Den vil ikke slipe det jeg vil at den skal slipe', 1,9,0);

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

INSERT INTO Foresporsel(Ansatt_ID, Utstyr_ID, Start_Dato, Slutt_Dato, Akseptert)
VALUES  (1,1, '2021-02-02', '2021-02-04', 0),  (1,2, '2021-02-02', '2021-02-04', 0),
        (2,3, '2021-02-02', '2021-02-04', 0),  (6,4, '2021-02-02', '2021-02-04', 0),
        (3,5, '2021-02-02', '2021-02-04', 0),  (9,6, '2021-02-02', '2021-02-04', 0),
        (4,7, '2021-02-02', '2021-02-04', 0),  (2,8, '2021-02-02', '2021-02-04', 0),
        (5,9, '2021-02-02', '2021-02-04', 1),  (5,10,'2021-02-02', '2021-02-04', 1),
        (6,11,'2021-02-02', '2021-02-04', 1),  (6,12,'2021-02-02', '2021-02-04', 1),
        (7,13,'2021-02-02', '2021-02-04', 1),  (1,14,'2021-02-02', '2021-02-04', 1),
        (8,15,'2021-02-02', '2021-02-04', 1),  (2,16,'2021-02-02', '2021-02-04', 1),
        (9,17,'2021-02-02', '2021-02-04', 1),  (9,18,'2021-02-02', '2021-02-04', 1),
        (9,19,'2021-02-02', '2021-02-04', 1),  (9,20,'2021-02-02', '2021-02-04', 1),
        (1,21,'2021-02-02', '2021-02-04', 1),  (6,22,'2021-02-02', '2021-02-04', 1),
        (2,23,'2021-02-02', '2021-02-04', 1),  (3,24,'2021-02-02', '2021-02-04', 1),
        (6,25,'2021-02-02', '2021-02-04', 1),  (6,26,'2021-02-02', '2021-02-04', 1),
        (2,27,'2021-02-02', '2021-02-04', 1),  (2,28,'2021-02-02', '2021-02-04', 1),
        (3,29,'2021-02-02', '2021-02-04', 1),  (4,30,'2021-02-02', '2021-02-04', 1),
        (1,31,'2021-02-02', '2021-02-04', 1),

        (1,1,  '2021-11-15', '2021-12-30', 1),
        (2,1,  '2021-11-15', '2021-11-19', 1),
        (3,3,  '2021-11-15', '2021-12-30', 1),
        (1,4,  '2021-11-15', '2021-11-19', 1),
        (5,1,  '2021-11-15', '2021-11-19', 1),
        (1,1,  '2021-10-10', '2021-10-14', 1),
        (7,7,  '2021-10-08', '2021-12-30', 1),
        (1,8,  '2021-10-08', '2021-12-30', 1),
        (9,9,  '2021-10-08', '2021-12-30', 1),
        (10,10,'2021-10-08', '2021-10-13', 1);

INSERT INTO Status (Foresporsel_ID, Levert)
VALUES  (1,1),   (2,0),   (3,0),   (4,1),
        (5,1),   (6,0),   (7,1),   (8,0),
        (9,1),   (10,1),  (11,0),  (12,1),
        (13,1),  (14,1),  (15,0),  (16,0),
        (17,1),  (18,1),  (19,1),  (20,1),
        (21,0),  (22,0),  (23,1),  (24,1),
        (25,1),  (26,1),  (27,1),  (28,0),
        (29,0),  (30,0),  (31,0),

        (32,0),  (33,1),  (34,0),  (35,1),
        (36,0),  (37,1),  (38,0),  (39,0),
        (40,0),  (41,1);

INSERT INTO Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode_ID, Foresporsel_ID)
VALUES  (3, 1, 1, 1),
        (8, 2, 1, 2),
        (5, 4, 2, 3),
        (8, 9, 1, 4),
        (8, 2, 2, 5),
        (5, 6, 2, 6),
        (8, 7, 1, 7),
        (5, 5, 2, 8),
        (3, 2, 2, 9),
        (1, 2, 2, 10);

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

INSERT INTO LisensiertAnsatt (Ansatt_ID, Lisens_ID)
VALUES (4, 1),
       (5, 2);
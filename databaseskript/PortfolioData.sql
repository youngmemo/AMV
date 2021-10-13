/* 1 - ansatt *
   2 - utstyr *
   3 - superbruker *
   4 - admin *
   5 - forslag *
   6 - betalingsmetode *
   7 - Kategori *
   8 - LisensiertUtstyr *
   9 - LisensiertAnsatt *
   10 - Status *
   11 - Rapport *
   12 - Forespørsel *
   13 - Betaling *
 */

USE AMV;

INSERT INTO Kategori (Kategori)
VALUES ('Verktøy'),
       ('Kjoretøy');

INSERT INTO Ansatt (Ansattnummer, Fornavn, Etternavn, Epost, Mobilnummer, Adresse)
VALUES  (1, 'Mehmet', 'Eksi', 'mehmeteksi99@hotmail.com', 46464646, 'Markens gate 19'),
        (2, 'Osamah', 'Almaliki', 'osamah2001@hotmail.com', 46276313, 'Holbergs gate 8'),
        (3, 'Tina', 'Ruud', 'tinamruud@gmail.com', 88745219, 'Henrik wergelandsgate 1'),
        (4, 'Abdul Rahman', 'Kasim', 'abdul-rahman-kasim@hotmail.com', 44444444, 'Vestre strandgate 42'),
        (5, 'Ømer', 'Fener', 'omer99fe@hotmail.com', 56565656, 'Dronningens gate 31'),
        (6, 'Berat', 'Gunes', 'beratg12@hotmail.com', 77777777, 'Kongens gate 1'),
        (7, 'Terje', 'Gjøsæter', 'terje.gjøsæter@uia.no', 12345678, 'Gyldenløvens gate 12'),
        (8, 'Geir', 'Hausvik', 'geir.i.hausvik@uia.no', 89898989, 'Tollbodgaten 60'),
        (9, 'Espen', 'Limi', 'espen.limi@uia.no', 87654321, 'Rådhusgaten 7'),
        (10, 'Janis', 'Gailis', 'janis.gailis@uia.no', 38141562, 'Elvegaten 10');

INSERT INTO Utstyr (Utstyr_Navn, Utstyr_Beskrivelse, Kategori_ID)
VALUES  ('Eksentersliper', 'børsteløs motor som gjør den vedlikeholdsfri og gir den lengre levetid og battertid.', 1),
        ('Båndsliper', 'denne effektive båndsliperen garanterer et jevnt og pent resultat. 650W med en bånddimensjon på 76x457 mm.', 1),
        ('vinkelsliper', '150 mm batteridrevet vinkelsliper med turtallsvalg og økt brukerbeskyttelse', 1),
        ('Meislemaskin', 'topp ytelse ved boring og meisling med 1500 wats motor og 12,5 joule enkeltslagenergi', 1),
        ('Slagdrill', 'Slagdrill med på 18 V med 13 mm hurtigchuch og batteriindikator', 1),
        ('Kantklipper', 'elektrisk kantklipper som hjelper deg med de utfordrende kantene langs veier, trær,murer', 1),
        ('Personløfter', 'personløft som er en enkel og sikker utstyr som du kan bruke til å komme deg opp i høyden', 2),
        ('Gaffeltruck', 'en kraftig og rubust gaffeltruck med dieselmotor som kan brukes i alle lagermiljøer og står for kvalitet, effektivitet og pålitelighet', 2),
        ('Motorisert trillebår', 'motorisert trillebår som er ypperlig for transport av varer etc.', 1),
        ('Spikerpistol, stor' , 'dykkertlengde mellom 15-50 mm. utstyrt med ergonomisk håndtak for komfortabel arbeidsstilling', 1);

INSERT INTO Superbruker (Ansatt_ID)
VALUES (10);


INSERT INTO Administrator (Ansatt_ID)
VALUES  (9),
        (7);

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

INSERT INTO LisensiertAnsatt (Lisens_ID, Ansatt_ID, Ansatt_Kommentar)
VALUES  (1, 4, 'Har lov til å kjøre personlift'),
        (2, 5, 'Har tilgang til gaffeltruck, har tatt truckførerkurs og vist bevis');


INSERT INTO Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode_ID)
VALUES  (1, 1, 1),
        (8, 2, 1),
        (5, 4, 2),
        (6, 9, 1),
        (9, 2, 2),
        (7, 6, 2),
        (8, 7, 1),
        (5, 5, 2),
        (3, 2, 2);
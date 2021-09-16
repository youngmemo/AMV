DROP DATABASE Webshop;

CREATE DATABASE Webshop;

use Webshop;

CREATE TABLE kunder (
                        KundeID integer PRIMARY KEY NOT NULL ,
                        Fornavn varchar (30) NOT NULL,
                        Etternavn varchar (30) NOT NULL ,
                        Passord varchar (60) NOT NULL ,
                        Epost varchar (40) NOT NULL ,
                        Telefonnummer integer (8) NOT NULL ,
                        Adresse varchar (40) NOT NULL ,
                        Postnummer integer (4) NOT NULL


);

INSERT INTO kunder (KundeID, Fornavn, Etternavn, Passord, Epost, Telefonnummer, Adresse, Postnummer)
values (1,'Kristian','Larsen','adnasdjasudu22','kristianlarsen@gmail.com',92830112,'Bramstadveien 28',4626);

INSERT INTO kunder (KundeID, Fornavn, Etternavn, Passord, Epost, Telefonnummer, Adresse, Postnummer)
values (2,'Adam','Sin','sadasidsaid21','adamsin@gmail.com',92822112,'Hallaveien 18',4626);

INSERT INTO kunder (KundeID, Fornavn, Etternavn, Passord, Epost, Telefonnummer, Adresse, Postnummer)
values (3,'Bjørn','Olsen','iajsdiasjd17','bjornolsen@gmail.com',2819228,'Furuveien 28',4626);

UPDATE kunder
SET adresse='Mehmetveien'
WHERE KundeID=1;

UPDATE kunder
SET adresse='Abdulveien'
WHERE KundeID=2;

UPDATE kunder
SET adresse='Omerveien'
WHERE KundeID=3;

DELETE FROM kunder
where Fornavn='Adam';

INSERT INTO kunder (KundeID, Fornavn, Etternavn, Passord, Epost, Telefonnummer, Adresse, Postnummer)
values (2, 'Mehmet', 'Eksi', 'babanisikem12', 'mehmeteksi@gmail.com', 48573991, 'Memo', 4626);

INSERT INTO kunder (KundeID, Fornavn, Etternavn, Passord, Epost, Telefonnummer, Adresse, Postnummer)
values (4, 'Abdul', 'Kasim', 'pakistani211', 'abdulkasim@gmail.com', 2847719, 'pakistaniwarrior', 4626);

SELECT KundeID, Fornavn, Epost FROM kunder;

CREATE TABLE ordre (
                       OrderID integer PRIMARY KEY NOT NULL,
                       KundeID integer NOT NULL ,
                       OrdreDato date NOT NULL ,
                       Produkter varchar (50) ,
                       FOREIGN KEY (KundeID) REFERENCES kunder(KundeID)
);

INSERT INTO ordre (OrderID, KundeID, OrdreDato, Produkter)
values (1, 2, '2011-11-11', 'Fatima sin nesehårsfjerner');
DROP DATABASE Webshop;

CREATE DATABASE WEBSHOP;
use WEBSHOP;

CREATE TABLE Kunder (
                        kundeID integer PRIMARY KEY NOT NULL,
                        Fornavn varchar (30) NOT NULL,
                        Etternavn varchar (30) NOT NULL,
                        Epost varchar (40) NOT NULL,
                        Telefonnummer integer (8) NOT NULL,
                        Passord varchar (20) NOT NULL,
                        Adresse varchar (20) NOT NULL,
                        Postnummer int (4) NOT NULL

);
INSERT INTO Kunder (kundeID, Fornavn, Etternavn, Epost, Telefonnummer, Passord, Adresse, Postnummer)
values(1, 'Tom', 'Larsen', 'tomlarsen@hotmail.com', 41384997, 'tomlarsen123', 'solbakken 7', 4626);
INSERT INTO Kunder (kundeID, Fornavn, Etternavn, Epost, Telefonnummer, Passord, Adresse, Postnummer)
values(2,'Petter', 'Jakobsen', 'petterjakobsen@hotmail.com', 98643913, 'petter123', 'åsane 12', 4020 );
INSERT INTO Kunder (kundeID, Fornavn, Etternavn, Epost, Telefonnummer, Passord, Adresse, Postnummer)
values(3,'Jakob', 'Sørensen', 'jakobsørensen@hotmail.com', 48089121, 'jakob123', 'møvig 15', 4515 );

update Kunder
SET Adresse='Tranestien 9'
WHERE kundeID=3;

DELETE FROM Kunder
WHERE Fornavn='Tom';

INSERT INTO Kunder (kundeID, Fornavn, Etternavn, Epost, Telefonnummer, Passord, Adresse, Postnummer)
values(4,'Mehmet', 'Eksi', 'MehmetEksi@hotmail,com', 41624571, 'memo123', 'Slettheiveien 66', 4626 );

INSERT INTO Kunder (kundeID, Fornavn, Etternavn, Epost, Telefonnummer, Passord, Adresse, Postnummer)
values(5,'Berat', 'Gunes', 'BeratGunes@hotmail.com', 41654751, 'berat', 'Tinnheiveien 15', 4208 );



SELECT kundeID, Fornavn, Epost FROM Kunder;

CREATE TABLE ordre (
                       OrderID integer PRIMARY KEY NOT NULL,
                       kundeID integer NOT NULL ,
                       OrdreDato date NOT NULL,
                       Produkter varchar (50),
                       Status varchar (20),
                       FOREIGN KEY (kundeID) REFERENCES Kunder(kundeID)


);

INSERT INTO ordre (OrderID, kundeID, OrdreDato, Produkter, Status)
values(1, 2, '2011-11-11', 'vaskemaskin', 'tilgjengelig');
INSERT INTO Ordre (OrderID, kundeID, OrdreDato, Produkter, Status)
values(3, 4,'2012-12-12', 'ørepropper', 'utsolgt');

/* Denne koden kommer ikke til å fungere
   fordi den prøver å slette en kunde som er bundet til en annen klasse med foreign key.
    DELETE FROM Customers
WHERE fornavn= 'mehmet';
 */

/* Vis fornavn, produkter, status fra Ordre.
  Ordre joiner Kunder og gir forkortelse K for Kunder
  On = Join on
  Ordre.KundeID blir slått sammen med K.KundeID*. Nå har du 1 tabell selvom informasjon kommer fra 2 tabeller */
SELECT Fornavn, Produkter, Status FROM Ordre INNER JOIN Kunder K on Ordre.KundeID = K.KundeID;


/*List all equipment in the system with their type*/
SELECT U.Utstyr_Navn AS Utstyrnavn, K.Kategori
FROM Utstyr U
    INNER JOIN Kategori K ON U.Kategori_ID = K.Kategori_ID;

/* List all the available (at the moment – not already borrowed) equipment */
SELECT DISTINCT U.Utstyr_ID, U.Utstyr_Navn AS Utstyrnavn
FROM Utstyr U
    INNER JOIN Foresporsel F ON U.Utstyr_ID = F.Utstyr_ID
    INNER JOIN Status S ON F.Foresporsel_ID = S.Foresporsel_ID
WHERE S.Levert = TRUE AND F.Akseptert = FALSE;

/*Listing the 5 first rows of the 5 most important tables (your judgement), sorted.*/
SELECT * FROM Ansatt
ORDER BY Ansatt_ID
LIMIT 5;

SELECT * FROM Utstyr
ORDER BY Utstyr_ID
LIMIT 5;

SELECT * FROM Forslag
ORDER BY Forslag_ID
LIMIT 5;

SELECT * FROM Foresporsel
ORDER BY Foresporsel_ID
LIMIT 5;

SELECT * FROM Rapport
ORDER BY Rapport_ID
LIMIT 5;

/*List the names and number of borrows of the three users with most equipment borrowed, sorted by number of borrows */
SELECT A.Fornavn, A.Etternavn, COUNT(A.Ansatt_ID) AS AntallUtstyr
FROM Foresporsel F
    INNER JOIN Ansatt A ON F.Ansatt_ID = A.Ansatt_ID
GROUP BY F.Ansatt_ID
ORDER BY AntallUtstyr DESC
LIMIT 3;

/*List all the equipment borrowed by the user with the highest number of equipment borrowed, sorted by date/time*/
SELECT F.Foresporsel_ID, A.Fornavn, U.Utstyr_Navn, F.Start_Dato, F.Slutt_Dato
FROM Foresporsel F
    INNER JOIN Utstyr U ON F.Utstyr_ID = U.Utstyr_ID
    INNER JOIN Ansatt A ON F.Ansatt_ID = A.Ansatt_ID
WHERE F.Ansatt_ID =
      (
          SELECT F.Ansatt_ID
          FROM Foresporsel F
          WHERE F.Akseptert = TRUE
          GROUP BY F.Ansatt_ID
          ORDER BY COUNT(F.Ansatt_ID) DESC
          LIMIT 1

      )
ORDER BY F.Start_Dato;

/*List all overdue equipment with their borrowers */
SELECT A.Ansatt_ID, A.Fornavn, A.Etternavn, U.Utstyr_Navn
FROM Foresporsel F
    INNER JOIN Ansatt A ON F.Ansatt_ID = A.Ansatt_ID
    INNER JOIN Utstyr U ON F.Utstyr_ID = U.Utstyr_ID
    INNER JOIN Status S ON F.Foresporsel_ID = S.Foresporsel_ID
WHERE S.Levert = FALSE AND F.Akseptert = TRUE AND F.Slutt_Dato < CAST(CURRENT_DATE AS DATE)
ORDER BY A.Fornavn;

/*Lister opp alle lisensierte utstyr Ansatt "?" har tilgang til*/
SELECT U.Utstyr_ID, U.Utstyr_Navn, LU.Utstyr_Kommentar, K.Kategori
FROM Utstyr U
    INNER JOIN LisensiertUtstyr LU ON U.Utstyr_ID = LU.Utstyr_ID
    INNER JOIN Kategori K ON U.Kategori_ID = K.Kategori_ID
    INNER JOIN LisensiertAnsatt LA ON LU.Lisens_ID = LA.Lisens_ID
WHERE LA.Ansatt_ID = ?;

/*Spørringer vi har på servletsene*/
/*AkseptereForesporselServlet*/
INSERT INTO Status (Foresporsel_ID, Levert)
VALUES(?, 0);

UPDATE Foresporsel
    SET Akseptert = TRUE
WHERE Foresporsel_ID = ?;

SELECT F.Foresporsel_ID, U.Utstyr_Navn, F.Start_Dato, F.Slutt_Dato FROM Foresporsel F
    INNER JOIN Utstyr U ON F.Utstyr_ID = U.Utstyr_ID
WHERE Akseptert = FALSE
ORDER BY Foresporsel_ID ASC;

/*AvslaForesporselServlet*/
DELETE FROM Foresporsel WHERE Foresporsel_ID = ?;

SELECT F.Foresporsel_ID, U.Utstyr_Navn, F.Start_Dato, F.Slutt_Dato
FROM Foresporsel F
    INNER JOIN Utstyr U ON F.Utstyr_ID = U.Utstyr_ID
WHERE Akseptert = FALSE
ORDER BY Foresporsel_ID ASC;

/*BekreftTilbakeleveringServlet*/
SELECT S.Status_ID, U.Utstyr_Navn, F.Start_Dato, F.Slutt_Dato
FROM Foresporsel F
    INNER JOIN Utstyr U ON F.Utstyr_ID = U.Utstyr_ID
    INNER JOIN Status S ON F.Foresporsel_ID = S.Foresporsel_ID
WHERE S.Levert = 0 AND F.Ansatt_ID = ?
ORDER BY F.Foresporsel_ID ASC;

/*BookeLisensiertUtstyrServlet*/
INSERT INTO Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode_ID)
VALUES(?,?,?);

INSERT INTO Foresporsel (Ansatt_ID, Utstyr_ID, Start_Dato, Slutt_Dato)
VALUES(?,?,?,?);

/*BookeUtstyrServlet*/
INSERT INTO Foresporsel (Ansatt_ID, Utstyr_ID, Start_Dato, Slutt_Dato)
VALUES(?,?,?,?);

INSERT INTO Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode_ID, Foresporsel_ID)
VALUES(?,?,?,(SELECT MAX(Foresporsel_ID) FROM Foresporsel));

/*EndreDataServlet*/
UPDATE Ansatt
SET Epost = ?, Adresse = ?, Postnummer = ?, Passord = ?
WHERE Ansatt_ID = ?;

SELECT A.Ansatt_ID, A.Fornavn, A.Etternavn, A.Epost, A.Adresse, A.Postnummer, A.Passord
FROM Ansatt A;

/*FjernAdminServlet*/
DELETE FROM Brukerrettigheter
WHERE Rettighet = 'administrator' AND Ansatt_ID = ?;

/*FjernAnsattServlet*/
DELETE FROM Ansatt
WHERE Ansatt_ID = ?;

SELECT DISTINCT Ansatt_ID, Fornavn, Etternavn, Mobilnummer, Epost, Adresse, Bynavn, Postnummer
FROM Ansatt;

/*FjerneUtstyrServlet*/
DELETE FROM Utstyr
WHERE Utstyr_Navn = ?;

SELECT *
FROM Utstyr;

/*ForslagsBoksServlet*/
INSERT INTO Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
VALUES(?,?,?);

/*GiAdminTilAnsattServlet*/
INSERT INTO Brukerrettigheter (Ansatt_ID, Rettighet, Kommentar)
VALUES(?,'administrator',?);

SELECT A.Ansatt_ID, A.Fornavn, A.Etternavn
FROM Ansatt A;

/*GiLisensRettighetServlet*/
INSERT INTO Brukerrettigheter (Ansatt_ID, Rettighet, Kommentar)
VALUES(?,'lisens',?);

/*IkkeLanteUtstyrServlet*/
SELECT DISTINCT U.Utstyr_ID, U.Utstyr_Navn FROM Utstyr U
    INNER JOIN Foresporsel F ON U.Utstyr_ID = F.Utstyr_ID
    INNER JOIN Status S ON F.Foresporsel_ID = S.Foresporsel_ID
WHERE S.Levert = TRUE
ORDER BY Utstyr_ID;

/*KansellereUtstyrServlet*/
DELETE FROM Foresporsel
WHERE Foresporsel_ID = ?;

SELECT F.Foresporsel_ID, U.Utstyr_Navn, F.Start_Dato, F.Slutt_Dato
FROM Foresporsel F
    INNER JOIN Utstyr U ON F.Utstyr_ID = U.Utstyr_ID
WHERE F.Ansatt_ID = ?;

/*LeggeTilUtstyrServlet*/
INSERT INTO Utstyr (Utstyr_Navn,Utstyr_Beskrivelse,Kategori_ID)
VALUES(?,?,?);

/*OpprettAnsattServlet*/
INSERT INTO Brukerrettigheter (Ansatt_ID, Rettighet, Kommentar)
VALUES((SELECT MAX(Ansatt_ID) FROM Ansatt), 'normal', 'Førstegangsregistrering');

INSERT INTO Brukerrettigheter (Ansatt_ID, Rettighet, Kommentar)
VALUES(?, 'normal', 'Førstegangsregistrering');

/*RapporterUtstyretServlet*/
INSERT INTO Rapport(Rapport_Tittel, Rapport_Kommentar, Utstyr_ID, Ansatt_ID)
VALUES(?,?,?,?);

SELECT U.Utstyr_ID, U.Utstyr_Navn
FROM Utstyr U;

/*SeForslagServlet*/
SELECT Forslag_Utstyr, Forslag_Kommentar
FROM Forslag;

/*SeRapporteneServlet*/
SELECT Rapport_ID, Rapport_Tittel, Rapport_Kommentar, Utstyr_ID, Ansatt_ID
FROM Rapport
WHERE Lest_Rapport = FALSE
ORDER BY Rapport_ID ASC;

UPDATE Rapport
SET Lest_Rapport = TRUE
WHERE Rapport_ID = ?;

/*UtlantUtstyrServlet*/
SELECT A.Fornavn, A.Etternavn, U.Utstyr_Navn, F.Start_Dato, F.Slutt_Dato
FROM Foresporsel F
    INNER JOIN Utstyr U on F.Utstyr_ID = U.Utstyr_ID
    INNER JOIN Status S on F.Foresporsel_ID = S.Foresporsel_ID
    INNER JOIN Ansatt A on F.Ansatt_ID = A.Ansatt_ID
WHERE F.Akseptert = 1 AND S.Levert = 0 AND F.Slutt_Dato > CAST(CURRENT_DATE AS DATE) AND F.Start_Dato < CAST(CURRENT_DATE AS DATE)
ORDER BY Foresporsel_ID ASC;

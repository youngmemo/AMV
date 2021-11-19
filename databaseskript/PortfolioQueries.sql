/*List all equipment in the system with their type*/
SELECT U.Utstyr_Navn AS Utstyrnavn, K.Kategori
FROM Utstyr U
    JOIN Kategori K ON U.Kategori_ID = K.Kategori_ID;

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
    JOIN Utstyr U ON F.Utstyr_ID = U.Utstyr_ID
    JOIN Ansatt A ON F.Ansatt_ID = A.Ansatt_ID
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
    JOIN Ansatt A ON F.Ansatt_ID = A.Ansatt_ID
    JOIN Utstyr U ON F.Utstyr_ID = U.Utstyr_ID
    JOIN Status S ON F.Foresporsel_ID = S.Foresporsel_ID
WHERE S.Levert = FALSE AND F.Akseptert = TRUE AND F.Slutt_Dato < CAST(CURRENT_DATE AS DATE)
ORDER BY A.Fornavn;

/*Lister opp alle lisensierte utstyr Ansatt "?" har tilgang til*/
SELECT U.Utstyr_ID, U.Utstyr_Navn, LU.Utstyr_Kommentar, K.Kategori
FROM Utstyr U
    INNER JOIN LisensiertUtstyr LU ON U.Utstyr_ID = LU.Utstyr_ID
    INNER JOIN Kategori K ON U.Kategori_ID = K.Kategori_ID
    INNER JOIN LisensiertAnsatt LA ON LU.Lisens_ID = LA.Lisens_ID
WHERE LA.Ansatt_ID = ?;


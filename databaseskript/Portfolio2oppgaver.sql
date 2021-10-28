/*List all equipment in the system with their type*/
SELECT Utstyr_Navn, Kategori_ID from Utstyr;

/* List all the available (at the moment – not already borrowed) equipment */
SELECT distinct Utstyr.Utstyr_Navn
FROM Foresporsel
         JOIN Utstyr on Foresporsel.Utstyr_ID = Utstyr.Utstyr_ID
WHERE Slutt_Dato < CAST(current_date AS DATE) or Start_Dato > CAST(current_date AS DATE);

/* List all equipment that is borrowed at the moment */
SELECT distinct Utstyr.Utstyr_Navn
FROM Foresporsel
         JOIN Utstyr on Foresporsel.Utstyr_ID = Utstyr.Utstyr_ID
WHERE Slutt_Dato > CAST(current_date AS DATE) or Start_Dato > CAST(current_date AS DATE);

/*Listing the 5 first rows of the 5 most important tables (your judgement), sorted.*/
SELECT * FROM Ansatt
LIMIT 5;

SELECT * FROM Utstyr
LIMIT 5;

SELECT * FROM Forslag
LIMIT 5;

SELECT * FROM Foresporsel
LIMIT 5;

SELECT * FROM Rapport
LIMIT 5;

/*List the names and number of borrows of the three users with most equipment borrowed, sorted by number of borrows */
SELECT Ansatt.Fornavn, COUNT(Betaling.Ansatt_ID) AS UTSTYRLANT
FROM Betaling
         INNER JOIN Ansatt ON Betaling.Ansatt_ID = Ansatt.Ansatt_ID
GROUP BY Betaling.Ansatt_ID
ORDER BY UTSTYRLANT DESC
LIMIT 3;


/*List all the equipment borrowed by the user with the highest number of equipment borrowed, sorted by date/time*/
SELECT Foresporsel_ID, Ansatt.Fornavn, Utstyr.Utstyr_Navn, Start_Dato, Slutt_Dato
FROM Foresporsel
         JOIN Utstyr on Foresporsel.Utstyr_ID = Utstyr.Utstyr_ID
         JOIN Ansatt on Foresporsel.Ansatt_ID = Ansatt.Ansatt_ID
WHERE Foresporsel.Ansatt_ID =
      (
          SELECT Ansatt_ID
          FROM Foresporsel
          GROUP BY Ansatt_ID
          ORDER BY count(Ansatt_ID) DESC
          LIMIT 1
      )
ORDER BY Start_Dato;

/*List all overdue equipment with their borrowers */
SELECT Ansatt.Ansatt_ID, Ansatt.Fornavn, Utstyr.Utstyr_Navn
FROM Foresporsel
         JOIN Ansatt on Foresporsel.Ansatt_ID = Ansatt.Ansatt_ID
         JOIN Utstyr on Foresporsel.Utstyr_ID = Utstyr.Utstyr_ID
         JOIN Status on Foresporsel.Foresporsel_ID = Status.Foresporsel_ID
WHERE Status.Levert = 0 AND Slutt_Dato < CAST(current_date AS DATE);


# Hvordan du skal hente data fra ansatte som har de følgende adresset
SELECT * FROM  Ansatt
where Adresse = 'Kongens gate 1' OR Adresse = 'Holbergs gate 8' OR Adresse = 'Vestre strandgate 42';

# En kjappere løsning til samme kode
Select * From Ansatt
WHERE Adresse IN ('Kongens gate 1', 'Holbergs gate 8', 'Vestre strandgate 42')





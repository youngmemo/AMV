/*List all equipment in the system with their type*/
SELECT Utstyr_Navn, Kategori_ID from Utstyr;

/* List all the available (at the moment – not already borrowed) equipment */
SELECT distinct Utstyr_ID
from Status
where Slutt_Dato < CAST(current_date AS DATE) or Start_Dato > CAST(current_date AS DATE);

/* List all equipment that is borrowed at the moment */
SELECT distinct Utstyr_ID
from Status
where Slutt_Dato > CAST(current_date AS DATE) or Start_Dato > CAST(current_date AS DATE);

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
SELECT Ansatt.Fornavn, COUNT(Betaling.Ansatt_ID)
FROM Betaling
INNER JOIN Ansatt ON Betaling.Ansatt_ID = Ansatt.Ansatt_ID
GROUP BY Betaling.Ansatt_ID
ORDER BY Betaling.Ansatt_ID DESC
LIMIT 1;


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

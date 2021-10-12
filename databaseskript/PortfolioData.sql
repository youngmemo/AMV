insert into Administrator (Ansatt_ID)
values  (9),
        (7);


insert into Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
values  ('MAG-sveisemaskin','Er ofte jeg trenger MAG-sveisemaskin',1),
        ('TIG-sveisemaskin','Er ofte jeg trenger TIG-sveisemaskin',2),
        ('Enkelstige 4100 mm','Vi har ingen stiger å låne akkurat nå, å ha noen hadde vært greit',3),
        ('Mørtelblander','Vi har alt annet utstyr til muring, men ikke en mørtelblander',4),
        ('Høytrykksvasker','Høytrykksvaskere er dyrt, hadde vært greit å fått lånt fra bedriften',5),
        ('Bosch Nibbler','Ønsker en Nibbler her på jobb, veldig god utstyr',6),
        ('Termisk kamera','Viktig å se hvor varmt ting er.',7),
        ('Støv/Våtsuger','Blir for mye vann i terrassen etter vask av tepper med madammen',8),
        ('Tørrsuger','Kan anta mange av oss maler hjemme, hadde vært greit å få tørket malinga fortere',9),
        ('Rotasjonslaser','Greit når man jobber hjemme.',10);


insert into LisensiertAnsatt (Lisens_ID, Ansatt_ID, Ansatt_Kommentar)
values  (1, 4, 'Har lov til å kjøre personlift'),
        (2, 5, 'Har tilgang til gaffeltruck, har tatt truckførerkurs og vist bevis');


insert into Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode_ID)
values  (1, 1, 1),
        (8, 2, 1),
        (5, 4, 2),
        (6, 9, 1),
        (9, 2, 2),
        (7, 6, 2),
        (8, 7, 1),
        (5, 5, 2),
        (3, 2, 2);
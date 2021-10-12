insert into Administrator (Ansatt_ID)
values (9);

insert into Administrator (Ansatt_ID)
values (7);


insert into Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
values ('MAG-sveisemaskin','Er ofte jeg trenger MAG-sveisemaskin',1);

insert into Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
values ('TIG-sveisemaskin','Er ofte jeg trenger TIG-sveisemaskin',2);

insert into Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
values ('Enkelstige 4100 mm','Vi har ingen stiger å låne akkurat nå, å ha noen hadde vært greit',3);

insert into Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
values ('Mørtelblander','Vi har alt annet utstyr til muring, men ikke en mørtelblander',4);

insert into Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
values ('Høytrykksvasker','Høytrykksvaskere er dyrt, hadde vært greit å fått lånt fra bedriften',5);

insert into Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
values ('Bosch Nibbler','Ønsker en Nibbler her på jobb, veldig god utstyr',6);

insert into Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
values ('Termisk kamera','Viktig å se hvor varmt ting er.',7);

insert into Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
values ('Støv/Våtsuger','Blir for mye vann i terrassen etter vask av tepper med madammen',8);

insert into Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
values ('Tørrsuger','Kan anta mange av oss maler hjemme, hadde vært greit å få tørket malinga fortere',9);

insert into Forslag (Forslag_Utstyr, Forslag_Kommentar, Ansatt_ID)
values ('Rotasjonslaser','Greit når man jobber hjemme.',10);


insert into LisensiertAnsatt (Ansatt_ID, Ansatt_Kommentar)
values (4, 'Har lov til å kjøre personlift');

insert into LisensiertAnsatt (Ansatt_ID, Ansatt_Kommentar)
values (5, 'Har tilgang til gaffeltruck, har tatt truckførerkurs og vist bevis');


insert into Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode)
values (1, 1, 1);

insert into Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode)
values (8, 2, 1);

insert into Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode)
values (5, 3, 2);

insert into Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode)
values (5, 4, 2);

insert into Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode)
values (6, 9, 1);

insert into Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode)
values (9, 2, 2);

insert into Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode)
values (7, 6, 2);

insert into Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode)
values (8, 7, 1);

insert into Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode)
values (5, 5, 2);

insert into Betaling (Ansatt_ID, Utstyr_ID, Betalingsmetode)
values (3, 2, 2);
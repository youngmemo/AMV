create database if not exists AMV;
use AMV;
CREATE OR REPLACE TABLE Ansatt
(
    Ansatt_ID               SMALLINT UNIQUE AUTO_INCREMENT,
    Mobilnummer             INTEGER NOT NULL,
    Fornavn                 VARCHAR(30) NOT NULL,
    Etternavn               VARCHAR(30) NOT NULL,
    Epost                   VARCHAR(255) NOT NULL,
    Adresse                 VARCHAR(50) NOT NULL,
    Ansattnummer            SMALLINT    NOT NULL,
    PRIMARY KEY (Ansatt_ID)
);

CREATE OR REPLACE TABLE Kategori
(
    Kategori_ID             SMALLINT UNIQUE AUTO_INCREMENT,
    Kategori                VARCHAR(50) NOT NULL,
    PRIMARY KEY (Kategori_ID)
);

CREATE OR REPLACE TABLE Betalingsmetode
(
    Betalingsmetode_ID      SMALLINT UNIQUE AUTO_INCREMENT,
    Metode                  VARCHAR(80) NOT NULL,
    PRIMARY KEY (Betalingsmetode_ID)
);

CREATE OR REPLACE TABLE Utstyr
(
    Utstyr_ID               SMALLINT UNIQUE AUTO_INCREMENT,
    Utstyr_Navn             VARCHAR(50) NOT NULL,
    Utstyr_Beskrivelse      VARCHAR(1000) NOT NULL,
    Kategori_ID             SMALLINT NOT NULL,
    PRIMARY KEY (Utstyr_ID),
    FOREIGN KEY (Kategori_ID) REFERENCES Kategori(Kategori_ID)
);

CREATE OR REPLACE TABLE Foresporsel
(
    Foresporsel_ID          SMALLINT UNIQUE AUTO_INCREMENT,
    Ansatt_ID               SMALLINT NOT NULL,
    Utstyr_ID               SMALLINT NOT NULL,
    PRIMARY KEY (Foresporsel_ID),
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID) ON DELETE CASCADE,
    FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE Status
(
    Status_ID               SMALLINT UNIQUE AUTO_INCREMENT,
    /* date skrives på format dd.mm.åååå */
    Start_Dato              DATE,
    Slutt_Dato              DATE,
    Tilgjengelig            VARCHAR(30) NOT NULL DEFAULT 'Tilgjengelig',
    Utstyr_ID               SMALLINT NOT NULL,
    PRIMARY KEY (Status_ID),
    FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE LisensiertUtstyr
(
    Lisens_ID               SMALLINT UNIQUE AUTO_INCREMENT,
    Utstyr_Kommentar        VARCHAR (1000) NOT NULL DEFAULT 'Ingen kommentar lagt til',
    Utstyr_ID               SMALLINT     NOT NULL,
    PRIMARY KEY (Lisens_ID),
    FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID) ON DELETE CASCADE
);


CREATE OR REPLACE TABLE Forslag
(
    Forslag_ID              SMALLINT UNIQUE AUTO_INCREMENT,
    Forslag_Utstyr          VARCHAR(150) NOT NULL,
    Forslag_Kommentar       VARCHAR(1000) NOT NULL,
    Ansatt_ID               SMALLINT    NOT NULL,
    PRIMARY KEY (Forslag_ID),
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID)
);

CREATE OR REPLACE TABLE Rapport
(
    Rapport_ID              SMALLINT UNIQUE AUTO_INCREMENT,
    Rapport_Tittel          VARCHAR(150) NOT NULL,
    Rapport_Kommentar       VARCHAR(100) NOT NULL,
    Utstyr_ID               SMALLINT    NOT NULL,
    Ansatt_ID               SMALLINT    NOT NULL,
    PRIMARY KEY (Rapport_ID),
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID),
    FOREIGN KEY (Utstyr_ID) REFERENCES Utstyr(Utstyr_ID)
);

CREATE OR REPLACE TABLE LisensiertAnsatt
(
    Lisens_ID               SMALLINT NOT NULL,
    Ansatt_ID               SMALLINT NOT NULL,
    Ansatt_Kommentar        VARCHAR(1000),
    FOREIGN KEY (Lisens_ID) REFERENCES LisensiertUtstyr(Lisens_ID),
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE Administrator
(
    Ansatt_ID               SMALLINT NOT NULL,
    FOREIGN KEY (Ansatt_ID) REFERENCES Ansatt(Ansatt_ID) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE Superbruker
(
    Ansatt_ID               SMALLINT NOT NULL,
    FOREIGN KEY(Ansatt_ID)  REFERENCES Ansatt(Ansatt_ID) ON DELETE CASCADE
);

CREATE OR REPLACE TABLE Betaling
(
    Ansatt_ID               SMALLINT NOT NULL,
    Utstyr_ID               SMALLINT NOT NULL,
    Betalingsmetode_ID      SMALLINT NOT NULL,
    FOREIGN KEY(Betalingsmetode_ID) REFERENCES Betalingsmetode(Betalingsmetode_ID)
);
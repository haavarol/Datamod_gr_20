CREATE DATABASE project;
USE project;

CREATE TABLE treningsøkt(ID INT NOT NULL AUTO_INCREMENT,
  dato DATE,
  tidspunkt TIME,
  varighet INT,
  informasjon VARCHAR(100),
  personlig_form INT,
  prestasjon INT,
  CONSTRAINT treningsøkt_pk PRIMARY KEY (ID)
);
CREATE TABLE notat(ID INT NOT NULL AUTO_INCREMENT,
  treningsformål TEXT,
  treningsopplevelse TEXT,
  treningsøktID INT,
  CONSTRAINT notat_pk PRIMARY KEY (ID),
  CONSTRAINT notat_fk FOREIGN KEY (treningsøktID) REFERENCES treningsøkt(ID)
);
CREATE TABLE apparat(ID INT NOT NULL AUTO_INCREMENT,
  navn VARCHAR(30),
  bruksanvisning TEXT,
  CONSTRAINT apparat_pk PRIMARY KEY (ID)
);
CREATE TABLE øvelse(ID INT NOT NULL AUTO_INCREMENT,
  navn VARCHAR(30),
  kilo INT,
  sett INT,
  beskrivelse TEXT,
  type VARCHAR(30),
  CONSTRAINT øvelse_pk PRIMARY KEY (ID)
);
CREATE TABLE apparatforøvelse(øvelseID INT NOT NULL,
  apparatID INT,
  CONSTRAINT apparatforøvelse_fk_1 FOREIGN KEY (øvelseID) REFERENCES øvelse(ID),
  CONSTRAINT apparatforøvelse_fk_2 FOREIGN KEY (apparatID) REFERENCES apparat(ID)
);
CREATE TABLE øvelsegruppe(ID INT NOT NULL AUTO_INCREMENT,
  navn VARCHAR(30),
  CONSTRAINT øvelsegruppe_pk PRIMARY KEY (ID)
);
CREATE TABLE øvelseigruppe(øvelseID INT NOT NULL,
  gruppeID INT,
  CONSTRAINT øvelseigruppe_fk_1 FOREIGN KEY (øvelseID) REFERENCES øvelse(ID),
  CONSTRAINT øvelseigruppe_fk_2 FOREIGN KEY (gruppeID) REFERENCES treningsøkt(ID)
);
CREATE TABLE treningsøktutførerøvelse(treningsøktID INT NOT NULL,
  øvelseID INT,
  CONSTRAINT treningsøktutførerøvelse_fk_1 FOREIGN KEY (treningsøktID) REFERENCES treningsøkt(ID),
  CONSTRAINT treningsøktutførerøvelse_fk_2 FOREIGN KEY (øvelseID) REFERENCES øvelse(ID)
);

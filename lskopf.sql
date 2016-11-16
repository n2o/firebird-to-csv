CREATE TABLE LSKOPF (
    IID                   integer not null primary key,
    ADRESSEN_ID           integer,
    AUFTRAGSNUMMER        varchar(12) not null unique,
    DATUM                 date,
    SACHBEARBEITER        varchar(30),
    SACHBEARBEITER1       varchar(30),
    LIEFERANSCHNR         varchar(12),
    LIEFERANSCHKURZNAME1  varchar(30),
    LIEFERANSCHKURZNAME2  varchar(30),
    LIEFERANSCHKURZNAME3  varchar(30),
    LIEFERANSCHSTR        varchar(30),
    LIEFERANSCHSTR1       varchar(30),
    LIEFERANSCHPLZ        varchar(5),
    LIEFERANSCHORT        varchar(30),
    LIEFERANSCHLAND       varchar(30),
    KUNDEN_BESTELLNR      varchar(30),
    KUNDEN_AUFTRAGS_NR    varchar(30),
    VERSANDART            varchar(20),
    AUFPOS_ANZ            real,
    AUFPOS_OFFEN          real,
    AUFSUM                real,
    AUFSUM_OFFEN          real,
    STATUS                smallint,
    GESGEWICHT            real,
    EZ_RECH_KENN          varchar(1),
    NACHN_KENN            varchar(1),
    TEIL_LIEFERUNG        varchar(1),
    PORTO_SPESEN_ZUSCHLAG real,
    BESTNR                varchar(12),
    BESTNAME1             varchar(30),
    BESTNAME2             varchar(30),
    BESTNAME3             varchar(30),
    BESTSTR               varchar(30),
    BESTPLZ               varchar(5),
    BESTORT               varchar(30),
    BESTLAND              varchar(30)
);
alter table LSKOPF
add constraint FK_LSKOPF_0
foreign key (ADRESSEN_ID) 
references ADRESSE (ID)
on delete NO ACTION 

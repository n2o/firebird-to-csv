import fdb
from random import randint

host = "localhost"
port = "3050"
database = "/home/firebird/foo.fdb"
user = "sysdba"
password = "masterkey"


def add_seed(con, cur):
    addresses = [
        (randint(10000, 99999), "Herr", "", "Christian", "Meter", "", "Foostr. 42", "4711", "Baztown", "", "", "Deutschland", "0123 45678", "", "foo@bar.com", "http://www.bar.com")
    ]
    lskopf = [
        (3, "", "Zweiter Name", "Barstr. 4", "Name3", "48923", "Footown", "DE", randint(100000, 999999))
    ]
    cur.executemany("insert into adresse (KUNDENNR, ANREDE, KURZNAME, NAME1, NAME2, NAME3, STRASSE, PLZ1, ORT, PLZ2, PFACH, LAND, TELEFON, TELEFAX, EMAIL, HOMEPAGE) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", addresses)
    con.commit()
    cur.executemany("insert into lskopf (ADRESSEN_ID, LIEFERANSCHNAME1, LIEFERANSCHNAME2, LIEFERANSCHSTR, LIEFERANSCHNAME3, LIEFERANSCHPLZ, LIEFERANSCHORT, LIEFERANSCHLAND, AUFTRAGSNUMMER) values (?, ?, ?, ?, ?, ?, ?, ?, ?)", lskopf)
    con.commit()


def get_adresses(con, cur):
    query = """
        select
        (case when lieferanschname1 = '' Then (select name1 from adresse where id = adressen_id) else lieferanschname1 end) as Firma,
        (case when lieferanschname1 = '' Then (select name2 from adresse where id = adressen_id) else lieferanschname2 end) as Name,
        (case when lieferanschname1 = '' Then (select strasse from adresse where id = adressen_id) else lieferanschstr end) as Strasse,
        (case when lieferanschname1 = '' Then (select name3 from adresse where id = adressen_id) else lieferanschname3 end) as Adresszusatz,
        (case when lieferanschname1 = '' Then (select plz1 from adresse where id = adressen_id) else lieferanschplz end) as PLZ,
        (case when lieferanschname1 = '' Then (select ort from adresse where id = adressen_id) else lieferanschort end) as Ort,
        (case when lieferanschname1 = '' Then (select land from adresse where id = adressen_id) else lieferanschland end) as Land,

        (select telefon from adresse where id = adressen_id) as Telefon,
        (select email from adresse where id = adressen_id) as Email,
        (select kundennr from adresse where id = adressen_id) as KundenNr,

        auftragsnummer as Referenz,
        'Saegeblaetter / Saegebaender' as Inhalt,
        ' ' as Gewicht,
        ' ' as Nachnahmebetrag
        from lskopf
    """
    cur.execute(query)
    return cur.fetchall()


if __name__ == '__main__':
    con = fdb.connect(host=host, port=port, database=database, user=user, password=password)

    # Create Object that operates in the context of con
    cur = con.cursor()

    add_seed(con, cur)
    entries = get_adresses(con, cur)
    for entry in entries:
        print(entry)

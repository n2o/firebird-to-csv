import fdb
from random import randint

host = "localhost"
port = "3050"
database = "/home/firebird/foo.fdb"
user = "sysdba"
password = "masterkey"


def add_seed(cur):
    addresses = [
        (randint(10000, 99999), "Herr", "", "Christian", "Meter", "", "Foostr. 42", "4711", "Baztown", "", "", "Deutschland", "0123 45678", "", "foo@bar.com", "http://www.bar.com")
    ]
    lskopf = [
        (randint(0, 1024), 0, randint(100, 200))
    ]
    cur.executemany("insert into adresse (KUNDENNR, ANREDE, KURZNAME, NAME1, NAME2, NAME3, STRASSE, PLZ1, ORT, PLZ2, PFACH, LAND, TELEFON, TELEFAX, EMAIL, HOMEPAGE) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", addresses)


def get_adresses(cur):
    query = "select "
    pass


if __name__ == '__main__':
    con = fdb.connect(host=host, port=port, database=database, user=user, password=password)

    # Create Object that operates in the context of con
    cur = con.cursor()

    add_seed(cur)
    con.commit()

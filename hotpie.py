import fdb

host = "localhost"
port = "3050"
database = "/home/n2o/Projects/firebird-to-xml/database/hotpie.fdb"
user = "sysdba"
password = "masterkey"

con = fdb.connect(host=host, port=port, database=database, user=user, password=password) 

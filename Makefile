all:
	lein uberjar
	zip -r -j firebird-to-csv.zip target/uberjar/*-standalone.jar config.edn

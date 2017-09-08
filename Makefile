all:
	LEIN_SNAPSHOTS_IN_RELEASE=1 lein do clean, uberjar
	zip -r -j firebird-to-csv.zip target/uberjar/*-standalone.jar config.edn

zip:
	zip -r -j firebird-to-csv.zip target/uberjar/*-standalone.jar config.edn


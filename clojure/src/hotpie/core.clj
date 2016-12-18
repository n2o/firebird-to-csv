(ns hotpie.core
  (:gen-class)
  (:require [clojure.edn :as edn]
            [clojure.data.csv :as csv]
            ;[hikari-cp.core :refer :all]
            [clojure.java.jdbc :as jdbc]
            [clojure.java.io :as io])
  (import (org.firebirdsql.pool FBSimpleDataSource)))

(require '[korma.core :as sql])
(use '[korma.db])

(def db-config
  (firebird {:db "/home/firebird/foo.fdb"
             :host "localhost"
             :port "3050"
             :user "sysdba"
             :password "masterkey"
             :make-pool? false}))

(defdb fdb db-config)

fdb

(default-connection fdb)

(sql/exec-raw fdb "SELECT * FROM adresse" :results)

(def data
  (edn/read-string (slurp "config.edn")))

#_(defn get-rows
  "Makes database query and returns all matching rows fitting to :query."
  []
  (jdbc/with-db-connection [conn {:datasource datasource}]
    (jdbc/query conn (:query data))))

#_(defn convert-one-row
  "Take one row from the database and apply the order of the fields."
  [row]
  (map #((keyword %) row) (get-in data [:csv :order])))

#_(defn rows->csv
  "Given all rows from database, convert them and write them to a file."
  [rows]
  (let [csv-data (:csv data)]
    (with-open [out-file (io/writer (:file-location csv-data))]
      (csv/write-csv
       out-file
       (cons (get-in data [:csv :headers]) rows)
       :separator (get-in data [:csv :separator])))))

#_(defn -main []
  (rows->csv (map convert-one-row (get-rows)))
  (close-datasource datasource))


#_(-main)

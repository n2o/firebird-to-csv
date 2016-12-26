(ns hotpie.core
  (:gen-class)
  (:require [clojure.edn :as edn]
            [clojure.data.csv :as csv]
            [clojure.string :refer [upper-case]]
            [clojure.java.io :as io]
            [korma.core :as sql])
  (:use [korma.db]))

(def db-config
  (firebird {:db "/home/firebird/foo.fdb"
             :host "localhost"
             :port "3050"
             :user "sysdba"
             :password "masterkey"
             :make-pool? false}))
(defdb fdb db-config)
(default-connection fdb)

(def data
  (edn/read-string (slurp "config.edn")))

(defn get-rows
  "Makes database query and returns all matching rows fitting to :query."
  []
  (sql/exec-raw fdb (:query data) :results))

(defn convert-one-row
  "Take one row from the database and apply the order of the fields."
  [row]
  (map #((keyword (upper-case (name %))) row) (get-in data [:csv :order])))

(defn rows->csv
  "Given all rows from database, convert them and write them to a file."
  [rows]
  (let [csv-data (:csv data)]
    (with-open [out-file (io/writer (:file-location csv-data))]
      (csv/write-csv
       out-file
       (cons (get-in data [:csv :headers]) rows)
       :separator (get-in data [:csv :separator])))))

(defn -main []
  (rows->csv (map convert-one-row (get-rows))))

(-main)

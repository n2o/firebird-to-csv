(ns hotpie.core
  (:gen-class)
  (:require [clojure.edn :as edn]
            [clojure.data.csv :as csv]
            [clojure.string :refer [upper-case]]
            [clojure.java.io :as io]
            [korma.core :as sql])
  (:use [korma.db]))

(def data
  (edn/read-string (slurp "config.edn")))

(def db-config
  (let [config (:firebird data)]
    (firebird {:db         (:database config)
               :host       (:host config)
               :port       (:port config)
               :user       (:username config)
               :password   (:password config)
               :make-pool? false})))
(defdb fdb db-config)
(default-connection fdb)

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
  (try
    (rows->csv (map convert-one-row (get-rows)))
    (catch Exception e (spit "error.log" (str (new java.util.Date) " Folgender Fehler ist aufgetreten: " (.getMessage e) "\n") :append true))))

#_(-main)

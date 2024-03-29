(ns hotpie.core
  (:gen-class)
  (:require [clojure.edn :as edn]
            [clojure.data.csv :as csv]
            [clojure.string :refer [upper-case]]
            [clojure.java.io :as io]
            [korma.db :refer :all]
            [korma.core :as sql])
  (:import (java.util Date)))

(def data
  (edn/read-string (slurp "config.edn")))

(def db-config
  (let [{:keys [database host port username password]} (:firebird data)]
    (firebird {:db database
               :host host
               :port port
               :user username
               :password password
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
  (map #((keyword (upper-case (name %))) row) (get-in data [:output :order])))

(defn rows->csv
  "Given all rows from database, convert them and write them to a file."
  [rows]
  (let [csv-data (:output data)
        headers (get-in data [:output :headers])]
    (with-open [out-file (io/writer (:file-location csv-data)
                                    :encoding (:encoding csv-data))]
      (csv/write-csv
       out-file
       (if-not (nil? headers)                              ;; wenn Header definiert sind, verwende sie
         (cons headers rows)
         rows)
       :separator (get-in data [:output :separator])))))

(defn -main []
  (try
    (rows->csv (map convert-one-row (get-rows)))
    (println "Success!")
    (catch Exception e (spit "error.log" (str (new Date) " Folgender Fehler ist aufgetreten: " (.getMessage e) "\n") :append true))))


(comment
  (-main)
  (get-rows)
  :end)

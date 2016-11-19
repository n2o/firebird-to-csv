(ns hotpie.core
  (:gen-class)
  (:require [clojure.edn :as edn]
            [clojure.data.csv :as csv]
            [hikari-cp.core :refer :all]
            [clojure.java.jdbc :as jdbc]
            [clojure.java.io :as io])
  (import (org.firebirdsql.pool FBSimpleDataSource)))

(def data
  (edn/read-string (slurp "config.edn")))

(def datasource-options
  (let [firebird (:firebird data)]
    {:connection-timeout 30000
     :validation-timeout 5000
     :idle-timeout       600000
     :max-lifetime       1800000
     :minimum-idle       10
     :maximum-pool-size  10
     :pool-name          "db-pool"
     :adapter            "firebird"
     :username           (:username firebird)
     :password           (:password firebird)
     :database-name      (:database firebird)
     ;;:host               (:host firebird)
     ;;:port               (:port firebird)
     }))

(def datasource
  (make-datasource datasource-options))

(defn get-rows
  "Makes database query and returns all matching rows fitting to :query."
  []
  (jdbc/with-db-connection [conn {:datasource datasource}]
    (jdbc/query conn (:query data))))

(defn convert-one-row
  "Take one row from the database and order the fields."
  [row]
  (map #((keyword %) row) (get-in data [:csv :order])))

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
  (rows->csv (map convert-one-row (get-rows)))
  (close-datasource datasource))


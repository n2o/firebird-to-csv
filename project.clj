(defproject hotpie "0.3.0"
  :description "Query Firebird database and convert the results to a CSV file."
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.csv "1.0.0"]
                 [org.firebirdsql.jdbc/jaybird "2.2.5"]
                 [korma "0.4.3"]]
  :main ^:skip-aot hotpie.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

(defproject hotpie "0.2"
  :description "Query Firebird database and convert the results to a CSV file."
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha19"]
                 [org.clojure/data.csv "0.1.3"]
                 [org.firebirdsql.jdbc/jaybird "2.2.5"]
                 [korma "0.4.3"]]
  :main ^:skip-aot hotpie.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

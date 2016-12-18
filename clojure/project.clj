(defproject hotpie "0.1"
  :description "Query Firebird database and convert the results to a CSV file."
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.csv "0.1.3"]
                 ;[hikari-cp "1.7.5"]
                 ;[org.clojure/java.jdbc "0.7.0-alpha1"]
                 [org.firebirdsql.jdbc/jaybird "2.2.5"]
                 [korma "0.4.3"]
                 [log4j "1.2.15" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]]
  :main ^:skip-aot hotpie.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

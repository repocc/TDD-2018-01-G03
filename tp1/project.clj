(defproject tp1-rule-validator "1.0.0-SNAPSHOT"
  :description "A simple clojure REST example"
  :url "http://materias.fi.uba.ar/7510/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :ring {:handler rest.core/app}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.2.0"]
                 [ring/ring-defaults "0.1.2"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [rage-db "1.0"]]
  :plugins [[lein-ring "0.8.13"]]
  :main ^:skip-aot rest.core
  :aot :all
  :profiles
   {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]]}})

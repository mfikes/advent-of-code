(defproject advent-of-cljs "0.1.0"
  :profiles {:dev
             {:dependencies [[tubular "1.1.1"]]
              :source-paths ["dev"]}}
  :dependencies [[org.clojure/clojure "1.9.0-RC2"]
                 [org.clojure/clojurescript "1.9.946"]
                 [org.clojure/spec.alpha "0.1.143"]
                 [org.clojure/test.check "0.10.0-alpha2"]])

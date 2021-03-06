(ns clojurewerkz.gizmo.middleware.logging
  (:require [clojure.string :as s]))

(defn wrap-logger
  [handler]
  (fn [env]
    (let [start (java.util.Date.)
          res   (handler env)]
      (println (format "Processing '%s' (for '%s' at %s) [%s]"
                       (:uri env)
                       (get-in env [:headers "host"])
                       start
                       (-> env :request-method name s/upper-case)))
      (println (format
                "Completed in %sms, status: %s\n"
                (- (.getTime (java.util.Date.)) (.getTime start))
                (:status res)))
      res)))

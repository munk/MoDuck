(ns moduck.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]
            [compojure.api.sweet :refer [context]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn get-all-data [])
(defn get-potoos [])
(defn create-potoo [])
(defn create-session [])
(defn delete-session [])


(defroutes routes
  (context "/api" []
      (context "/quacks" []
        (GET "/" [])
        (POST "/" []))
    (context "/sessions" []
      (GET "/" [])
      (POST "/" []))
    (context "/users" []
        (GET "/" [])
        (POST "/" [])))
  (GET "/" [] (resource-response "index.html" {:root "public"}))

  (resources "/"))


(def dev-handler (-> #'routes wrap-reload))

(def handler routes)


(defn wrap-connection [handler conn]
  (fn [req] (handler (assoc req :db-conn conn))))

(defn moduck-handler [db-connection]
  (-> routes
      (wrap-connection db-connection)))

(ns uglybird.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]
            [compojure.api.sweet :refer [context]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn create-user [req]
  (let [conn (:db-conn req)
        {:keys [username password]} (:body req)
        password (encrypt password)
        _ (db/create-user conn username password)
        user (db/find-user-by-username conn username)]
    (-> (resp/response {:name username})
        (assoc :session (assoc (:session req) :identity user)))))

(defn get-all-data [])
(defn get-potoos [])
(defn create-potoo [])
(defn create-session [])
(defn delete-session [])


(defroutes routes
  (context "/api" []
      (context "/potoos" []
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

(defn uglybird-handler [db-connection]
  (-> routes
      (wrap-connection db-connection)))

(defrecord WebServer [opts container postgres-connection]
  component/Lifecycle
  (start [component]
    (log/info "Starting web server with params:" opts)
    (let [req-handler (uglybird-handler (:db-spec postgres-connection))
          container (run-jetty req-handler opts)]
      (assoc component :container container)))
  (stop [component]
    (log/info "Stopping web server")
    (.stop container)
    component))


(comment

  )

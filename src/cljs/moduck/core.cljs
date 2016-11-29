(ns moduck.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [moduck.events]
              [moduck.subs]
              [moduck.routes :as routes]
              [moduck.views :as views]
              [moduck.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))

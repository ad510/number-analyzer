(use '[ring.adapter.jetty :only [run-jetty]])

(defn handler [req]
  {:body "hello world" :headers {} :status 200})

(defonce server (run-jetty #'handler {:port 8000 :join? false}))

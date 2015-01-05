(use '[ring.adapter.jetty :only [run-jetty]]
     'hiccup.page)

(defn handler [req]
  {:body (html5 [:head [:title "title"]
                       [:meta {:http-equiv "content-type" :content "text/html; charset=utf-8"}]]
                [:body [:p "hello world"]])
   :headers {}
   :status 200})

(defonce server (run-jetty #'handler {:port 8000 :join? false}))

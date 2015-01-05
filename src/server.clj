(use '[ring.adapter.jetty :only [run-jetty]]
     'compojure.core
     'hiccup.page
     'hiccup.util)

(def content-type [:meta {:http-equiv "content-type" :content "text/html; charset=utf-8"}])

(def home (html5 [:head [:title "title"] content-type]
                 [:body [:p "hello world"]]))

(defroutes handler
  (GET "/" [] home)
  (GET "/:uri" [uri]
    (let [id (try (bigdec uri) (catch NumberFormatException ex uri))]
      (if (number? id)
        (html5 [:head [:title "number"] content-type]
               [:body [:p "hello, " (escape-html id)]])
        home))))

(defonce server (run-jetty #'handler {:port 8000 :join? false}))

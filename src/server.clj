(use '[ring.adapter.jetty :only [run-jetty]]
     'compojure.core
     'hiccup.page
     'hiccup.util)

(def content-type [:meta {:http-equiv "content-type" :content "text/html; charset=utf-8"}])

(defroutes handler
  (GET "/" [] (html5 [:head [:title "title"] content-type]
                     [:body [:p "hello world"]]))
  (GET "/:id" [id] (html5 [:head [:title "title2"] content-type]
                          [:body [:p "hello, " (escape-html id)]])))

(defonce server (run-jetty #'handler {:port 8000 :join? false}))

(use '[ring.adapter.jetty :only [run-jetty]]
     'compojure.core
     'hiccup.form
     'hiccup.page
     'hiccup.util)

(def content-type [:meta {:http-equiv "content-type" :content "text/html; charset=utf-8"}])

(def home (html5 [:head [:title "Number Analyzer"] content-type]
                 [:body [:p "Enter a number: " (text-field "number")
                            [:button {:onclick "location = document.getElementById('number').value"} "Submit"]]]))
;(form-to [:get ""] (text-field "number") (submit-button "Submit"))


(defroutes handler
  (GET "/" [] home)
  (GET "/:uri" [uri]
    (let [id (try (bigdec uri) (catch NumberFormatException ex uri))]
      (if (number? id)
        (html5 [:head [:title (escape-html id)] content-type]
               [:body [:h1 (escape-html id) " is "
                        (if (zero? id) "zero"
                            (str (if (pos? id) "a positive " "a negative ")
                                 (if (zero? (rem id 1)) "integer" "decimal")))]
                      (cond (and (pos? id) (zero? (rem id 1)))
                            [:p "Factors of " (escape-html id) ": "
                                (interpose ", " (filter #(zero? (rem id %)) (range 1 (inc (Math/sqrt id)))))])])
        home))))

(defonce server (run-jetty #'handler {:port 8000 :join? false}))

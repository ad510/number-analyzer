(use '[ring.adapter.jetty :only [run-jetty]]
     'compojure.core
     'hiccup.form
     'hiccup.page
     'hiccup.util)

(def content-type [:meta {:http-equiv "content-type" :content "text/html; charset=utf-8"}])

(def number-form [:p "Enter a number: " (text-field "number")
                     [:button {:onclick "location = document.getElementById('number').value"} "Submit"]])
;(form-to [:get ""] (text-field "number") (submit-button "Submit"))

(defroutes handler
  (GET "/" [] (html5 [:head [:title "Number Analyzer"] content-type]
                     [:body number-form]))
  (GET "/:uri" [uri]
    (let [id (try (bigdec uri) (catch NumberFormatException ex uri))]
      (html5 [:head [:title (escape-html id)] content-type]
             [:body number-form
                    [:h1 (escape-html id) " is "
                         (cond (not (number? id)) "not a number"
                               (zero? id) "zero"
                               :else (str (if (pos? id) "a positive " "a negative ")
                                          (if (zero? (rem id 1)) "integer" "decimal")))]
                    (cond (and (number? id) (pos? id) (zero? (rem id 1)))
                          [:p "Factors of " (escape-html id) ": "
                              (interpose ", " (filter #(zero? (rem id %)) (range 1 (inc (Math/sqrt id)))))])]))))

(defonce server (run-jetty #'handler {:port 8000 :join? false}))

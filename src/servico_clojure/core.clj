(ns servico-clojure.core
  (:require [io.pedestal.http.route :as route]
            [io.pedestal.http :as http]))

(defn funcao-hello
  "request -> string
  Exibe hello world na aplicação"
  [request]
  {:status 200 :body "Hello World"})

(comment
 "Define as rotas da aplicação sendo que cada rota é definida por set (conjunto)
  que é passado para a função route/expand-routes de io.pedesta.http.route.

  Cada um dos vetores passados no set tem o formato:
  [\"endpoint\" :method callback :route-name :name-of-the-route]")
(def routes (route/expand-routes #{
                                  ["/hello" :get funcao-hello :route-name :hello-world]
                                  }))

(comment
 "Inicia e configura o serviço com Pedestal.

  Os parâmetros são:
  ::http/routes nome da função que contem as rotas
  ::http/port   em qual porta o servidor irá rodar
  ::http/type   qual servidor usado jetty/tomcat/...
  ::http/join?  boolean, true é default e false para ambiente de prod")
(def service-map {::http/routes routes
                  ::http/port   8080
                  ::http/type   :jetty
                  ::http/join?   false})

;; inicia o servidor
(http/start (http/create-server service-map))
(println "Serviço iniciado na porta 8080...")

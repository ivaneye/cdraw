(ns cdraw.core-test
  (:require [clojure.test :refer :all]
            [cdraw.uml :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))


(defclz Context {:fields ["+strategy:Stragety"] :methods ["+contextInterface()"]})
(defclz Stragety {:methods ["+algorithmInterface()"]})
(defclz ConcreteStrategyA {:methods ["+algorithmInterface()"]})
(defclz ConcreteStrategyB {:methods ["+algorithmInterface()"]})
(defclz ConcreteStrategyC {:methods ["+algorithmInterface()" "+algorithmInterface()"]})

(defrelation a Context :u Stragety)
(defrelation aa ConcreteStrategyA :e Stragety)
(defrelation bb ConcreteStrategyB :c Stragety)
(defrelation cc ConcreteStrategyC :i Stragety)


(watch (to-file "/home/ivan/t.dot") "/home/ivan/t.png")

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

(defrelation Context :u Stragety)
(defrelation ConcreteStrategyA :e Stragety)
(defrelation ConcreteStrategyB :c Stragety)
(defrelation ConcreteStrategyC :i Stragety)

(defsub "Cluster2" Context)
(defsub "Cluster1" Stragety ConcreteStrategyA ConcreteStrategyB ConcreteStrategyC)

(label Context "hjhhhhh")

(watch (to-file "E:/t.dot") "E:/t.png")

(ns cdraw.core-test
  (:require [clojure.test :refer :all]
            [cdraw.uml :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))


(defclass Context {:f ["+strategy:Stragety"] :m ["+contextInterface()"]})
(defclass Stragety {:m ["+algorithmInterface()"]})
(defclass ConcreteStrategyA {:m ["+algorithmInterface()"]})
(defclass ConcreteStrategyB {:m ["+algorithmInterface()"]})
(defclass ConcreteStrategyC {:m ["+algorithmInterface()" "+algorithmInterface()"]})

(defrelation Context :u Stragety {:h "1..*" :t "*"})
(defrelation ConcreteStrategyA :e Stragety)
(defrelation ConcreteStrategyB :c Stragety)
(defrelation ConcreteStrategyC :i Stragety)

(defsub "调用" Context)
(defsub "策略" Stragety ConcreteStrategyA ConcreteStrategyB ConcreteStrategyC)

(label Context "这是个
                              Message")

(watch (to-file "/home/ivan/t.dot") "/home/ivan/t.jpg")

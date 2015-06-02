(ns cdraw.core
  (:require [clojure.string :as str]))

(def __node__ (atom []))

(defn add-to-node [node]
  (swap! __node__ conj node))

(defn- gets [f m]
  (if-let [f (f m)]
    (str/join "\\l" f) ""))

(defn field [m]
  (gets :fields m))

(defn method [m]
  (gets :methods m))

;定义类
(defmacro defclz
  ([name] `(do (def ~name (str '~name "[label=\"{" '~name "|}\"]\n\n"))
               (add-to-node ~name)))
  ([name args] `(do (def ~name (str '~name "[label=\"{" '~name "| " (field ~args) "|" (method ~args) "}\"]\n\n"))
                    (add-to-node ~name))))

;定义类之间的
(defmacro defrelation [name & args]
  (case (second args)
    ;has a,关联
    :u `(do (def ~name (str "edge[arrowhead=\"vee\", style=\"filled\"]\n" (first '~args) "->" (last '~args) "\n\n"))
            (add-to-node ~name))
    ;依赖
    :d `(do (def ~name (str "edge[arrowhead=\"vee\", style=\"dotted\"]\n" (first '~args) "->" (last '~args) "\n\n"))
            (add-to-node ~name))
    ;聚合
    :p `(do (def ~name (str "edge[arrowhead=\"odiamond\", style=\"filled\"]\n" (last '~args) "->" (first '~args) "\n\n"))
            (add-to-node ~name))
    ;组合
    :c `(do (def ~name (str "edge[arrowhead=\"diamond\", style=\"filled\"]\n" (last '~args) "->" (first '~args) "\n\n"))
            (add-to-node ~name))
    ;继承
    :e `(do (def ~name (str "edge[arrowhead=\"onormal\", style=\"filled\"]\n" (first '~args) "->" (last '~args) "\n\n"))
            (add-to-node ~name))
    ;实现
    :i `(do (def ~name (str "edge[arrowhead=\"onormal\", style=\"dotted\"]\n" (first '~args) "->" (last '~args) "\n\n"))
            (add-to-node ~name))
    ))

;输出到文件中
(defn to-file [s]
  (spit s (str "digraph G {
    node[fontname =\"Microsoft YaHei\",shape=record,style=\"filled\",color=\"black\",fillcolor=\".7 .3 1.0\"]\n {rank=same}\n\n  " (str/join " " @__node__) "}" :encoding "UTF-8"))
  s)

;生成图片
(defn watch [s t]
  (.exec (Runtime/getRuntime) (str "dot -Tpng " s " -o " t)))

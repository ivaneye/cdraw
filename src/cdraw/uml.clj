(ns cdraw.uml
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

(defn _msg [& r]
  (if r (str "[headlabel=\" " (:head (first r)) "\",taillabel=\"" (:tail (first r)) "\"]") ""))

;定义类之间的
(defmacro defrelation [c1 type c2 & rest]
  (case type
    ;has a,关联
    :u `(add-to-node (str "edge[arrowhead=\"vee\", style=\"filled\"]\n" '~c1 "->" '~c2 (_msg ~@rest) "\n\n"))
    ;依赖
    :d `(add-to-node (str "edge[arrowhead=\"vee\", style=\"dotted\"]\n" '~c1 "->" '~c2 (_msg ~@rest) "\n\n"))
    ;聚合
    :p `(add-to-node (str "edge[arrowhead=\"odiamond\", style=\"filled\"]\n" '~c2 "->" '~c1 (_msg ~@rest) "\n\n"))
    ;组合
    :c `(add-to-node (str "edge[arrowhead=\"diamond\", style=\"filled\"]\n" '~c2 "->" '~c1 (_msg ~@rest) "\n\n"))
    ;继承
    :e `(add-to-node (str "edge[arrowhead=\"onormal\", style=\"filled\"]\n" '~c1 "->" '~c2 (_msg ~@rest) "\n\n"))
    ;实现
    :i `(add-to-node (str "edge[arrowhead=\"onormal\", style=\"dotted\"]\n" '~c1 "->" '~c2 (_msg ~@rest) "\n\n"))
    )
  )

;定义子包,name必须以cluster开头
(defmacro defsub [label & rest]
  (let [t (rand)
        s (str/replace (str t) "." "")
        clu (str "cluster" s)]
  `(add-to-node (str "subgraph" ~clu " {\nlabel=\"" '~label "\"\nbgcolor=\"mintcream\";\n\n" ~@rest "}\n\n"))))

;定义label
(defmacro label [node msg]
  (let [t (rand)
        s (str/replace (str t) "." "")
        lb (str "label" s)]
    `(add-to-node (str ~lb "[style=\"filled\",fillcolor=\"powderblue\", color=\"powderblue\", label=\"" '~msg "\"]\n"
                       "edge [ arrowhead=\"none\", style=\"dashed\"]\n" '~node "->" ~lb "\n"))))

;输出到文件中
(defn to-file [s]
  (spit s (str "digraph G {
    node[fontname =\"Microsoft YaHei\",shape=record,style=\"filled\",color=\"black\",fillcolor=\"skyblue\"]\n {rank=same}\n\n  " (str/join " " @__node__) "}"))
  s)

;生成图片
(defn watch [s t]
  (.exec (Runtime/getRuntime) (str "dot -Tpng " s " -o " t)))

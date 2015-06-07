# cdraw

基于Graphviz的Clojure封装

## Usage

以命令模式的UML为例，演示cdraw的使用

![](/doc/cdraw/command.jpg)

## 安装Graphviz

cdraw是对Graphviz的简单封装，请先安装Graphviz

## 添加依赖

- 使用leiningen新建一个Clojure项目uml
- 在project.clj中添加如下依赖

```clojure
[com.ivaneye/cdraw "0.2.0"]
```

## 定义类

- 在uml.core中编写如下代码

```clojure
(ns uml.core
  (:require [cdraw.uml :refer :all]))
  
 (defclass Client)
 (defclass Inboker)
 (defclass Receiver {:m ["Action()"]})
 (defclass Command {:m ["Execute()"]})
 (defclass ConcreteCommand {:f  ["state"] :m ["Execute()"]})
```
- 第一，二行，引入了cdraw
- defclass定义了类，及其字段(:f)和方法(:m),效果图如下:

![](/dot/cdraw/c1.png)

<!-- more -->

## 添加依赖

- 继续在uml.core中添加依赖代码

```clojure
(defrelation Client :u Receiver) 
(defrelation ConcreteCommand :u Receiver {:t "reveiver"})  
(defrelation Client :d ConcreteCommand)
(defrelation ConcreteCommand :e Command)
(defrelation Invoker :p Command)
```

- Client关联Receiver
- ConcreteCommand关联Receiver
- Client依赖ConcreteCommand
- ConcreteCommand继承Command
- Invoker聚合Command

```
关联  :u 
依赖 :d
聚合 :p
组合 :c
继承 :e
实现 :i
```

![](/dot/cdraw/c2.png)

## 添加label

- 在uml.core中添加如下代码

```clojure
(label ConcreteCommand "receive-\\>Action()")
```

- >需要转义

![](/dot/cdraw/c3.png)

## 定义子包

```clojure
(defsub "Sub Command" Command ConcreteCommand)
```

- 第一个参数为子包名称
- 后续为需要包含到子包中的类

![](/dot/cdraw/c4.png)

## 生成

- 在uml.core中添加如下代码

```clojure
(watch (to-file "/t.dot") "/t.png")
```

- to-file生成符合Graphviz的dot文件
- watch生成需要的最终文件，这里是生成了png图片

## 最终代码

```clojure
(ns uml.core
  (:require [cdraw.uml :refer :all]))


(defclass Client)
(defclass Invoker)
(defclass Receiver {:m ["Action()"]})
(defclass Command {:m ["Execute()"]})
(defclass ConcreteCommand {:f  ["state"] :m ["Execute()"]})

(defrelation Client :u Receiver)
(defrelation ConcreteCommand :u Receiver {:t "reveiver"})
(defrelation Client :d ConcreteCommand)
(defrelation ConcreteCommand :e Command)
(defrelation Invoker :p Command)

(label ConcreteCommand "receive-\\>Action()")

(defsub "Sub Command" Command ConcreteCommand)

(watch (to-file "/t.dot") "/t.png")
```

## License

Copyright © 2015 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

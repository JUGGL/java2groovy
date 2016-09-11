# How to switch a project from Java To Groovy
## For fun and profit!

## Overview
This presentation for the Java Users Group of Greater Louisville shows the path and potential
benefits of switching from writing Java code to writing code for the JVM in [Groovy](http://groovy-lang.org/).
The slides and notes to accompany this presentation can be found [HERE](https://docs.google.com/presentation/d/1SnJJekLuuXSVm7NyVC1A9Hp94jJd32QUcyXmeEW18q0/edit?usp=sharing).

## Build And Run The Project
```
mvn clean compile exec:java
```

## Description
* Start with the branch: `Step-00`, this is a simple Java project using [Vert.x](https://vertx.io).
  * Study the project and make sure you understand what is going on with the lambdas and method references
* Switch to the `Step-01` branch to see that we have now added a new class, but this one is written in Groovy 
  and can work alongside the Java classes!
  * The Maven `pom.xml` file is set up to be able to compile Groovy source files along with Java source files
  * Look at the comments in the Groovy code to see some interesting syntactic sugar available with Groovy
* Switch to `Step-02` and see that we have converted the original Java source file to Groovy
  * Take note of the syntax differences:
    * Map literals
    * Different method references (Closures actually!)
    * Differences between List syntax in Java and Groovy
* Switch to `Step-03` for a view of some more advanced features of Groovy:
  * [Type coersion](http://www.groovy-lang.org/objectorientation.html#_positional_argument_constructor) instead of constructors
  * [Groovy Beans](http://groovy-lang.org/style-guide.html#_initializing_beans_with_named_parameters_and_the_default_constructor), which do not need getters/setters/constructors/builders
  * Groovy's [JSON API](http://www.groovy-lang.org/json.html)
  * [Slashy Strings](http://www.groovy-lang.org/syntax.html#_slashy_string)
* `Step-04`: Bonus! Using Groovy for CLI scripts
  * Using ranges to create [loops](http://grails.asia/groovy-each-examples)
  * Handling user input
  * Interesting Object [comparisons](http://groovy-lang.org/operators.html#_relational_operators)
  * Simplified RegEx handling with the [Find Operator](http://groovy-lang.org/operators.html#_find_operator)
  * Multiple assignment with the [equals operator](http://groovy-lang.org/operators.html#_multiple_assignment)
  
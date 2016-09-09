# How to switch a project from Java To Groovy
## For fun and profit!

* Start with the branch: `Step-00`, this is a simple Java project using [Vert.x](https://vertx.io).
  * Study the project and make sure you understand what is going on with the lambdas and method references
* Switch to the `Step-01` branch to see that we have now added a new class, but this one is written in Groovy and can work alongside the Java classes!
  * The Maven `pom.xml` file is set up to be able to compile Groovy source files along with Java source files
* Switch to `Step-02` and see that we have converted the original Java source file to Groovy
  * Take note of the syntax differences:
    * Map literals
    * Different method references (Closures actually!)
    * Differences between List syntax in Java and Groovy

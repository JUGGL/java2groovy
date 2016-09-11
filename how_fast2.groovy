#!/usr/bin/env groovy

// Range based loops
(1..10000).each { it ->
    print("${it}.")
}
println()


// Multiple assignment!
def (a, b, c) = "One Two Three".split(" ")

println("${c} ${b} ${a}")


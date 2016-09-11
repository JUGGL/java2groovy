#!/usr/bin/env groovy

println("This is a groovy script!")

println("Please enter your name")
def userInput = (new Scanner(System.in)).nextLine()

if (userInput) {                     // Is userInput non-null and non-empty?
    println("You entered: ${userInput}")
} else {
    println("CHEATER! You didn't enter anything!")
}

if (userInput=="Test") {             // Simplified String comparison!!! In Groovy, this is not an identity check!
    println("You must be a developer!")
}

if (userInput=~/.*Deve[lmno].*/) {   // Does the user input match the regular expression?
    println("You are Deven!!!")
}

if (System.getenv("PWD")) {    // Is this ENV variable non-null and non-empty?
    println("Current working directory is: ${System.getenv('PWD')}")
}
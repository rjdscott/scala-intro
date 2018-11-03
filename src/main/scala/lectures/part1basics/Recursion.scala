package lectures.part1basics

import com.sun.org.apache.xpath.internal.functions.FuncFalse

import scala.annotation.tailrec

object Recursion extends App {

    // Factorial function
    def factorial(n: Int): Int = {
        if (n <= 0) 1
        else {
            println("Computing factorial of " + n + " - I first need factorial of " + (n - 1))
            val result = n * factorial(n - 1)
            println("Computed value of " + n)
            result
        }
    }

    println(factorial(10))

    // TAIL RECURSION - Use a recursive call as last expression, not first
    def factorial2(n: Int): BigInt = {

        @tailrec
        def factHelper(x: Int, accumulator: BigInt): BigInt = {
            if (x <= 1) accumulator
            else factHelper(x - 1, x * accumulator) // TAIL RECURSION
        }

        factHelper(n, 1)
    }

    println(factorial2(50))

    // WHEN YOU NEED LOOPS, USE TAIL RECURSION

    def stringConcat(aString: String, n: Int): String = {

        @tailrec
        def concatHelper(x: Int, accumulator: String): String = {
            if (x <= 1) accumulator
            else concatHelper(x - 1, aString + accumulator)
        }

        concatHelper(n, "")
    }

    println(stringConcat("Hi ", 40))

    @tailrec
    def stringConcat2(aString: String, n: Int, accumulator: String): String = {
        if (n <= 1) accumulator
        else stringConcat2(aString, n - 1, aString + accumulator)
    }

    println(stringConcat2("Hello", 10, ""))

    // tail recursion using if else
    def isPrime(n: Int): Boolean = {
        @tailrec
        def isPrimeTailRec(t: Int, isStillPrime: Boolean): Boolean = {
            if (!isStillPrime) false
            else if (t <= 1) true
            else isPrimeTailRec(t - 1, n % t != 0 && isStillPrime)
        }

        isPrimeTailRec(n / 2, true)
    }

    println(isPrime(2003))
    println(isPrime(2000001))


    // tail recursion using two accumulators with fib
    def fib(n: Int): Int = {
        def fibTailRec(i: Int, last: Int, secondLast: Int): Int = {
            if (i >= n) last
            else fibTailRec(i + 1, last + secondLast, last)
        }

        if (n <= 2) 1
        else fibTailRec(2, 1, 1)
    }

    println(fib(8))

}

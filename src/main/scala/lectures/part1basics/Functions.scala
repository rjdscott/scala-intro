package lectures.part1basics

object Functions extends App {

    // basic function used in a code block
    def aFunction(a: String, b: Int): String = {
        a + " " + b
    }

    println(aFunction("hello", 3))

    // paramaterless function
    def aParamaterlessFunction(): Int = 45

    println(aParamaterlessFunction())
    println(aParamaterlessFunction)

    // create a function that repeats a string n times
    def aRepeatedFunction(aString: String, n: Int): String = {
        if (n == 1) aString
        else aString + aRepeatedFunction(aString, n - 1)
    }

    println(aRepeatedFunction("Hello ", 3))

    // When you need loops, use recursion

    // you can use unit as a return type for when we want to make side effects
    def aFunctionWithSideEffects(aString: String): Unit = {
        println(aString)
    }

    def aBigFunction(n: Int): Int = {
        def aSmallerFunction(a: Int, b: Int): Int = a + b

        aSmallerFunction(n, n - 1)
    }

    // Factorial function
    def aFactorialFunction(n: Int): Int = {
        if (n <= 0) 1
        else n * aFactorialFunction(n - 1)
    }

    println(aFactorialFunction(5))

    // fibonacci
    def fib(n: Int): Int = {
        if (n <= 2) 1
        else fib(n - 1) + fib(n - 2)
    }

    println(fib(8))

    def isPrime(n: Int): Boolean = {
        def isPrimeUntil(t: Int): Boolean = {
            if (t <= 1) true
            else n % t != 0 && isPrimeUntil(t - 1)

        }

        isPrimeUntil(n / 2)
    }
    println(isPrime(37))
    println(isPrime(2003))
    println(isPrime(22))
}

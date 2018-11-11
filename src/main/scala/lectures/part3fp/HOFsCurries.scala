package lectures.part3fp

object HOFsCurries extends App {

    // The below is an example of a higher order function
    // that is, a function can be passed as a param in another function
    val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = null

    // map, filter, flatMap in MyList

    // function that applies a function n times over a value x
    // nTimes(f, n, x)
    // nTimes(f, 3, x) = f(f(f(x)))
    // nTimes(f, 3, x) = f(f(...f(x)) = nTimes(f, n-1, f(X))
    def nTimes(f: Int => Int, n: Int, x: Int): Int = {
        if(n <= 0) x
        else nTimes(f, n-1, f(x))
    }

    // functional programming is a direct mapping of mathematics

    val plusOne = (x: Int) => x + 1
    println(nTimes(plusOne, 10, 1))

    // lets do the above better
    // we are going to return a lambda ntb(f, n) = f(f(f(....(x)))
    // this needs to be a function that returns a lambda
    def nTimesBetter(f: Int => Int, n: Int): (Int => Int) = {
        if (n <= 0) (x: Int) => x
        else (x: Int) => nTimesBetter(f, n-1)(f(x))
    }

    val plus10 = nTimesBetter(plusOne, 10)
    println(plus10(1))

    // CURRIED FUNCTIONS - helper functions
    val superAdder: Int => Int => Int = (x: Int) => (y: Int) => x + y
    val add3 = superAdder(3)
    println(add3(10))

    // functions with multiple parameter lists
    def curriedFormatter(c: String)(x: Double): String = c.format(x)

    // sub functions to be used in currying
    val standardFormat: (Double => String) = curriedFormatter("%4.2f")
    val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

    // output of curried functions
    println(standardFormat(math.Pi))
    println(preciseFormat(math.Pi))

    // partial function applications extends this topic but covered in advanced topics.

    def toCurry(f: (Int, Int) => Int): (Int => Int => Int) = {
        x => y => f(x, y)
    }

    def fromCurry(f: (Int => Int => Int)): (Int, Int) => Int ={
        (x, y) => f(x)(y)
    }

    //def compose(f: Int => Int, g: Int => Int): Int => Int = {
    def compose[A,B,T](f: A => B, g: T => A): T => B = {
        x => f(g(x))
    }

    def andThen[A,B,C](f: A => B, g: B => C): A => C = {
        x => g(f(x))
    }

    def superAdder2: (Int => Int => Int) = toCurry(_ + _)
    def add4 = superAdder2(4)
    println(add4(17))

    def simpleAdder = fromCurry(superAdder)
    println(simpleAdder(4,17))

    val add2 = (x: Int) => x + 2
    val times3 = (x: Int) => x * 3

    val composed = compose(add2, times3)
    val ordered = andThen(add2, times3)
    println(composed(4))
    println(ordered(4))
}

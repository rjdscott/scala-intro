package lectures.part3fp

object AnonymousFunctions extends App {

    // OOP way of instanciating a function
    val doubler = new Function1[Int, Int] {
        override def apply(v1: Int): Int = v1 * 2
    }

    // FP way of doing the same thing is be done as follows - Anonymous function or lambda
    // this is a lambda
    // systatic sugar - is a value of function1
    val doubler2 = (x: Int) => x * 2

    // shorter hand
    val doubler3: Int => Int = x  => x * 2

    // what about multiple params, both examples are the same
    val adder = (a: Int, b: Int) => a + b

    // if you have multiple params you need parenths
    val adder2: (Int, Int) => Int = (a: Int, b: Int) => a + b

    // no params
    val justDoSomething = () => 3
    val justDoSomething2: () => Int = () => 3

    // notice the difference in results below
    println(justDoSomething) // prints function itself
    println(justDoSomething()) // prints value of function - must be called with parenths

    // another way of writing lambdas

    val stringToInt = { str: String =>
        str.toInt
    }

    // more syntactic sugar
    val niceIncrementer: Int => Int = (x: Int) => x + 1
    val niceAdder: (Int, Int) => Int = (a, b) => a + b

    // is same as
    val niceIncrementer2: Int => Int = _ + 1
    val niceAdder2: (Int, Int) => Int = _ + _

}

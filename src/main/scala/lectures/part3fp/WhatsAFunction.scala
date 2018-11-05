package lectures.part3fp

object WhatsAFunction extends App {

    // DREAM: use functions as first class elements
    // PROBLEM: OOP

    // we can actually store the below trait as a function and parse values to it
    val doubler = new MyFunction[Int, Int] {
        override def apply(element: Int): Int = element * 2
    }

    println(doubler(3))

    // function types = Function1[A,B]

    val stringToIntConverter = new Function[String, Int] {
        override def apply(string: String): Int = string.toInt
    }

    println(stringToIntConverter("40"))

    // syntatic sugar could be used for the following function
    val adder = new Function2[Int, Int, Int] {
        override def apply(v1: Int, v2: Int): Int = v1 + v2
    }

    println(adder(3,5))

    // using syntatic sugar for the above
    val adder2: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
        def apply(v1: Int, v2: Int): Int = v1 + v2
    }

    def concatenator: (String, String) => String = new Function2[String, String, String] {
        override def apply(v1: String, v2: String): String = v1 ++ v2
    }

    println(concatenator("hello, ","Scala!"))

    // Curried Function!
    // Create a function that returns a function and a value
    // Function1[Int, Function1[Int, Int]]

    val superAdder: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
        override def apply(v1: Int): Function1[Int, Int] = new Function1[Int, Int] {
            override def apply(v2: Int): Int = v1 + v2
        }
    }

    val adder3 = superAdder(3)
    println(adder3(4))
}

trait MyFunction[A, B] {
    def apply(element: A): B
}
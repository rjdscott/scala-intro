package lectures.part1basics

import scala.annotation.tailrec

object DefaultArgs extends App {

    // in this case we may want to parse default value at param level
    @tailrec
    def trFact(n: Int, acc: Int = 1): Int = {
        if (n <= 1) acc
        else trFact(n - 1, n * acc)
    }

    val fact10 = trFact(10) // mow we dont need to use acc input!

    println(fact10)

    // key work args
    def rect(height: Int, width: Int): Int = height * width

    println(rect(width = 10, height = 30))

}

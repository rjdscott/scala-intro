package lectures.part1basics

object VBNvsCBV extends App {

    // call by value - value computed before call, same value used everywhere
    def callByValue(x: Long): Unit = {
        println(" by value: " + x)
        println(" by value: " + x)
    }

    // call by name = ecpression passed literally and re-evaluated every time called
    def callByName(x: => Long): Unit = {
        println(" by name: " + x)
        println(" by name: " + x)
    }

    callByValue(System.nanoTime())
    callByName(System.nanoTime())

    // this is extremely helpful when it comes to streaming data

    def infinite(): Int = 1 + infinite()

    def printFirst(x: Int, y: => Int): Unit = println(x)

    // printFirst(infinite(), 34)
    printFirst(34, infinite())

}


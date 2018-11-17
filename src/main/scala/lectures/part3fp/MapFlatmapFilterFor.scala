package lectures.part3fp

/*
    Standard Library implementations of
      - map
      - flatMap
      - filter
    For documentation on how these work take a look at docs on scala-lang.org
 */
object MapFlatmapFilterFor extends App {

    val list = List(1,2,3)
    println(list)
    println(list.head)
    println(list.tail)

    println(list.filter(_ % 2 == 0))

    val toPair = (x: Int) => List(x, x + 1)
    println(list.flatMap(toPair))

    // ITERATION USING MAP, FLATMAP, FOREACH
    // Exercise: print out all combinations between two lists
    val nums = List(1,2,3,4)
    val chars = List('a','b','c','d')
    val cols = List("black", "white")

    // two combinations
    val combinations2 = nums.flatMap(n => chars.map(c => "" + c + n))
    println(combinations2)

    // three combinations
    val combinations3 = nums.flatMap(n => chars.flatMap(c => cols.map(col => "" + c + n + "-" + col)))
    println(combinations3)

    /*
        --> FOREACH
     */

    // single line statement
    nums.foreach(println)

    // implemented with a for
    for {
        n <- nums
    } println(n)

    /*
        --> FOR COMPREHENSIONS
     */

    // the below does the same as combinations3
    val forCombinations = for {
        n <- nums
        c <- chars
        col <- cols
    } yield "" + c + n + "-" + col

    // adding guards (if conditionals)
    val forCombinationsFilter = for {
        n <- nums if n % 2 == 0
        c <- chars
        col <- cols
    } yield "" + c + n + "-" + col
    println(forCombinationsFilter)

    //syntax overload - don't be afraid, they're okay!
    list.map( x =>
        x * 2
    )


}


package lectures.part3fp

object TuplesAndMaps extends App {

        // Tuples - finite ordered list
    val aTuple = (2, "Hello, Scala")

    println(aTuple._1)
    println(aTuple._2)
    println(aTuple.copy(_2 = "goodbye Java"))



}

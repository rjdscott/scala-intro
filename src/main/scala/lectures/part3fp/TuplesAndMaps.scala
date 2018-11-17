package lectures.part3fp

object TuplesAndMaps extends App {

    /*
        Tuples - finite ordered list
     */
    val aTuple = (2, "Hello Scala")

    println(aTuple._1)
    println(aTuple._2)
    println(aTuple.copy(_2 = "goodbye Java"))
    println(aTuple.swap) // swaps elements in place

    /*
        Maps - keys -> values
     */

    val aMap: Map[String, Int] = Map()

    val phonebook = Map(("Jim", 555),("Daniel", 890))
    val phonebook2 = Map("Jim" -> 555, "Daniel" -> 890) // Syntatic sugar
    val phonebook3 = Map("Jim" -> 555, "Daniel" -> 890).withDefaultValue(-1)

    //OPERATORS
    println(phonebook.contains("Jim"))   // boolean with key existence
    println(phonebook("Jim"))   // returns value
    //println(phonebook("Mary")) // throws bad error, can be fixed withDefaultValue
    println(phonebook3("Mary")) // uses .withDefaultValue(-1)

    // add a paring
    val newParing = "Mary" -> 678
    val newPhonebook = phonebook + newParing
    println(newPhonebook)

    // functionals - map, filter, flatmap
    println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))

    // filter keys
    println(phonebook.filterKeys(x => x.startsWith("J")))

    // map values
    println(phonebook.mapValues(number => number * 10))

    // conversions
    println(phonebook.toList)
    println(List(("Daniel", 345)).toMap)

    val names = List("Joan","James","Mary","Mark","Lisa","Paul")
    println(names.groupBy(name => name.charAt(0)))

}

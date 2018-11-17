package lectures.part3fp

import scala.annotation.tailrec

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


    /*
        Exercises
     */

    // 1. What happens when you have two keys of same chars
    val exercisePhonebook = Map("Jim" -> 555, "JIM" -> 345)
    println(exercisePhonebook)
    println(exercisePhonebook.map(pair => pair._1.toLowerCase -> pair._2))


    // 2. Create a social network using maps
    def add(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
        network + (person -> Set())

    def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
        val friendsA = network(a)
        val friendsB = network(b)

        network + (a -> (friendsA + b)) + (b -> (friendsB + a))
    }

    def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
        val friendsA = network(a)
        val friendsB = network(b)

        network + (a -> (friendsA - b)) + (b -> (friendsB - a))
    }

    def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
        def removeAux(friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] =
            if (friends.isEmpty) networkAcc
            else removeAux(friends.tail, unfriend(networkAcc, person, friends.head))

        val unfriended = removeAux(network(person), network)
            unfriended - person
    }

    val empty: Map[String, Set[String]] = Map()

    val network = add(add(empty,"Bob"), "Mary")
    println(network)
    println(friend(network, "Bob","Mary"))
    println(unfriend(friend(network, "Bob","Mary"), "Bob","Mary"))
    println(remove(friend(network, "Bob","Mary"), "Bob"))

    // build a small network with Bob,Jim,Mary
    val people = add(add(add(empty,"Bob"), "Mary"), "Jim")
    val jimBob = friend(people, "Bob","Jim")
    val testNet = friend(jimBob, "Bob","Mary")
    println(testNet)

    // find number of friends for a given person

    def nFriends(network: Map[String, Set[String]], person: String): Int = {
        if (!network.contains(person)) 0
        else network(person).size
    }

    println(nFriends(testNet, "Bob"))

    def mostFriends(network: Map[String, Set[String]]): String = {
        network.maxBy(pair => pair._2.size)._1
    }

    println(mostFriends(testNet))

    def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int = {
        // network.filterKeys(k => network(k).isEmpty).size
        network.count(_._2.isEmpty)
    }
    println(nPeopleWithNoFriends(testNet))

    def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {

        @tailrec
        def bfs(target: String, consideredProple: Set[String], discoveredPeople: Set[String]): Boolean ={
            if (discoveredPeople.isEmpty) false
            else {
                val person = discoveredPeople.head
                if (person == target) true
                else if (consideredProple.contains(person)) bfs(target, consideredProple, discoveredPeople.tail)
                else bfs(target, consideredProple + person, discoveredPeople.tail ++ network(person))
            }
        }

        bfs(b, Set(), network(a) + a)
    }

    println(socialConnection(testNet, "Mary", "Jim"))
    println(socialConnection(network, "Mary", "Bob"))
}

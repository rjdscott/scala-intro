package lectures.part2oop

object Objects extends App {

    // SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY

    object Person {

        // Use objects to impose class level functionality
        val N_EYES = 2

        def canFly: Boolean = false

        // factory method - used to construct a new instance
        def apply(mother: Person, father: Person): Person = new Person("Bobbie")

    }

    class Person(val name: String) {
        // instance level functionality

    }

    // both Class and object are COMPANIONS

    println(Person.N_EYES)
    println(Person.canFly)

    // use object as a SINGLETON Instance
    val mary = Person
    val mark = Person
    println(mary == mark)

    // when instantiating new instances of person they are not equal
    val norman = new Person("Norman")
    val norma = new Person("Norma")
    println(norman == norma)

    // factory methods to generate new objects from singletons
    val bobby = Person(norman, norma)
    println(bobby.name)

    // Scala Applications - Scala with def main(args: Array[String]): Unit
    // if you remove the 'extends App' you can wrap the whole class in the above main call

}

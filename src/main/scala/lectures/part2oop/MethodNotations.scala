package lectures.part2oop

object MethodNotations extends App {

    class Person(val name: String, favMovie: String) {
        def likes(movie: String): Boolean = movie == favMovie
        def hangsOutWith(person: Person): String = s"${this.name} is hanging out with ${person.name}"
        def +(person: Person): String = s"${this.name} is with with ${person.name}"
        def unary_! : String = s"$name, what the heck"
        def isAlive: Boolean = true
    }

    val mary = new Person("Mary", "Inception")

    // INFIX NOTATION - Syntactic Sugar - only works with methods with one param
    println(mary.likes("Inception"))
    println(mary likes "Inception") // infix notation - operator notation - is the same as above

    // Scala Operators are methods underneath!
    var tom = new Person("Tom", "Fight Club")
    println(mary hangsOutWith tom)
    println(mary + tom)

    // ALL OPERATORS ARE METHODS
    println(1 + 2)
    println(1.+(2))

    // PREFIX Notation
    val x = -1 // this is the same 1.unary_-
    val y = 1.unary_- // only works with + - ` !

    // POSTFIX Notation - works only with methods with no params
    println(mary isAlive)


}

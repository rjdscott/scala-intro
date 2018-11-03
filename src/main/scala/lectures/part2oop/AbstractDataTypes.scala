package lectures.part2oop

/*
 * Abstract Data Types
 *   Traits vs Abstract Classes
 *   1. Traits do not have constructor params
 *   2. multiple traits may be inherited by the same class
 *   3. Traits = behaviour, Abstract classes = type of thing
 */

object AbstractDataTypes extends App {

    // Abstract classes
    abstract class Animal {
        val creatureType: String
        def eat: Unit

    }

    // lets create a new class that extends the abstract class and overrides params
    class Dog extends Animal {
        override val creatureType: String = "Canine"
        override def eat: Unit = println("NomNomNom")
    }

    // Traits
    trait Carnivore {
        def eat(animal: Animal): Unit
        val preferredMeal: String = "Fresh Meat"

    }
    trait ColdBlooded

    // Class extending abstract with trait
    class Crocodile extends Animal with Carnivore with ColdBlooded {
        val creatureType: String = "Croc"
        def eat(animal: Animal): Unit = println(s"Im a croc and i'm eating a ${animal.creatureType}")
        def eat: Unit = println("Crunch Crunch")
    }

    val dog = new Dog
    val croc = new Crocodile

    croc.eat(dog)

}

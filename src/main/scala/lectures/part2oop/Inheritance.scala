package lectures.part2oop

/*
 * Inheritance and Traits
 *  - sub classes
 *  - super classes
 *  - Polymorphism
 *  - Overriding
 *  - preventing overrides with with final classes, final defs and sealed classes
 */


object Inheritance extends App {

    class Animal { // Super class of cat

        def eat: Unit = println("nom, nom, nom...")
        protected def speak: Unit = println("Yolo...") // only accessible to this class and no subclasses
        private def sleep: Unit = println("Bed time now...") // usable between this class and subclasses
        final def roll: Unit = println("Toll over")
    }

    class Cat extends Animal { // Subclass of animal
        def makeNoise: Unit = {
            speak
            println( "Told you so")
        }
    }

    val cat = new Cat
    cat.eat
    // cat.sleep - cannot access as it is private
    // cat.speak - cannot call this directly bur can through makeNoise
    cat.makeNoise // this accesses speak through cat class


    // constructors
    class Person(name: String, age: Int)
    class Adult(name: String, age: Int, iDCard: String) extends Person(name, age)

    // overriding
    class Dog(breed: String) extends Animal {
        override def eat: Unit = {
            super.eat
            println("Crunch crunch")
        }
    }

    var dog = new Dog("Kelpie")
    dog.eat

    // Polymorphism
    val unknownAnimal: Animal = new Dog("k9")
    unknownAnimal.eat

    // SUPER
    // 1. this can be done using super.

    // PREVENTING Overrides
    // 1. use keyword final on def - makes the class be the last to call
    // 2. Use keyword class on class
    //3. seal the class using keyword sealed
}

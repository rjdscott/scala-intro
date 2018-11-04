package lectures.part2oop

/*
 * Anonymous Classes
 *  - work for abstract and non-abstract classes
 *  - need to pass required constructor inputs
 */


object AnonymousClasses extends App {

    abstract class Animal {
        def eat: Unit
    }

    val funnyAnimal: Animal = new Animal {
        override def eat: Unit = println("hahahahah")
    }

    /*
    above is equivalent of doing the following

    val funnyAnimal: AnonymousClasses$$anon$1 = new Animal {
        override def eat: Unit = println("hahahahah")
    }
    */

    println(funnyAnimal.getClass) // prints: class lectures.part2oop.AnonymousClasses$$anon$1

    class Person(name: String) {
        def sayHi: Unit = println(s"Hi, my name is $name, how can I help?")
    }
    val jim = new Person("Jim"){
        override def sayHi: Unit = println("Hello, My name is Jim, what can I help you with?")
    }

    println(jim.getClass) // prints: class lectures.part2oop.AnonymousClasses$$anon$2
    println(jim.sayHi) // prints: Hello, My name is Jim, what can I help you with?

}

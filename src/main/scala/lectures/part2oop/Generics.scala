package lectures.part2oop


/*
 * MyList[A] defines generic type
 * Covariance
 * Intravariance
 * Contravariance
 * Bounded types <: sub-type, >: super-type
 * Adding a list of dogs to a list of cats will turn the list to a list of animals
 */
object Generics extends App {

    class MyList[+A] {
        // use the type A
        def add[B >: A](element: B): MyList[B] = ???

        /*
            A = Cat
            B = Animal
         */
    }
    val listOfIntegers = new MyList[Int] // the Int overrides type A for this instance
    val listOfStrings = new MyList[String] // the String overrides type A for this instance

    class MyMap[Key, Value] // Same here applies as above

    trait MyMap2[Key, Value] // can also be done as a trait but cannot be instantiated

    // Generic methods
    object MyList {
        def empty[A]: MyList[A] = ???
    }

    val emptyListOfInts = MyList.empty[Int]

    // Variance problem
    class Animal
    class Cat extends Animal
    class Dog extends Animal


    // 1. Yes = COVARIANCE.  List[Cat] extends List[Animal]
    class CovarianceList[+A]
    var animal: Animal = new Cat
    var animalList: CovarianceList[Animal] = new CovarianceList[Cat]
    // animalList.add(new Dog) ??? Hard question


    // 2. No = INVARIANCE
    class InvariantList[A]
    val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]

    // 3. Hell no! = CONTRAVARIANCE
    class ContravariantList[-A]
    val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]

    class Trainer[-A]
    val trainer: Trainer[Cat] = new Trainer[Animal]


    // Bounded Types - allows you to bound types that are super type or sub type
    class Cage[A <: Animal](animal: A) // class cage only accepts subtypes of Animal
    val cage = new Cage(new Dog) // since dog is an animal which is a sub type, it conforms

}

package exercises

/*
 * Make a Covariant List Class
 *  - this is how you generalise an API or something like that
 *  - define parameter with square brackets
 *  - variance problems addressed (Covariance, Intravariance, Contravariance)
 *  - bounded types
 */

abstract class MyList2[+A] {
    def head: A
    def tail: MyList2[A]
    def isEmpty: Boolean
    def add[B >: A](element: B): MyList2[B]

    // Polymorphic call
    def printElements: String
    override def toString: String = "[" + printElements + "]"
}

object Empty2 extends MyList2[Nothing] {
    def head: Nothing = throw new NoSuchElementException
    def tail: MyList2[Nothing] = throw new NoSuchElementException
    def isEmpty: Boolean = true
    def add[B >: Nothing](element: B): MyList2[B] = new Cons2(element, Empty2)
    def printElements: String = ""
}

class Cons2[+A](h: A, t: MyList2[A]) extends MyList2[A] {
    def head: A = h
    def tail: MyList2[A] = t
    def isEmpty: Boolean = false
    def add[B >: A](element: B): MyList2[B] = new Cons2(element, this)

    // recursive call to print elements
    def printElements: String = {
        if (t.isEmpty) "" + h
        else h + " " + t.printElements
    }
}

object ListTest2 extends App {

    val listOfInts: MyList2[Int] = new Cons2(1, new Cons2(2, new Cons2(3, Empty2)))
    val listOfStr: MyList2[String] = new Cons2("List", new Cons2("of", new Cons2("Scala", Empty2)))

    println(listOfInts.toString)
    println(listOfStr.toString)
}
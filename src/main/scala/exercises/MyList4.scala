package exercises

/*
 * Make functions more FP and less OOP
 * Remove traits and use Function1[A,A] syntax
 */

abstract class MyList4[+A] {
    def head: A
    def tail: MyList4[A]
    def isEmpty: Boolean
    def add[B >: A](element: B): MyList4[B]

    // Polymorphic call
    def printElements: String
    override def toString: String = "[" + printElements + "]"

    def filter(predicate: A => Boolean): MyList4[A]
    def map[B] (transformer: A => B): MyList4[B] // need to have the - on transformer trait or wont work
    def flatMap[B](transformer: A => MyList4[B]): MyList4[B]

    def ++[B >: A](list: MyList4[B]): MyList4[B]

}

object Empty4 extends MyList4[Nothing] {
    def head: Nothing = throw new NoSuchElementException
    def tail: MyList4[Nothing] = throw new NoSuchElementException
    def isEmpty: Boolean = true
    def add[B >: Nothing](element: B): MyList4[B] = new Cons4(element, Empty4)
    def printElements: String = ""

    def map[B] (transformer: Nothing => B): MyList4[B] = Empty4
    def filter(predicate: Nothing => Boolean): MyList4[Nothing] = Empty4
    def flatMap[B](transformer: Nothing => MyList4[B]): MyList4[B] = Empty4

    def ++[B >: Nothing](list: MyList4[B]): MyList4[B] = list
}

class Cons4[+A](h: A, t: MyList4[A]) extends MyList4[A] {
    def head: A = h
    def tail: MyList4[A] = t
    def isEmpty: Boolean = false
    def add[B >: A](element: B): MyList4[B] = new Cons4(element, this)

    // recursive call to print elements
    def printElements: String = {
        if (t.isEmpty) "" + h
        else h + " " + t.printElements
    }

    /*
        [1,2,3].filter(n % 2 == 0) =
          [2,3].filter(n % 2 == 0)
        = new Cons(2, [3].filter(n % 2 == 0)
        = new Cons(2, Empty.filter(n % 2 == 0)
        = new Cons(2, Empty)
     */

    def filter(predicate: A => Boolean): MyList4[A] = {
        if (predicate(h)) new Cons4(h, t.filter(predicate))
        else t.filter(predicate)
    }


    /*
        [1,2,3].map(m * 2)
          = new Cons(2, [2,3].map(n * 2))
          = new Cons(2, new Cons(4, [3].map(n * 2)))
          = new Cons(2, new Cons(4, new Cons(6, Empty.map(n * 2))))
          = new Cons(2, new Cons(4, new Cons(6, Empty))))
     */
    def map[B] (transformer: A => B): MyList4[B] = {
        new Cons4(transformer(h), t.map(transformer))
    }

    /*  Concatenator
        [1,2 ++ [2,4,5]
        = new Cons(1, [2] ++ [3,4,5])
        = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
        = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5)))))
     */
    def ++[B >: A](list: MyList4[B]): MyList4[B] = new Cons4(h, t ++ list)


    /* flatMap
        [1,2].flatMap( n => [n, n+1])
        = [1,2] ++ [2].flatMap(n => [n, n+1])
        = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
        = [1,2] ++ [2,3] ++ Empty
        = [1,2,2,3]
     */
    def flatMap[B](transformer: A => MyList4[B]): MyList4[B] = {
        transformer(h) ++ t.flatMap(transformer)
    }
}

object ListTest4 extends App {

    val listOfInts: MyList4[Int] = new Cons4(1, new Cons4(2, new Cons4(3, Empty4)))
    val anotherListOfInts: MyList4[Int] = new Cons4(4, new Cons4(5, Empty4))
    val listOfStr: MyList4[String] = new Cons4("List", new Cons4("of", new Cons4("Scala", Empty4)))

    println(listOfInts.toString) // prints: [1 2 3]
    println(listOfStr.toString) // prints: [List of Scala]

    println(listOfInts.map(new Function1[Int, Int] {
        override def apply(elem: Int): Int = elem * 2
    }).toString) // prints: [2 4 6]

    println(listOfInts.filter(new Function1[Int, Boolean] {
        override def apply(elem: Int): Boolean = elem % 2 == 0
    }).toString) // prints: [2]

    println((listOfInts ++ anotherListOfInts).toString) // prints: [1 2 3 4 5]

    println(listOfInts.flatMap(new Function1[Int, MyList4[Int]] {
        override def apply(elem: Int): MyList4[Int] = new Cons4(elem, new Cons4(elem + 1, Empty4))
    }).toString) // prints: [1 2 2 3 3 4]
}
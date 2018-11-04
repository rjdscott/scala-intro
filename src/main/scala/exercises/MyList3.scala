package exercises

/*
 * 1. Generic Trait MyPredicate[-T]
 * 2. generic trait MyTransformer[-A, B]
 * 3. MyList:
 *    - map(transformer) => MyList
 *    - filter(transformer) => MyList
 *    - flatMap(Transformer from A to MyList[B]) => MyList[B]
 */

abstract class MyList3[+A] {
    def head: A
    def tail: MyList3[A]
    def isEmpty: Boolean
    def add[B >: A](element: B): MyList3[B]

    // Polymorphic call
    def printElements: String
    override def toString: String = "[" + printElements + "]"

    def filter(predicate: MyPredicate[A]): MyList3[A]
    def map[B] (transformer: MyTransformer[A, B]): MyList3[B] // need to have the - on transformer trait or wont work
    def flatMap[B](transformer: MyTransformer[A, MyList3[B]]): MyList3[B]

    def ++[B >: A](list: MyList3[B]): MyList3[B]

}

object Empty3 extends MyList3[Nothing] {
    def head: Nothing = throw new NoSuchElementException
    def tail: MyList3[Nothing] = throw new NoSuchElementException
    def isEmpty: Boolean = true
    def add[B >: Nothing](element: B): MyList3[B] = new Cons3(element, Empty3)
    def printElements: String = ""

    def map[B] (transformer: MyTransformer[Nothing, B]): MyList3[B] = Empty3
    def filter(predicate: MyPredicate[Nothing]): MyList3[Nothing] = Empty3
    def flatMap[B](transformer: MyTransformer[Nothing, MyList3[B]]): MyList3[B] = Empty3

    def ++[B >: Nothing](list: MyList3[B]): MyList3[B] = list
}

class Cons3[+A](h: A, t: MyList3[A]) extends MyList3[A] {
    def head: A = h
    def tail: MyList3[A] = t
    def isEmpty: Boolean = false
    def add[B >: A](element: B): MyList3[B] = new Cons3(element, this)

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

    def filter(predicate: MyPredicate[A]): MyList3[A] = {
        if (predicate.test(h)) new Cons3(h, t.filter(predicate))
        else t.filter(predicate)
    }


    /*
        [1,2,3].map(m * 2)
          = new Cons(2, [2,3].map(n * 2))
          = new Cons(2, new Cons(4, [3].map(n * 2)))
          = new Cons(2, new Cons(4, new Cons(6, Empty.map(n * 2))))
          = new Cons(2, new Cons(4, new Cons(6, Empty))))
     */
    def map[B] (transformer: MyTransformer[A, B]): MyList3[B] = {
        new Cons3(transformer.transform(h), t.map(transformer))
    }

    /*  Concatenator
        [1,2 ++ [2,4,5]
        = new Cons(1, [2] ++ [3,4,5])
        = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
        = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5)))))
     */
    def ++[B >: A](list: MyList3[B]): MyList3[B] = new Cons3(h, t ++ list)


    /* flatMap
        [1,2].flatMap( n => [n, n+1])
        = [1,2] ++ [2].flatMap(n => [n, n+1])
        = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
        = [1,2] ++ [2,3] ++ Empty
        = [1,2,2,3]
     */
    def flatMap[B](transformer: MyTransformer[A, MyList3[B]]): MyList3[B] = {
        transformer.transform(h) ++ t.flatMap(transformer)
    }
}

trait MyPredicate[-T] {
    def test(elem: T): Boolean
}

trait MyTransformer[-A, B] {
    def transform(elem: A): B
}

object ListTest3 extends App {

    val listOfInts: MyList3[Int] = new Cons3(1, new Cons3(2, new Cons3(3, Empty3)))
    val anotherListOfInts: MyList3[Int] = new Cons3(4, new Cons3(5, Empty3))
    val listOfStr: MyList3[String] = new Cons3("List", new Cons3("of", new Cons3("Scala", Empty3)))

    println(listOfInts.toString) // prints: [1 2 3]
    println(listOfStr.toString) // prints: [List of Scala]

    println(listOfInts.map(new MyTransformer[Int, Int] {
        override def transform(elem: Int): Int = elem * 2
    }).toString) // prints: [2 4 6]

    println(listOfInts.filter(new MyPredicate[Int] {
        override def test(elem: Int): Boolean = elem % 2 == 0
    }).toString) // prints: [2]

    println((listOfInts ++ anotherListOfInts).toString) // prints: [1 2 3 4 5]

    println(listOfInts.flatMap(new MyTransformer[Int, MyList3[Int]] {
        override def transform(elem: Int): MyList3[Int] = new Cons3(elem, new Cons3(elem + 1, Empty3))
    }).toString) // prints: [1 2 2 3 3 4]
}
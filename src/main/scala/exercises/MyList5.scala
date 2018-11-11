package exercises

/*
 * Make functions more FP and less OOP
 * Remove traits and use Function1[A,A] syntax
 */

abstract class MyList5[+A] {
    def head: A
    def tail: MyList5[A]
    def isEmpty: Boolean
    def add[B >: A](element: B): MyList5[B]

    // Polymorphic call
    def printElements: String
    override def toString: String = "[" + printElements + "]"

    def filter(predicate: A => Boolean): MyList5[A]
    def map[B] (transformer: A => B): MyList5[B] // need to have the - on transformer trait or wont work
    def flatMap[B](transformer: A => MyList5[B]): MyList5[B]

    def ++[B >: A](list: MyList5[B]): MyList5[B]

    // HOFs
    def foreach(f: A => Unit): Unit
    def sort(compare: (A,A) => Int): MyList5[A]
    def zipWith[B, C](list: MyList5[B], zip:(A, B) => C): MyList5[C]
    def fold[B](start: B)(operator: (B, A) => B): B

}

case object Empty5 extends MyList5[Nothing] {
    def head: Nothing = throw new NoSuchElementException
    def tail: MyList5[Nothing] = throw new NoSuchElementException
    def isEmpty: Boolean = true
    def add[B >: Nothing](element: B): MyList5[B] = new Cons5(element, Empty5)
    def printElements: String = ""

    def map[B] (transformer: Nothing => B): MyList5[B] = Empty5
    def filter(predicate: Nothing => Boolean): MyList5[Nothing] = Empty5
    def flatMap[B](transformer: Nothing => MyList5[B]): MyList5[B] = Empty5

    def ++[B >: Nothing](list: MyList5[B]): MyList5[B] = list

    // HOFs
    def foreach(f: Nothing => Unit): Unit = ()
    def sort(compare: (Nothing, Nothing) => Int) = Empty5
    def zipWith[B, C](list: MyList5[B], zip:(Nothing, B) => C): MyList5[C] = {
        if (!list.isEmpty) throw new RuntimeException("List does not have same length")
        else Empty5
    }
}

case class Cons5[+A](h: A, t: MyList5[A]) extends MyList5[A] {
    def head: A = h
    def tail: MyList5[A] = t
    def isEmpty: Boolean = false
    def add[B >: A](element: B): MyList5[B] = new Cons5(element, this)

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

    def filter(predicate: A => Boolean): MyList5[A] = {
        if (predicate(h)) new Cons5(h, t.filter(predicate))
        else t.filter(predicate)
    }


    /*
        [1,2,3].map(m * 2)
          = new Cons(2, [2,3].map(n * 2))
          = new Cons(2, new Cons(4, [3].map(n * 2)))
          = new Cons(2, new Cons(4, new Cons(6, Empty.map(n * 2))))
          = new Cons(2, new Cons(4, new Cons(6, Empty))))
     */
    def map[B] (transformer: A => B): MyList5[B] = {
        new Cons5(transformer(h), t.map(transformer))
    }

    /*  Concatenator
        [1,2 ++ [2,4,5]
        = new Cons(1, [2] ++ [3,4,5])
        = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
        = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5)))))
     */
    def ++[B >: A](list: MyList5[B]): MyList5[B] = new Cons5(h, t ++ list)


    /* flatMap
        [1,2].flatMap( n => [n, n+1])
        = [1,2] ++ [2].flatMap(n => [n, n+1])
        = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
        = [1,2] ++ [2,3] ++ Empty
        = [1,2,2,3]
     */
    def flatMap[B](transformer: A => MyList5[B]): MyList5[B] = {
        transformer(h) ++ t.flatMap(transformer)
    }

    def foreach(f: A => Unit): Unit = {
        f(h)
        t.foreach(f)
    }

    def sort(compare: (A, A) => Int): MyList5[A] = {
        // insertion sprt
        def insert(x: A, sortedList: MyList5[A]): MyList5[A] = {
            if (sortedList.isEmpty) new Cons5(x, Empty5)
            else if (compare(x, sortedList.head) <= 0) new Cons5(x, sortedList)
            else new Cons5(sortedList.head, insert(x, sortedList.tail))
        }
        val sortedTail = t.sort(compare)
        insert(h, sortedTail)
    }

    def zipWith[B, C](list: MyList5[B], zip:(A, B) => C): MyList5[C] = {
        if (list.isEmpty) throw new RuntimeException("Lists do not have same length")
        else new Cons5(zip(h, list.head), t.zipWith(list.tail, zip))
    }
}

object ListTest5 extends App {

    val listOfInts: MyList5[Int] = new Cons5(1, new Cons5(2, new Cons5(3, Empty5)))
    val anotherListOfInts: MyList5[Int] = new Cons5(4, new Cons5(5, Empty5))
    val listOfStr: MyList5[String] = new Cons5("List", new Cons5("of", new Cons5("Scala", Empty5)))

    //println(listOfInts.toString) // prints: [1 2 3]
    //println(listOfStr.toString) // prints: [List of Scala]

    //println(listOfInts.map(elem => elem * 2).toString) // prints: [2 4 6]
    //println(listOfInts.map(_ * 2).toString)

    //println(listOfInts.filter(elem => elem % 2 == 0).toString) // prints: [2]
    //println(listOfInts.filter(_ % 2 == 0).toString) // prints: [2]

    //println((listOfInts ++ anotherListOfInts).toString) // prints: [1 2 3 4 5]

    //println(listOfInts.flatMap(elem => new Cons5(elem, new Cons5(elem + 1, Empty5))).toString) // prints: [1 2 2 3 3 4]

    // HOFs
    listOfInts.foreach(println)
    println(listOfInts.sort((x, y) => y - x))
    println(listOfInts.zipWith[String, String](listOfStr, _ + "-" + _))
}
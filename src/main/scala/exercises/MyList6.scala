package exercises

/*
 * Make functions more FP and less OOP
 * Remove traits and use Function1[A,A] syntax
 */

abstract class MyList6[+A] {
    def head: A
    def tail: MyList6[A]
    def isEmpty: Boolean
    def add[B >: A](element: B): MyList6[B]

    // Polymorphic call
    def printElements: String
    override def toString: String = "[" + printElements + "]"

    def filter(predicate: A => Boolean): MyList6[A]
    def map[B] (transformer: A => B): MyList6[B] // need to have the - on transformer trait or wont work
    def flatMap[B](transformer: A => MyList6[B]): MyList6[B]

    def ++[B >: A](list: MyList6[B]): MyList6[B]

    // HOFs
    def foreach(f: A => Unit): Unit
    def sort(compare: (A,A) => Int): MyList6[A]
    def zipWith[B, C](list: MyList6[B], zip:(A, B) => C): MyList6[C]
    def fold[B](start: B)(operator: (B, A) => B): B

}

case object Empty6 extends MyList6[Nothing] {
    def head: Nothing = throw new NoSuchElementException
    def tail: MyList6[Nothing] = throw new NoSuchElementException
    def isEmpty: Boolean = true
    def add[B >: Nothing](element: B): MyList6[B] = new Cons6(element, Empty6)
    def printElements: String = ""

    def map[B] (transformer: Nothing => B): MyList6[B] = Empty6
    def filter(predicate: Nothing => Boolean): MyList6[Nothing] = Empty6
    def flatMap[B](transformer: Nothing => MyList6[B]): MyList6[B] = Empty6

    def ++[B >: Nothing](list: MyList6[B]): MyList6[B] = list

    // HOFs
    def foreach(f: Nothing => Unit): Unit = ()
    def sort(compare: (Nothing, Nothing) => Int) = Empty6
    def zipWith[B, C](list: MyList6[B], zip:(Nothing, B) => C): MyList6[C] = {
        if (!list.isEmpty) throw new RuntimeException("List does not have same length")
        else Empty6
    }
    def fold[B](start: B)(operator: (B, Nothing) => B): B = start
}

case class Cons6[+A](h: A, t: MyList6[A]) extends MyList6[A] {
    def head: A = h
    def tail: MyList6[A] = t
    def isEmpty: Boolean = false
    def add[B >: A](element: B): MyList6[B] = new Cons6(element, this)

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

    def filter(predicate: A => Boolean): MyList6[A] = {
        if (predicate(h)) new Cons6(h, t.filter(predicate))
        else t.filter(predicate)
    }


    /*
        [1,2,3].map(m * 2)
          = new Cons(2, [2,3].map(n * 2))
          = new Cons(2, new Cons(4, [3].map(n * 2)))
          = new Cons(2, new Cons(4, new Cons(6, Empty.map(n * 2))))
          = new Cons(2, new Cons(4, new Cons(6, Empty))))
     */
    def map[B] (transformer: A => B): MyList6[B] = {
        new Cons6(transformer(h), t.map(transformer))
    }

    /*  Concatenator
        [1,2 ++ [2,4,5]
        = new Cons(1, [2] ++ [3,4,5])
        = new Cons(1, new Cons(2, Empty ++ [3,4,5]))
        = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5)))))
     */
    def ++[B >: A](list: MyList6[B]): MyList6[B] = new Cons6(h, t ++ list)


    /* flatMap
        [1,2].flatMap( n => [n, n+1])
        = [1,2] ++ [2].flatMap(n => [n, n+1])
        = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1])
        = [1,2] ++ [2,3] ++ Empty
        = [1,2,2,3]
     */
    def flatMap[B](transformer: A => MyList6[B]): MyList6[B] = {
        transformer(h) ++ t.flatMap(transformer)
    }

    def foreach(f: A => Unit): Unit = {
        f(h)
        t.foreach(f)
    }

    def sort(compare: (A, A) => Int): MyList6[A] = {
        // insertion sprt
        def insert(x: A, sortedList: MyList6[A]): MyList6[A] = {
            if (sortedList.isEmpty) new Cons6(x, Empty6)
            else if (compare(x, sortedList.head) <= 0) new Cons6(x, sortedList)
            else new Cons6(sortedList.head, insert(x, sortedList.tail))
        }
        val sortedTail = t.sort(compare)
        insert(h, sortedTail)
    }

    def zipWith[B, C](list: MyList6[B], zip:(A, B) => C): MyList6[C] = {
        if (list.isEmpty) throw new RuntimeException("Lists do not have same length")
        else new Cons6(zip(h, list.head), t.zipWith(list.tail, zip))
    }

    /*  Lets take a look at how the fold function works
        [1,2,3].fold(0)(+)
        = [2,3].fold(1)(+)
        =   [3].fold(3)(+)
        =   [1].fold(6)(+)
        =    6
     */
    def fold[B](start: B)(operator: (B, A) => B): B = {
        t.fold(operator(start, h))(operator)
    }
}

object ListTest6 extends App {

    val listOfInts: MyList6[Int] = new Cons6(1, new Cons6(2, new Cons6(3, Empty6)))
    val anotherListOfInts: MyList6[Int] = new Cons6(4, new Cons6(5, Empty6))
    val listOfStr: MyList6[String] = new Cons6("List", new Cons6("of", new Cons6("Scala", Empty6)))

    //println(listOfInts.toString) // prints: [1 2 3]
    //println(listOfStr.toString) // prints: [List of Scala]

    //println(listOfInts.map(elem => elem * 2).toString) // prints: [2 4 6]
    //println(listOfInts.map(_ * 2).toString)

    //println(listOfInts.filter(elem => elem % 2 == 0).toString) // prints: [2]
    //println(listOfInts.filter(_ % 2 == 0).toString) // prints: [2]

    //println((listOfInts ++ anotherListOfInts).toString) // prints: [1 2 3 4 5]

    //println(listOfInts.flatMap(elem => new Cons6(elem, new Cons6(elem + 1, Empty6))).toString) // prints: [1 2 2 3 3 4]

    // HOFs
    listOfInts.foreach(println)
    println(listOfInts.sort((x, y) => y - x))
    println(listOfInts.zipWith[String, String](listOfStr, _ + "-" + _))
    println(listOfInts.fold(0)(_ + _))

    // FOR COMPREHENSIONS

    val combinations = for {
        n <- listOfInts
        s <- listOfStr
    } yield n + "-" + s

    println(combinations)
}

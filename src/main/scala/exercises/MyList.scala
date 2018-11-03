package exercises

/*
  Single linked list with following methods
    - head = first element of list
    - tail = remainder of list
    - isEmpty = is list empty
    - add(int) => receives list and adds integer to it
    - toString = a string representation of the list
 */

abstract class MyList {
    def head: Int
    def tail: MyList
    def isEmpty: Boolean
    def add(element: Int): MyList

    // Polymorphic call
    def printElements: String
    override def toString: String = "[" + printElements + "]"
}

object Empty extends MyList {
    def head: Int = throw new NoSuchElementException
    def tail: MyList = throw new NoSuchElementException
    def isEmpty: Boolean = true
    def add(element: Int): MyList = new Cons(element, Empty)
    def printElements: String = ""
}

class Cons(h: Int, t: MyList) extends MyList {
    def head: Int = h
    def tail: MyList = t
    def isEmpty: Boolean = false
    def add(element: Int): MyList = new Cons(element, this)

    // recursive call to print elements
    def printElements: String = {
        if (t.isEmpty) "" + h
        else h + " " + t.printElements
    }
}

object ListTest extends App {
    val list = new Cons(1, Empty)
    println(list.head) // should print 1

    val list2 = new Cons(1, new Cons(2, new Cons(3, Empty)))
    println(list2.tail.head) // should print 2
    println(list2.add(4).head) // should print 4
    println(list2.toString) // should print [1 2 3]

}
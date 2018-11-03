package lectures.part2oop

object OOBasics extends App {
    val person = new Person("John", 25)
    println(person.x)
    person.greet("Daniel")

    val author = new Writer("Charles", "Dickens", 1812)
    val novel = new Novel("Great Expectations", 1861, author)

    println(novel.authorAge)
    println(novel.isWrittenBy(author))

    val counter = new Counter

    counter.inc.inc.inc.print

}

// constructor
class Person(val name: String, val age: Int) {
    // body - defines implementation of the class
    // vals, vars (Are fields and can be called)
    // defs
    // expressions
    // value of code block is ignored

    val x = 4


    // methods - use "this." to use class variables
    def greet(name: String): Unit = println(s"${this.name} says hi, $name")

    // overloading
    def greet(): Unit = println(s"Helli I am ${this.name}")

    // multiple constructors - overloading constructors
    // we actuallt dont need this since it is the same  as implementing default values in constructor!
    def this(name: String) = this(name, 0)

    def this() = this("John Doe")

}

class Writer(firstName: String, surname: String, val year: Int) {
    def fullName: String = firstName + " " + surname
}

class Novel(name: String, year: Int, author: Writer) {
    def authorAge: Int = year - author.year

    def isWrittenBy(author: Writer): Boolean = author == this.author

    def copy(newYear: Int): Novel = new Novel(name, newYear, author)

}


class Counter(val count: Int = 0) {

    // this is important to return a new instance of counter and not mutate
    def inc = {
        println("Incrementing")
        new Counter(count + 1)
    }

    def dec = {
        println("Decrementing")
        new Counter(count - 1)
    }

    def inc(n: Int): Counter = {
        if (n <= 0) this
        else inc.inc(n - 1)
    }

    def dec(n: Int): Counter = {
        if (n <= 0) this
        else inc.inc(n - 1)
    }

    def print: Unit = println(count)
}
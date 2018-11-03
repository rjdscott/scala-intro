package lectures.part1basics

object StringOps extends App {

    var str: String = "Hello I am learning Scala!"

    // these are all java utilities
    println(str.charAt(2))
    println(str.substring(7, 10))
    println(str.split(" ").toList)
    println(str.startsWith("Hello"))
    println(str.replace(" ", "_"))
    println(str.toLowerCase)
    println(str.toUpperCase)
    println(str.length)

    val aNumberString = "45"
    val aNumber = aNumberString.toInt

    println('a' +: aNumberString :+ 'z')
    println(str.reverse)
    println(str.take(3))

    // scala specific string interpolator
    // S-Interpolators
    val name = "David"
    val age = 12
    val greeting = s"Hello, my name is $name and I am $age years old"
    val greeting2 = s"Hello, my name is $name and I am ${age + 1} years old"

    println(greeting)
    println(greeting2)

    // F-Interpolators
    val speed = 1.2f
    val myth = f"$name can eat $speed%2.2f burgers per minute"
    println(myth)

    // raw-Interpolators
    println(raw"This is a \n new line")

    val escaped = "This is a \n new line"
    println(raw"$escaped")
}

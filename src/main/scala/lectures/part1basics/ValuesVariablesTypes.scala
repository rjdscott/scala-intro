package lectures.part1basics

object ValuesVariablesTypes extends App {

    // Values
    // immutable, not exposed to side-effects
    // the compiler can infer types
    // you can terminate a line with a semi-colon

    val x: Int = 42
    println(x)

    val aString: String = "Hello this is a string"
    println(aString)

    val aBool: Boolean = false
    val aChar: Char = 'a'
    val anInt: Int = x
    val aFloat: Float = 2.4f
    val aDouble: Double = 3.5

    // Variables
    // mutable and are exposed to side effects

    var aVariable: Int = 4
    println(aVariable)

    aVariable = 20
    println(aVariable)

}

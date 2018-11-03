package lectures.part1basics

object Expressions extends App {

    val x = 1 + 2 // EXPRESSION
    println(x)

    println(3 + 5 * 5)
    // + - * / & | << >> >>> (right shift with zero extension

    println(1 == 4)
    // == != > >= < <=

    println(!(1 == x))
    // ! && ||

    var aVariable = 3
    aVariable += 10
    println(aVariable)
    // += -= *= /=


    // Instructions (DO) vs Expressions (VALUE)

    // IF Expression
    val aCondition = true
    val aConditionValue = if(aCondition) 5 else 3
    println(aConditionValue)


    // Looping - DO NOT LOOP!!!!!
    var i = 0
    while (i < 10) {
        println(i)
        i += 2
    }

    // never loop again, Scala is an expression!

    val aWeirdValue = (aVariable = 3)
    println(aWeirdValue)

    // side-effects: println(), while, reassigning

    // Code blocks
    val aCodeBlock = {
        val y = 2
        val z = y + 1

        if (z > 2) "hello" else "goodbye"
    }

}

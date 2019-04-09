package util

object Printer {

    fun printJavaInfo(info: Any?) {
        if (info != null) {
            println(info.toString())
        } else {
            println("Null")
        }
    }
}

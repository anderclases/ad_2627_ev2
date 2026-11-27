package com.example.kmp_inventory

import java.io.PrintStream

actual object MyLog {
    private const val PREFIX = "[MyApp] "

    // Compatible con cualquier versión de Java/JVM
    private val stdout = PrintStream(System.out, true, "UTF-8")
    private val stderr = PrintStream(System.err, true, "UTF-8")

    private fun caller(): String {
        val trace = Throwable().stackTrace
        val frame = trace.firstOrNull { element ->
            !element.className.contains("MyLog") &&
                    !element.className.startsWith("kotlin.") &&
                    !element.className.startsWith("java.")
        }
        val file = frame?.fileName ?: "Unknown.kt"
        val line = frame?.lineNumber ?: -1
        return "($file:$line)"
    }

    actual fun d(message: String) {
        stdout.println("DEBUG / ${caller()}: $PREFIX$message")
    }

    actual fun e(message: String, throwable: Throwable?) {
        stderr.println("ERROR / ${caller()}: $PREFIX$message")
        throwable?.printStackTrace(stderr)
    }
}
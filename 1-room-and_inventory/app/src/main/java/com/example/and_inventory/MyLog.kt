package com.example.and_inventory

import android.util.Log

object MyLog {
    private const val PREFIX = "[MyApp] "

    private fun caller(): String {
        val trace = Throwable().stackTrace
        val frame = trace.firstOrNull { element ->
            element.className.startsWith("com.example.libreria") &&
                    !element.className.contains("MyLog")
        }
        val className = frame?.className?.substringAfterLast('.')?.substringBefore('$')
            ?: "UnknownClass"
        val file = frame?.fileName ?: "Unknown.kt"
        val line = frame?.lineNumber ?: -1
        return "($file:$line)"
    }
    fun d(message: String) {
        Log.d(caller(), PREFIX + message)
    }
    fun e(message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            // Log.e acepta un Throwable como tercer parámetro
            Log.e(caller(), PREFIX + message, throwable)
        } else {
            Log.e(caller(), PREFIX + message)
        }
    }
}

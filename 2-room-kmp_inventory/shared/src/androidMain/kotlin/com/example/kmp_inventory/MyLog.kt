package com.example.kmp_inventory

import android.util.Log

actual object MyLog {
    private const val TAG = "MyApp"

    private fun caller(): String {
        val trace = Throwable().stackTrace
        val frame = trace.firstOrNull { element ->
            val name = element.className
            !name.contains("MyLog") &&
                    !name.startsWith("java.") &&
                    !name.startsWith("javax.") &&
                    !name.startsWith("kotlin.") &&
                    !name.startsWith("kotlinx.coroutines.")
        }
        val file = frame?.fileName ?: "Unknown.kt"
        val line = frame?.lineNumber ?: -1
        return "($file:$line)"
    }

    actual fun d(message: String) {
        Log.d(TAG, "${caller()} $message")
    }

    actual fun e(message: String, throwable: Throwable?) {
        if (throwable != null) {
            Log.e(TAG, "${caller()} $message", throwable)
        } else {
            Log.e(TAG, "${caller()} $message")
        }
    }
}
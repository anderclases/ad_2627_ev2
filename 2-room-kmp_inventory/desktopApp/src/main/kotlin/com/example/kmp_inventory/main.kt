package com.example.kmp_inventory

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Kmp_inventory",
    ) {
        App()
    }
}
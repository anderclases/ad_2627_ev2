package com.example.kmp_inventory

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package com.example.kmp_inventory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.kmp_inventory.data.InventoryDatabase
import java.io.File

@Composable
actual fun rememberAppContainer(): AppContainer {
    return remember {
        val builder = getDatabaseBuilder()
        DefaultAppContainer(builder)
    }
}

// C:\Users\<TuUsuario>\AppData\Local\Temp\inventory.db
fun getDatabaseBuilder(): RoomDatabase.Builder<InventoryDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "inventory.db")
    return Room.databaseBuilder<InventoryDatabase>(
        name = dbFile.absolutePath
    )
}
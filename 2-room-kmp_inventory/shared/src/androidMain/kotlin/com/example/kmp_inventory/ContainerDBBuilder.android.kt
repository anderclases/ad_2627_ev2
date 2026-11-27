package com.example.kmp_inventory

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.example.kmp_inventory.data.InventoryDatabase

@Composable
actual fun rememberAppContainer(): AppContainer {
    val context = LocalContext.current.applicationContext

    // remember garantiza que la base de datos se cree una sola vez
    return remember(context) {
        val builder = getDatabaseBuilder(context)
        DefaultAppContainer(builder)
    }
}

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<InventoryDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("my_room_db.db")
    return Room.databaseBuilder<InventoryDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
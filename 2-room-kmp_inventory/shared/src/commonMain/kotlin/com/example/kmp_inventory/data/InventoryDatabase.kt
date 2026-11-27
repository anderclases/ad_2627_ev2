package com.example.kmp_inventory.data

import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<InventoryDatabase>
): InventoryDatabase {
    return builder
        .setDriver(BundledSQLiteDriver()) // Motor SQLite multiplataforma
        .setQueryCoroutineContext(Dispatchers.IO)
        .fallbackToDestructiveMigration(dropAllTables = true) // Para sobrescribir al construir
        .build()
}
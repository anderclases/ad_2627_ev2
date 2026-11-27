package com.example.and_inventory.data

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            //context.deleteDatabase("item_database")
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {

                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
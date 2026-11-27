package com.example.kmp_inventory

import androidx.compose.runtime.Composable
import androidx.room3.RoomDatabase
import com.example.kmp_inventory.data.InventoryDatabase
import com.example.kmp_inventory.data.ItemsRepository
import com.example.kmp_inventory.data.LocalItemsRepository
import com.example.kmp_inventory.data.getRoomDatabase


interface AppContainer {
    val itemsRepository: ItemsRepository
}

class DefaultAppContainer(
    databaseBuilder: RoomDatabase.Builder<InventoryDatabase>
) : AppContainer {

    private val database: InventoryDatabase by lazy {
        getRoomDatabase(databaseBuilder)
    }

    // El DAO se mantiene privado internamente dentro del contenedor
    override val itemsRepository: ItemsRepository by lazy {
        LocalItemsRepository(database.itemDao())
    }
}

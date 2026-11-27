package com.example.kmp_inventory.data

import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getAllItemsStream(): Flow<List<Item>>
    suspend fun insertItem(item: Item)
}

class LocalItemsRepository(
    private val itemDao: ItemDao
) : ItemsRepository {
    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()
    override suspend fun insertItem(item: Item) = itemDao.insert(item)
}
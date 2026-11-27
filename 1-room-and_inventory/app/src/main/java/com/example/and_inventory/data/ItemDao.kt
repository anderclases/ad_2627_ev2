package com.example.and_inventory.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the Inventory database
 */
@Dao
interface ItemDao {

    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Query("""
        SELECT * FROM items
        WHERE (:nombreExacto IS NULL OR LOWER(TRIM(name)) = LOWER(TRIM(:nombreExacto)))
          AND (:precioMin IS NULL OR price >= :precioMin)
          AND (:precioMax IS NULL OR price <= :precioMax)
          AND (:cantidadMin IS NULL OR quantity >= :cantidadMin)
          AND (:cantidadMax IS NULL OR quantity <= :cantidadMax)
        ORDER BY name ASC
    """)
    fun searchItems(
        nombreExacto: String?,
        precioMin: Double?,
        precioMax: Double?,
        cantidadMin: Int?,
        cantidadMax: Int?
    ): Flow<List<Item>>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)
}
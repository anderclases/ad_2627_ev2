package com.example.roomelementos.data


import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query

/**
 * Defin consultas y operaciones CRUD
 * Interfaz DAO (Data Access Object) para la entidad [UsuarioEntity].
 *
 * Proporciona los métodos necesarios para realizar operaciones de acceso a datos
 * en la base de datos local relacionada con los usuarios, como insertar, consultar,
 * actualizar o eliminar registros.
 *
 * Esta interfaz será implementada automáticamente por Room si se usan las anotaciones
 * correspondientes como @Dao, @Insert, @Query, @Update, @Delete.
 */
@Dao
interface UsuarioDao {

    //Inserta un Usuario que en bas ede datos se refleja cómo UsuarioEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: UsuarioEntity)

    //Obtiene una lista de ususarios
    @Query("SELECT * FROM usuarios")
    suspend fun getAll(): List<UsuarioEntity>

    //Realiza una búsqueda filtrando por id
    @Query("SELECT * FROM usuarios WHERE id = :id OR nombreUsr LIKE :nombre")
    suspend fun findByIdOrName(id: Int?, nombre: String): List<UsuarioEntity>

    @Query("DELETE FROM usuarios WHERE id = :id")
    suspend fun eliminarUsuario(id: String)
}
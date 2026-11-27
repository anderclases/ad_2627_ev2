package com.example.roomelementos.data

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

/**
 * Define una base de datos de Room con la entidad UsuarioEntity
 * Proporciona un DAO de acceso a la DB UsuarioDao
 * Implementa el patron de diseño singleton,
 * - para asegurar que la base de datos tiene una sola instancia
 * - Se puede acceder a la BD desde cualquier app
 */
@Database(entities = [UsuarioEntity::class], version = 1, exportSchema = true)
abstract class UsuarioDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: UsuarioDatabase? = null

        fun getDatabase(context: Context): UsuarioDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsuarioDatabase::class.java,
                    "usuarios_db"
                )
                    .fallbackToDestructiveMigration()  // <- Esto elimina la base de datos antigua, si haces cambios frecuentes en las entidades
                    .build()
                // aunque esto resete la construccion de la base de datos, la app mantiene la base de datos
                // es decir el error es causado por los archivos que guarda el emulador.
                // Solucion: Desinstalar la app o wipe data del emulador
                INSTANCE = instance
                instance
            }
        }
    }
}
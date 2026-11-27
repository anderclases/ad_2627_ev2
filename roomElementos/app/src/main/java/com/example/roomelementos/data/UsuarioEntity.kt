package com.example.roomelementos.data

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreUsr: String,
    val telefono: String,
    val descripcion: String
)
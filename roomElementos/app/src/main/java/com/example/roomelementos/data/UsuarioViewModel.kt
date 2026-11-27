package com.example.roomelementos.data

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class UsuarioData(
    val nombreUsr: String,
    val telefono: String,
    val descripcion: String
)

/**
 * ViewModel responsable de gestionar la lógica de presentación relacionada con los usuarios.
 * Lo usan las ventanas para poder realizar las consultas, este se comunica con el DAO
 *
 * Esta clase actúa como intermediario entre la capa de datos (accedida a través de [UsuarioDao])
 * y la interfaz de usuario (Activities o Fragments). Su función principal es mantener y manejar
 * los datos del usuario de forma que sobrevivan a cambios de configuración (como rotaciones de pantalla).
 *
 * @property usuarioDao Objeto [UsuarioDao] que provee los métodos de acceso a la base de datos local.
 * @property application Instancia de [Application] usada para obtener el contexto de la aplicación.
 *
 * @constructor Crea una instancia de [UsuarioViewModel] asociada al ciclo de vida del componente que la usa.
 */
class UsuarioViewModel(private val usuarioDao: UsuarioDao, application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    fun crearUsuario(usuario: UsuarioData) {
        viewModelScope.launch {
            try {
                usuarioDao.insert(
                    UsuarioEntity(
                        nombreUsr = usuario.nombreUsr,
                        telefono = usuario.telefono,
                        descripcion = usuario.descripcion
                    )
                )
                Toast.makeText(context, "Usuario creado correctamente", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun eliminarUsuario(id : String) {
        viewModelScope.launch {
            try {
                usuarioDao.eliminarUsuario(id)
                Toast.makeText(context, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Estado observable de la UI
    private val _usuarios = mutableStateListOf<UsuarioEntity>()
    val usuarios: List<UsuarioEntity> = _usuarios

    fun verTodos() {
        viewModelScope.launch {
            val lista = usuarioDao.getAll()
            _usuarios.clear()
            _usuarios.addAll(lista)
        }
    }
}
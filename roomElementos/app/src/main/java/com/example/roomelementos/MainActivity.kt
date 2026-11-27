package com.example.roomelementos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.roomelementos.data.UsuarioDatabase
import com.example.roomelementos.data.UsuarioViewModel
import com.example.roomelementos.data.ventanaPrincipal


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = UsuarioDatabase.getDatabase(this)
        val usuarioDao = db.usuarioDao()
        val viewModel = UsuarioViewModel(usuarioDao, application)

        setContent {
            ventanaPrincipal(viewModel)
        }
    }
}

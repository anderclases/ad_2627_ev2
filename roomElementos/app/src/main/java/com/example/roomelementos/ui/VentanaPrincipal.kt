package com.example.roomelementos.data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete

@Composable
fun ventanaPrincipal(usrViewModel: UsuarioViewModel) {
    val id = remember { mutableStateOf("") }
    val nombre = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val descripcion = remember { mutableStateOf("") }
    val usuarios = usrViewModel.usuarios

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = id.value, onValueChange = { id.value = it }, label = { Text("id") })
        OutlinedTextField(value = nombre.value, onValueChange = { nombre.value = it }, label = { Text("Nombre") })
        OutlinedTextField(value = telefono.value, onValueChange = { telefono.value = it }, label = { Text("Teléfono") })
        OutlinedTextField(value = descripcion.value, onValueChange = { descripcion.value = it }, label = { Text("Descripción") })

        Button(onClick = {
            // Aqui quiero llamar a crearUsuario
            val nuevoUsuario = UsuarioData(
                nombreUsr = nombre.value,
                telefono = telefono.value,
                descripcion = descripcion.value
            )
            usrViewModel.crearUsuario(nuevoUsuario)
        }) { Text("Crear usuario") }

        Button(onClick = {
            usrViewModel.verTodos()
        }) { Text("Ver todos") }
        ventanaUsuarios(usuarios,usrViewModel)
    }

}


@Composable
fun ventanaUsuarios(usuarios: List<UsuarioEntity>,usrViewModel: UsuarioViewModel) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .verticalScroll(scrollState)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (usuarios.isEmpty()) {
            Text("No se ha cargado ningún usuario")
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(10.dp)
            ) {
                Text(text = "ID",modifier = Modifier.weight(1f,),textAlign = TextAlign.Center,color = Color.White)
                Text(text = "Nombre", modifier = Modifier.weight(3f),textAlign = TextAlign.Center,color = Color.White)
                Text(text = "Acción", modifier = Modifier.weight(1f),textAlign = TextAlign.Center,color = Color.White)
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth(),thickness = 1.dp,color = Color.Gray)

            usuarios.forEach { usuario ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween // separa las columnas
                ) {
                    Text(
                        text = usuario.id.toString(),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = usuario.nombreUsr,
                        modifier = Modifier.weight(3f),
                        textAlign = TextAlign.Center
                    )
                    // INI Boton editar
                    IconButton(
                        onClick = { },
                        modifier = Modifier//.background(Color.Blue, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Eliminar usuario",
                            tint = Color.Black,
                            modifier = Modifier.size(25.dp)                        )
                    }
                    // FIN Boton editar
                    // INI Boton eliminar
                    IconButton(
                        onClick = {
                            usrViewModel.eliminarUsuario(usuario.id.toString())
                                  },
                        modifier = Modifier//.background(Color.Red, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar usuario",
                            tint = Color.Black,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                    // FIN Boton eliminar
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth(),thickness = 1.dp,color = Color.Gray)
            }
        }
    }
}



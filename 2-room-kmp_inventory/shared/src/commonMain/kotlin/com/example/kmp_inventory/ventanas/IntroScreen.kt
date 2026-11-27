package com.example.kmp_inventory.ventanas


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.kmp_inventory.LocalAppContainer
import com.example.kmp_inventory.MyLog
import com.example.kmp_inventory.data.InventoryViewModel
import com.example.kmp_inventory.data.rememberInventoryViewModel

class IntroScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val appContainer = LocalAppContainer.current

        val inventoryViewModel = rememberInventoryViewModel()

        var textNombreProducto by remember { mutableStateOf("") }
        var textPrecio by remember { mutableStateOf("") }
        var textCantidad by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Text("Aquí vamos a meter datos")

            OutlinedTextField(
                value = textNombreProducto,
                onValueChange = {
                    textNombreProducto = it
                    errorMessage = null
                },
                label = { Text("Nombre producto") },
            )

            OutlinedTextField(
                value = textPrecio,
                onValueChange = {
                    textPrecio = it
                    errorMessage = null
                },
                label = { Text("Precio") },
            )

            OutlinedTextField(
                value = textCantidad,
                onValueChange = {
                    textCantidad = it
                    errorMessage = null
                },
                label = { Text("Cantidad") },
            )

            errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    MyLog.d("Boton intro datos pulsado")

                    if (textNombreProducto.isBlank()) {
                        errorMessage = "Introduce un nombre válido"
                        return@Button
                    }

                    val dbprice = textPrecio.toDoubleOrNull()
                    if (dbprice == null) {
                        errorMessage = "El precio debe ser un número válido"
                        return@Button
                    }

                    val dbQuantity = textCantidad.toIntOrNull()
                    if (dbQuantity == null) {
                        errorMessage = "La cantidad debe ser un número entero"
                        return@Button
                    }

                    inventoryViewModel.insertItem(
                        name = textNombreProducto,
                        price = dbprice,
                        quantity = dbQuantity
                    )

                    textNombreProducto = ""
                    textPrecio = ""
                    textCantidad = ""
                    errorMessage = null
                }
            ) {
                Text("Intro datos primera prueba")
            }

            Button(
                onClick = {
                    MyLog.d("VentanaIntro.navigate.inicio")
                    navigator.pop() // Regresa a la pantalla anterior en la pila
                }
            ) {
                Text("Inicio")
            }
        }
    }
}
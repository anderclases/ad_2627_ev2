package com.example.and_inventory.ventanas

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.and_inventory.MyLog
import com.example.and_inventory.data.InventoryViewModel

@Composable
fun VentanaIntro(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: InventoryViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {

        Text("Aquí vamos a meter datos")

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.nombreProducto,
            onValueChange = { viewModel.onNombreChange(it) },
            label = { Text("Nombre producto") },
            isError = viewModel.mensajeError != null && viewModel.nombreProducto.isBlank(),
            singleLine = true
        )

        OutlinedTextField(
            value = viewModel.precioProducto,
            onValueChange = { viewModel.onPrecioChange(it) },
            label = { Text("Precio") },
            isError = viewModel.mensajeError != null && viewModel.precioProducto.toDoubleOrNull() == null,
            singleLine = true
        )

        OutlinedTextField(
            value = viewModel.cantidadProducto,
            onValueChange = { viewModel.onCantidadChange(it) },
            label = { Text("Cantidad") },
            isError = viewModel.mensajeError != null && viewModel.cantidadProducto.toIntOrNull() == null,
            singleLine = true
        )

        // Mensaje de error en pantalla si existe
        viewModel.mensajeError?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.validarYGuardarItem() }
        ) {
            Text("Intro datos primera prueba")
        }

        Button(
            onClick = {
                MyLog.d("VentanaIntro.navigate.inicio")
                navController.navigate("inicio")
            }
        ) {
            Text("Inicio")
        }
    }
}
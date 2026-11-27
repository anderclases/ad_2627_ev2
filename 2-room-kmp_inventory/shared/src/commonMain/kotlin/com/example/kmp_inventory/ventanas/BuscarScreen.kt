package com.example.kmp_inventory.ventanas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

class BuscarScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val appContainer = LocalAppContainer.current

        val inventoryViewModel = rememberInventoryViewModel()

        val items by inventoryViewModel.allItems.collectAsState(initial = emptyList())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Text("Esta es la ventana de búsquedas")

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                items.forEach { item ->
                    Text(text = "${item.name} - ${item.price} - ${item.quantity}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    MyLog.d("VentanaBuscar.navigate.inicio")
                    navigator.pop() // Regresa a la pantalla anterior en la pila
                }
            ) {
                Text("Inicio")
            }
        }
    }
}
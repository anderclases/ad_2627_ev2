package com.example.and_inventory.ventanas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.and_inventory.MyLog
import com.example.and_inventory.data.InventoryViewModel
import com.example.and_inventory.data.Item
import java.util.Locale

@Composable
fun VentanaBuscar(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: InventoryViewModel
) {
    val items by viewModel.filteredItems.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Búsqueda en Inventario",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- PANEL DE FILTROS ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 1. Búsqueda por nombre exacto
                OutlinedTextField(
                    value = viewModel.filtroNombreExacto,
                    onValueChange = { viewModel.onFiltroNombreChange(it) },
                    label = { Text("Nombre exacto") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // 2. Rango de precios
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.filtroPrecioMin,
                        onValueChange = { viewModel.onFiltroPrecioMinChange(it) },
                        label = { Text("Precio mín.") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    OutlinedTextField(
                        value = viewModel.filtroPrecioMax,
                        onValueChange = { viewModel.onFiltroPrecioMaxChange(it) },
                        label = { Text("Precio máx.") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                }

                // 3. Rango de cantidades
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = viewModel.filtroCantidadMin,
                        onValueChange = { viewModel.onFiltroCantidadMinChange(it) },
                        label = { Text("Cant. mín.") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = viewModel.filtroCantidadMax,
                        onValueChange = { viewModel.onFiltroCantidadMaxChange(it) },
                        label = { Text("Cant. máx.") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                // Mensaje de error de validación en pantalla (si existe)
                viewModel.errorFiltro?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Botón para limpiar filtros
                OutlinedButton(
                    onClick = { viewModel.limpiarFiltros() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Limpiar filtros")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- LISTADO DE RESULTADOS ---
        if (items.isEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No se encontraron productos con esos criterios.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(
                    items = items,
                    key = { item -> item.id }
                ) { item ->
                    ItemFila(item = item)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                MyLog.d("VentanaBuscar.navigate.inicio")
                navController.navigate("inicio")
            }
        ) {
            Text("Inicio")
        }
    }
}

@Composable
fun ItemFila(item: Item, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = String.format(Locale.getDefault(), "Precio: %.2f €", item.price),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text = "Cant: ${item.quantity}",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
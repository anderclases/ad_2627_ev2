package com.example.kmp_inventory.ventanas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun Footer() {
    val navigator = LocalNavigator.currentOrThrow

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                // Opción A: Vuelve a la pantalla raíz (InicioScreen) vaciando las pantallas intermedias
                navigator.popUntilRoot()

                // Opción B: Reinicia toda la pila colocando una nueva instancia de InicioScreen
                // navigator.replaceAll(InicioScreen())
            }
        ) {
            Text("Volver a Home")
        }
    }
}
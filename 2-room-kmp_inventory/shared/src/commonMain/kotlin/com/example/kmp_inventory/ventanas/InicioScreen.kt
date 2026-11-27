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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class InicioScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Text("Soy la primera ventana")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navigator.push(IntroScreen()) }) {
                Text("Ir a intro")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { navigator.push(BuscarScreen()) }) {
                Text("Ir a Buscar")
            }
        }
    }
}
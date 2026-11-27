package com.example.and_inventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.and_inventory.data.InventoryViewModel
import com.example.and_inventory.ui.theme.and_inventoryTheme
import com.example.and_inventory.ventanas.Footer
import com.example.and_inventory.ventanas.VentanaBuscar
import com.example.and_inventory.ventanas.VentanaInicio
import com.example.and_inventory.ventanas.VentanaIntro

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            and_inventoryTheme {
                // Declaramos el NavController aquí arriba para poder pasárselo al Footer si lo necesita (para navegar)
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        // El Footer se coloca aquí para que sea persistente en todas las pantallas
                        Footer(navController = navController)
                    }
                ) { innerPadding ->
                    // Pasamos el innerPadding al gestor de ventanas para que el contenido
                    // no quede oculto detrás del footer
                    GestorVentanas(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun GestorVentanas(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    // Aqui preparamos el viewModel que vamos a enviar a nuestras ventanas, el FActory se encargará de que funcione
    val inventoryViewModel: InventoryViewModel = viewModel(factory = InventoryViewModel.Factory)
    NavHost(
        navController = navController,
        startDestination = "inicio",
        modifier = modifier // Aplicamos el padding que viene del Scaffold
    ) {
        composable("inicio") {VentanaInicio(navController, Modifier, inventoryViewModel)}
        composable("ventanaIntro") {VentanaIntro(navController, Modifier, inventoryViewModel)}
        composable("ventanaBuscar") {VentanaBuscar(navController, Modifier, inventoryViewModel)}
    }
}
package com.example.kmp_inventory.data

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kmp_inventory.LocalAppContainer
import com.example.kmp_inventory.MyLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// 1. Hereda de ViewModel (no AndroidViewModel) y recibe el Repositorio por constructor
class InventoryViewModel(
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    // 2. Expone el Flow proveniente del repositorio
    val allItems: Flow<List<Item>> = itemsRepository.getAllItemsStream()

    fun insertItem(name: String, price: Double, quantity: Int) {
        val newItem = Item(name = name, price = price, quantity = quantity)
        MyLog.d("Nuevo Item creado: $newItem")

        // 3. Lanzamos la corrutina en el scope del ViewModel
        viewModelScope.launch {
            itemsRepository.insertItem(newItem)
            MyLog.d("Insert realizado")
        }
    }
}

@Composable
fun rememberInventoryViewModel(): InventoryViewModel {
    val appContainer = LocalAppContainer.current
    return viewModel(
        factory = viewModelFactory {
            initializer {
                InventoryViewModel(appContainer.itemsRepository)
            }
        }
    )
}
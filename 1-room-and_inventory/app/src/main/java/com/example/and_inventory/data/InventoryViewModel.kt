package com.example.and_inventory.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.and_inventory.MyLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {

    val allItems: Flow<List<Item>> = itemDao.getAllItems()

    // --- ESTADOS DE INSERCIÓN (VentanaIntro) ---
    var nombreProducto by mutableStateOf("")
        private set

    var precioProducto by mutableStateOf("")
        private set

    var cantidadProducto by mutableStateOf("")
        private set

    var mensajeError by mutableStateOf<String?>(null)
        private set

    // --- ESTADOS DE BÚSQUEDA Y FILTRADO (VentanaBuscar) ---
    var filtroNombreExacto by mutableStateOf("")
        private set

    var filtroPrecioMin by mutableStateOf("")
        private set

    var filtroPrecioMax by mutableStateOf("")
        private set

    var filtroCantidadMin by mutableStateOf("")
        private set

    var filtroCantidadMax by mutableStateOf("")
        private set

    var errorFiltro by mutableStateOf<String?>(null)
        private set

    // Estructura interna para agrupar los parámetros limpios hacia Room
    private data class ParametrosBusqueda(
        val nombreExacto: String?,
        val precioMin: Double?,
        val precioMax: Double?,
        val cantidadMin: Int?,
        val cantidadMax: Int?,
        val esValido: Boolean
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredItems: StateFlow<List<Item>> = combine(
        snapshotFlow { filtroNombreExacto },
        snapshotFlow { filtroPrecioMin },
        snapshotFlow { filtroPrecioMax },
        snapshotFlow { filtroCantidadMin },
        snapshotFlow { filtroCantidadMax }
    ) { nombre, pMinStr, pMaxStr, cMinStr, cMaxStr ->
        validarYConstruirParametros(nombre, pMinStr, pMaxStr, cMinStr, cMaxStr)
    }
        .distinctUntilChanged()
        .flatMapLatest { params ->
            if (!params.esValido) {
                flowOf(emptyList())
            } else {
                MyLog.d("Ejecutando query en Room con filtros: $params")
                itemDao.searchItems(
                    nombreExacto = params.nombreExacto,
                    precioMin = params.precioMin,
                    precioMax = params.precioMax,
                    cantidadMin = params.cantidadMin,
                    cantidadMax = params.cantidadMax
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private fun validarYConstruirParametros(
        nombreExacto: String,
        pMinStr: String,
        pMaxStr: String,
        cMinStr: String,
        cMaxStr: String
    ): ParametrosBusqueda {
        val pMin = if (pMinStr.isBlank()) null else pMinStr.toDoubleOrNull()
        val pMax = if (pMaxStr.isBlank()) null else pMaxStr.toDoubleOrNull()
        val cMin = if (cMinStr.isBlank()) null else cMinStr.toIntOrNull()
        val cMax = if (cMaxStr.isBlank()) null else cMaxStr.toIntOrNull()

        val error = when {
            pMinStr.isNotBlank() && pMin == null -> "Precio mínimo inválido"
            pMaxStr.isNotBlank() && pMax == null -> "Precio máximo inválido"
            pMin != null && pMax != null && pMin > pMax -> "El precio mínimo no puede superar al máximo"
            cMinStr.isNotBlank() && cMin == null -> "Cantidad mínima inválida"
            cMaxStr.isNotBlank() && cMax == null -> "Cantidad máxima inválida"
            cMin != null && cMax != null && cMin > cMax -> "La cantidad mínima no puede superar a la máxima"
            else -> null
        }

        errorFiltro = error
        if (error != null) {
            MyLog.d("Error de validación en filtros: $error")
            return ParametrosBusqueda(null, null, null, null, null, esValido = false)
        }

        return ParametrosBusqueda(
            nombreExacto = nombreExacto.trim().takeIf { it.isNotEmpty() },
            precioMin = pMin,
            precioMax = pMax,
            cantidadMin = cMin,
            cantidadMax = cMax,
            esValido = true
        )
    }

    // --- EVENTOS DE FILTRADO ---
    fun onFiltroNombreChange(valor: String) {
        filtroNombreExacto = valor
    }

    fun onFiltroPrecioMinChange(valor: String) {
        filtroPrecioMin = valor
    }

    fun onFiltroPrecioMaxChange(valor: String) {
        filtroPrecioMax = valor
    }

    fun onFiltroCantidadMinChange(valor: String) {
        filtroCantidadMin = valor
    }

    fun onFiltroCantidadMaxChange(valor: String) {
        filtroCantidadMax = valor
    }

    fun limpiarFiltros() {
        MyLog.d("Filtros de búsqueda restablecidos")
        filtroNombreExacto = ""
        filtroPrecioMin = ""
        filtroPrecioMax = ""
        filtroCantidadMin = ""
        filtroCantidadMax = ""
        errorFiltro = null
    }

    // --- EVENTOS Y VALIDACIONES DE INSERCIÓN (VentanaIntro) ---
    fun onNombreChange(nuevoNombre: String) {
        nombreProducto = nuevoNombre
        if (mensajeError != null) mensajeError = null
    }

    fun onPrecioChange(nuevoPrecio: String) {
        precioProducto = nuevoPrecio
        if (mensajeError != null) mensajeError = null
    }

    fun onCantidadChange(nuevaCantidad: String) {
        cantidadProducto = nuevaCantidad
        if (mensajeError != null) mensajeError = null
    }

    fun validarYGuardarItem() {
        MyLog.d("Boton intro datos pulsado - Iniciando validación en ViewModel")

        if (nombreProducto.isBlank()) {
            registrarError("Introduce un nombre válido")
            return
        }

        val dbPrice = precioProducto.toDoubleOrNull()
        if (dbPrice == null || dbPrice < 0) {
            registrarError("El precio debe ser un número válido")
            return
        }

        val dbQuantity = cantidadProducto.toIntOrNull()
        if (dbQuantity == null || dbQuantity < 0) {
            registrarError("La cantidad debe ser un número entero válido")
            return
        }

        mensajeError = null
        insertItem(
            name = nombreProducto.trim(),
            price = dbPrice,
            quantity = dbQuantity
        )
        limpiarFormulario()
    }

    private fun registrarError(mensaje: String) {
        mensajeError = mensaje
        MyLog.d("Error de validación: $mensaje")
    }

    private fun limpiarFormulario() {
        nombreProducto = ""
        precioProducto = ""
        cantidadProducto = ""
    }

    private fun insertItem(name: String, price: Double, quantity: Int) {
        val newItem = Item(name = name, price = price, quantity = quantity)
        MyLog.d("Nuevo Item creado: $newItem")

        viewModelScope.launch(Dispatchers.IO) {
            itemDao.insert(newItem)
            MyLog.d("Insert realizado correctamente en Room")
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                val itemDao = InventoryDatabase.getDatabase(application).itemDao()
                InventoryViewModel(itemDao)
            }
        }
    }
}
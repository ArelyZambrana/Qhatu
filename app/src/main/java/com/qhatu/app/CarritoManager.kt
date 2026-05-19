package com.qhatu.app

object CarritoManager {
    val productos = mutableListOf<Producto>()

    fun agregar(producto: Producto) {
        productos.add(producto)
    }

    fun limpiar() {
        productos.clear()
    }

    fun getTotal(): Double {
        return productos.sumOf { it.precio.toDoubleOrNull() ?: 0.0 }
    }
}
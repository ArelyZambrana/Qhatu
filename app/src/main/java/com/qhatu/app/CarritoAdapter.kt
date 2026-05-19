package com.qhatu.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarritoAdapter(
    private val lista: MutableList<Producto>,
    private val onEliminar: (Int) -> Unit
) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreCarrito)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecioCarrito)
        val tvEliminar: TextView = view.findViewById(R.id.tvEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = lista[position]
        holder.tvNombre.text = producto.nombre
        holder.tvPrecio.text = "Bs. ${producto.precio}"
        holder.tvEliminar.setOnClickListener {
            onEliminar(position)
        }
    }

    override fun getItemCount() = lista.size

    fun eliminarItem(position: Int) {
        lista.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getTotal(): Double {
        return lista.sumOf { it.precio.toDoubleOrNull() ?: 0.0 }
    }
}
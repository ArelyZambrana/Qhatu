package com.qhatu.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Producto(
    val nombre: String,
    val descripcion: String,
    val precio: String,
    val stock: String
)

class ProductoAdapter(private val lista: MutableList<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvInicial: TextView = view.findViewById(R.id.tvInicial)
        val tvNombre: TextView = view.findViewById(R.id.tvNombreProducto)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcionProducto)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecioProducto)
        val tvStock: TextView = view.findViewById(R.id.tvStock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = lista[position]
        holder.tvInicial.text = producto.nombre.first().uppercase()
        holder.tvNombre.text = producto.nombre
        holder.tvDescripcion.text = producto.descripcion
        holder.tvPrecio.text = "Bs. ${producto.precio}"
        holder.tvStock.text = "Stock: ${producto.stock}"
    }

    override fun getItemCount() = lista.size

    fun agregarProducto(producto: Producto) {
        lista.add(producto)
        notifyItemInserted(lista.size - 1)
    }
}
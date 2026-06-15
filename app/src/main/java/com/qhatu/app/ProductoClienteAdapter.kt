package com.qhatu.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductoClienteAdapter(
    private val lista: List<Producto>,
    private val onAgregar: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoClienteAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvInicial: TextView = view.findViewById(R.id.tvInicialCliente)
        val tvNombre: TextView = view.findViewById(R.id.tvNombreCliente)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcionCliente)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecioCliente)
        val tvAgregar: TextView = view.findViewById(R.id.tvAgregarCarritoCliente)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto_cliente, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = lista[position]
        holder.tvInicial.text = producto.nombre.first().uppercase()
        holder.tvNombre.text = producto.nombre
        holder.tvDescripcion.text = producto.descripcion
        holder.tvPrecio.text = "Bs. ${producto.precio}"
        holder.tvAgregar.setOnClickListener { onAgregar(producto) }
    }

    override fun getItemCount() = lista.size
}
package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TiendaClienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tienda_cliente)

        val nombre = intent.getStringExtra("nombre") ?: ""
        val descripcion = intent.getStringExtra("descripcion") ?: ""
        val uid = intent.getStringExtra("uid") ?: ""
        val idTienda = intent.getStringExtra("idTienda") ?: ""

        findViewById<TextView>(R.id.tvNombreTiendaCliente).text = nombre
        findViewById<TextView>(R.id.tvDescripcionTiendaCliente).text = descripcion
        findViewById<TextView>(R.id.tvIdTienda).text = "ID: #$idTienda"

        val listaProductos = mutableListOf<Producto>()
        val adapter = ProductoClienteAdapter(listaProductos) { producto ->
            CarritoManager.agregar(producto)
            android.widget.Toast.makeText(this, "${producto.nombre} agregado al carrito", android.widget.Toast.LENGTH_SHORT).show()
        }

        val rv = findViewById<RecyclerView>(R.id.rvProductosCliente)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        FirebaseHelper.db.collection("tiendas").document(uid)
            .collection("productos").get()
            .addOnSuccessListener { docs ->
                listaProductos.clear()
                listaProductos.addAll(docs.map { doc ->
                    Producto(
                        nombre = doc.getString("nombre") ?: "",
                        descripcion = doc.getString("descripcion") ?: "",
                        precio = doc.getString("precio") ?: "",
                        stock = doc.getString("stock") ?: ""
                    )
                })
                adapter.notifyDataSetChanged()
            }

        findViewById<TextView>(R.id.tvVolverCliente).setOnClickListener { finish() }

        findViewById<TextView>(R.id.tvCarritoCliente).setOnClickListener {
            startActivity(Intent(this, CarritoActivity::class.java))
        }
    }
}
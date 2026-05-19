package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CatalogoActivity : AppCompatActivity() {

    private lateinit var adapter: ProductoAdapter
    private val listaProductos = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        val nombre = intent.getStringExtra("nombre") ?: "Mi Tienda"
        val descripcion = intent.getStringExtra("descripcion") ?: ""

        findViewById<TextView>(R.id.tvNombreTienda).text = nombre
        findViewById<TextView>(R.id.tvDescripcionTienda).text = descripcion

        adapter = ProductoAdapter(listaProductos) { producto ->
            CarritoManager.agregar(producto)
            android.widget.Toast.makeText(this, "${producto.nombre} agregado al carrito", android.widget.Toast.LENGTH_SHORT).show()
        }

        val rv = findViewById<RecyclerView>(R.id.rvProductos)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        findViewById<TextView>(R.id.tvCarrito).setOnClickListener {
            startActivity(Intent(this, CarritoActivity::class.java))
        }
        findViewById<TextView>(R.id.tvAgregarProducto).setOnClickListener {
            val intent = Intent(this, AgregarProductoActivity::class.java)
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            val producto = Producto(
                nombre = data.getStringExtra("nombre") ?: "",
                descripcion = data.getStringExtra("descripcion") ?: "",
                precio = data.getStringExtra("precio") ?: "",
                stock = data.getStringExtra("stock") ?: ""
            )
            adapter.agregarProducto(producto)
        }
    }
}
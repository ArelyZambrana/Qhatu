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

        val tvNombre = findViewById<TextView>(R.id.tvNombreTienda)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcionTienda)

        val uid = FirebaseHelper.auth.currentUser?.uid
        if (uid != null) {
            FirebaseHelper.db.collection("tiendas").document(uid).get()
                .addOnSuccessListener { doc ->
                    tvNombre.text = doc.getString("nombre") ?: "Mi Tienda"
                    tvDescripcion.text = doc.getString("descripcion") ?: ""
                }
        }

        adapter = ProductoAdapter(
            listaProductos,
            onAgregarCarrito = { producto ->
                CarritoManager.agregar(producto)
                android.widget.Toast.makeText(this, "${producto.nombre} agregado al carrito", android.widget.Toast.LENGTH_SHORT).show()
            },
            onEditar = { position, producto ->
                val intent = Intent(this, AgregarProductoActivity::class.java)
                intent.putExtra("editar", true)
                intent.putExtra("position", position)
                intent.putExtra("nombre", producto.nombre)
                intent.putExtra("precio", producto.precio)
                intent.putExtra("descripcion", producto.descripcion)
                intent.putExtra("stock", producto.stock)
                startActivityForResult(intent, 101)
            }
        )

        val rv = findViewById<RecyclerView>(R.id.rvProductos)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        cargarProductos()

        findViewById<TextView>(R.id.tvMenu).setOnClickListener {
            val opciones = arrayOf("Mi perfil", "Historial de ventas", "Cerrar sesión")
            android.app.AlertDialog.Builder(this)
                .setTitle("Menú")
                .setItems(opciones) { _, which ->
                    when (which) {
                        0 -> android.widget.Toast.makeText(this, "Próximamente", android.widget.Toast.LENGTH_SHORT).show()
                        1 -> android.widget.Toast.makeText(this, "Próximamente", android.widget.Toast.LENGTH_SHORT).show()
                        2 -> {
                            FirebaseHelper.auth.signOut()
                            startActivity(Intent(this, MainActivity::class.java))
                            finishAffinity()
                        }
                    }
                }
                .show()
        }

        findViewById<TextView>(R.id.tvAgregarProducto).setOnClickListener {
            startActivityForResult(Intent(this, AgregarProductoActivity::class.java), 100)
        }

        findViewById<TextView>(R.id.tvCarrito).setOnClickListener {
            startActivity(Intent(this, CarritoActivity::class.java))
        }
    }

    private fun cargarProductos() {
        FirebaseHelper.obtenerProductos(
            onSuccess = { productos ->
                listaProductos.clear()
                listaProductos.addAll(productos)
                adapter.notifyDataSetChanged()
            },
            onError = {}
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val producto = Producto(
                nombre = data.getStringExtra("nombre") ?: "",
                descripcion = data.getStringExtra("descripcion") ?: "",
                precio = data.getStringExtra("precio") ?: "",
                stock = data.getStringExtra("stock") ?: ""
            )
            if (requestCode == 100) {
                FirebaseHelper.guardarProducto(producto,
                    onSuccess = { cargarProductos() },
                    onError = {}
                )
            } else if (requestCode == 101) {
                val position = data.getIntExtra("position", -1)
                if (position >= 0) adapter.editarProducto(position, producto)
            }
        }
    }
}
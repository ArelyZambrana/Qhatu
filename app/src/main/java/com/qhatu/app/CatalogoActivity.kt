package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class CatalogoActivity : AppCompatActivity() {

    private lateinit var adapter: ProductoAdapter
    private val listaProductos = mutableListOf<Producto>()
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)

        drawerLayout = findViewById(R.id.drawerLayout)
        val navView = findViewById<NavigationView>(R.id.navView)
        val tvNombre = findViewById<TextView>(R.id.tvNombreTienda)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcionTienda)

        val header = navView.getHeaderView(0)
        val tvDrawerNombre = header.findViewById<TextView>(R.id.tvDrawerNombre)
        val tvDrawerId = header.findViewById<TextView>(R.id.tvDrawerId)

        val uid = FirebaseHelper.auth.currentUser?.uid
        if (uid != null) {
            FirebaseHelper.db.collection("tiendas").document(uid).get()
                .addOnSuccessListener { doc ->
                    val nombre = doc.getString("nombre") ?: "Mi Tienda"
                    tvNombre.text = nombre
                    tvDescripcion.text = doc.getString("descripcion") ?: ""
                    tvDrawerNombre.text = nombre
                    tvDrawerId.text = "#${doc.get("idTienda")}"
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
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_perfil -> startActivity(Intent(this, MiPerfilActivity::class.java))
                R.id.nav_historial -> startActivity(Intent(this, HistorialVentasActivity::class.java))
                R.id.nav_dashboard -> startActivity(Intent(this, DashboardActivity::class.java))
                R.id.nav_compartir -> compartirTienda()
                R.id.nav_config -> startActivity(Intent(this, ConfiguracionActivity::class.java))
                R.id.nav_cerrar -> {
                    FirebaseHelper.auth.signOut()
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        findViewById<TextView>(R.id.tvAgregarProducto).setOnClickListener {
            startActivityForResult(Intent(this, AgregarProductoActivity::class.java), 100)
        }

        findViewById<TextView>(R.id.tvCarrito).setOnClickListener {
            startActivity(Intent(this, CarritoActivity::class.java))
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun compartirTienda() {
        val uid = FirebaseHelper.auth.currentUser?.uid ?: return
        FirebaseHelper.db.collection("tiendas").document(uid).get()
            .addOnSuccessListener { doc ->
                val nombre = doc.getString("nombre") ?: "Mi tienda"
                val id = doc.get("idTienda")?.toString() ?: ""
                val mensaje = "¡Visitá mi tienda $nombre en Qhatu! Buscá el código #$id en la app."
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, mensaje)
                startActivity(Intent.createChooser(intent, "Compartir mi tienda"))
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
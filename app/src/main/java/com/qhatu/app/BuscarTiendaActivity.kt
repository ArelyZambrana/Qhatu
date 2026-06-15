package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class BuscarTiendaActivity : AppCompatActivity() {

    private val todasLasTiendas = mutableListOf<String>()
    private val datosTiendas = mutableListOf<Map<String, Any>>()
    private var tiendsFiltradas = mutableListOf<Map<String, Any>>()
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_tienda)

        drawerLayout = findViewById(R.id.drawerLayoutCliente)
        val navView = findViewById<NavigationView>(R.id.navViewCliente)
        val lv = findViewById<ListView>(R.id.lvTiendas)
        val etBuscar = findViewById<EditText>(R.id.etBuscarTienda)

        findViewById<TextView>(R.id.tvMenuCliente).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_carrito -> startActivity(Intent(this, CarritoActivity::class.java))
                R.id.nav_favoritos -> Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show()
                R.id.nav_config_cliente -> startActivity(Intent(this, ConfiguracionActivity::class.java))
                R.id.nav_modo_emprendedor -> {
                    startActivity(Intent(this, OpcionEmprendedorActivity::class.java))
                }
                R.id.nav_volver_inicio -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        FirebaseHelper.obtenerTiendas(
            onSuccess = { tiendas ->
                datosTiendas.clear()
                datosTiendas.addAll(tiendas)
                tiendsFiltradas.addAll(tiendas)
                todasLasTiendas.clear()
                todasLasTiendas.addAll(tiendas.map { tienda ->
                    val nombre = tienda["nombre"] as? String ?: ""
                    val id = tienda["idTienda"]?.toString() ?: ""
                    "$nombre  #$id"
                })
                lv.adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    todasLasTiendas
                )
            },
            onError = { error ->
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        )

        lv.setOnItemClickListener { _, _, position, _ ->
            val tienda = tiendsFiltradas[position]
            val intent = Intent(this, TiendaClienteActivity::class.java)
            intent.putExtra("nombre", tienda["nombre"] as? String ?: "")
            intent.putExtra("descripcion", tienda["descripcion"] as? String ?: "")
            intent.putExtra("uid", tienda["uid"] as? String ?: "")
            intent.putExtra("idTienda", tienda["idTienda"]?.toString() ?: "")
            startActivity(intent)
        }

        etBuscar.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tiendsFiltradas = datosTiendas.filter {
                    val nombre = it["nombre"] as? String ?: ""
                    val id = it["idTienda"]?.toString() ?: ""
                    nombre.contains(s.toString(), ignoreCase = true) ||
                            id.contains(s.toString(), ignoreCase = true)
                }.toMutableList()
                val nombres = tiendsFiltradas.map { tienda ->
                    val nombre = tienda["nombre"] as? String ?: ""
                    val id = tienda["idTienda"]?.toString() ?: ""
                    "$nombre  #$id"
                }
                lv.adapter = ArrayAdapter(
                    this@BuscarTiendaActivity,
                    android.R.layout.simple_list_item_1,
                    nombres
                )
            }
        })
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
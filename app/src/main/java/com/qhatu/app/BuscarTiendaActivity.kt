package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BuscarTiendaActivity : AppCompatActivity() {

    private val todasLasTiendas = mutableListOf<String>()
    private val datosTiendas = mutableListOf<Map<String, Any>>()
    private var tiendsFiltradas = mutableListOf<Map<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_tienda)

        val lv = findViewById<ListView>(R.id.lvTiendas)
        val etBuscar = findViewById<EditText>(R.id.etBuscarTienda)

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
}
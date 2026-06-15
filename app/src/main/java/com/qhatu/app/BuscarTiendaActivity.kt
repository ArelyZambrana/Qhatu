package com.qhatu.app

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BuscarTiendaActivity : AppCompatActivity() {

    private val todasLasTiendas = mutableListOf<String>()
    private val datosTiendas = mutableListOf<Map<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_tienda)

        val lv = findViewById<ListView>(R.id.lvTiendas)
        val etBuscar = findViewById<EditText>(R.id.etBuscarTienda)

        FirebaseHelper.obtenerTiendas(
            onSuccess = { tiendas ->
                datosTiendas.clear()
                datosTiendas.addAll(tiendas)
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

        etBuscar.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filtradas = todasLasTiendas.filter {
                    it.contains(s.toString(), ignoreCase = true)
                }
                lv.adapter = ArrayAdapter(
                    this@BuscarTiendaActivity,
                    android.R.layout.simple_list_item_1,
                    filtradas
                )
            }
        })
    }
}
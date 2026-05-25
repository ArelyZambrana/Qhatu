package com.qhatu.app

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BuscarTiendaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_tienda)

        val tiendas = listOf(
            "#1001 · Ropa Gótica by Arely",
            "#1002 · Artesanías Cochabamba",
            "#1003 · Dulces y Repostería Mary",
            "#1004 · Accesorios Boho",
            "#1005 · Ropa Deportiva Cbba"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tiendas)
        val lv = findViewById<ListView>(R.id.lvTiendas)
        lv.adapter = adapter

        val etBuscar = findViewById<EditText>(R.id.etBuscarTienda)
        etBuscar.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filtradas = tiendas.filter {
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
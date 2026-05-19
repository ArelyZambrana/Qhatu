package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class CrearTiendaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_tienda)

        val spinnerCategoria = findViewById<Spinner>(R.id.spinnerCategoria)
        val btnCrear = findViewById<Button>(R.id.btnCrearTienda)
        val etNombre = findViewById<EditText>(R.id.etNombreTienda)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)

        val categorias = listOf(
            "Ropa y accesorios",
            "Comida y bebidas",
            "Tecnología",
            "Artesanías",
            "Belleza y cuidado personal",
            "Hogar y decoración",
            "Deportes",
            "Otros"
        )

        spinnerCategoria.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            categorias
        )

        btnCrear.setOnClickListener {
            val nombre = etNombre.text.toString()
            val descripcion = etDescripcion.text.toString()

            val intent = Intent(this, CatalogoActivity::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("descripcion", descripcion)
            startActivity(intent)
        }
    }
}
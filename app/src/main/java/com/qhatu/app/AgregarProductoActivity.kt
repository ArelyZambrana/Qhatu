package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AgregarProductoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)

        val etNombre = findViewById<EditText>(R.id.etNombreProducto)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcionProducto)
        val etStock = findViewById<EditText>(R.id.etStock)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarProducto)
        val tvVolver = findViewById<TextView>(R.id.tvVolver)

        val esEdicion = intent.getBooleanExtra("editar", false)
        val position = intent.getIntExtra("position", -1)

        if (esEdicion) {
            etNombre.setText(intent.getStringExtra("nombre"))
            etPrecio.setText(intent.getStringExtra("precio"))
            etDescripcion.setText(intent.getStringExtra("descripcion"))
            etStock.setText(intent.getStringExtra("stock"))
            btnGuardar.text = "Guardar cambios"
            findViewById<TextView>(R.id.tvVolver).also {
                (it.parent as? android.view.ViewGroup)?.let { parent ->
                    parent.findViewWithTag<TextView>("titulo")?.text = "Editar Producto"
                }
            }
        }

        tvVolver.setOnClickListener { finish() }

        btnGuardar.setOnClickListener {
            val intent = Intent()
            intent.putExtra("nombre", etNombre.text.toString())
            intent.putExtra("precio", etPrecio.text.toString())
            intent.putExtra("descripcion", etDescripcion.text.toString())
            intent.putExtra("stock", etStock.text.toString())
            intent.putExtra("position", position)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OpcionEmprendedorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opcion_emprendedor)

        findViewById<Button>(R.id.btnIniciarSesion).setOnClickListener {
            startActivity(Intent(this, LoginEmprendedorActivity::class.java))
        }

        findViewById<Button>(R.id.btnRegistrarse).setOnClickListener {
            startActivity(Intent(this, CrearTiendaActivity::class.java))
        }

        findViewById<TextView>(R.id.tvVolverOpcion).setOnClickListener {
            finish()
        }
    }
}
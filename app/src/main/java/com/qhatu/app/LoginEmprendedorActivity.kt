package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginEmprendedorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_emprendedor)

        findViewById<Button>(R.id.btnIngresarEmprendedor).setOnClickListener {
            startActivity(Intent(this, CrearTiendaActivity::class.java))
        }

        findViewById<TextView>(R.id.tvCrearCuenta).setOnClickListener {
            startActivity(Intent(this, CrearTiendaActivity::class.java))
        }
    }
}
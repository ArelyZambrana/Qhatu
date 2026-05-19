package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvCrearTienda = findViewById<TextView>(R.id.tvCrearTienda)

        btnLogin.setOnClickListener {
            startActivity(Intent(this, CatalogoActivity::class.java))
        }

        tvCrearTienda.setOnClickListener {
            startActivity(Intent(this, CrearTiendaActivity::class.java))
        }
    }
}
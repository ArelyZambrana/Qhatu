package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnEmprendedor).setOnClickListener {
            startActivity(Intent(this, LoginEmprendedorActivity::class.java))
        }

        findViewById<Button>(R.id.btnCliente).setOnClickListener {
            startActivity(Intent(this, BuscarTiendaActivity::class.java))
        }
    }
}
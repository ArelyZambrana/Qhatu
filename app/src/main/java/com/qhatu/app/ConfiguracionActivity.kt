package com.qhatu.app

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConfiguracionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        findViewById<TextView>(R.id.tvVolverConfig).setOnClickListener { finish() }

        findViewById<android.widget.Switch>(R.id.switchModoOscuro).setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, if (isChecked) "Modo oscuro próximamente" else "Modo claro", Toast.LENGTH_SHORT).show()
        }
    }
}
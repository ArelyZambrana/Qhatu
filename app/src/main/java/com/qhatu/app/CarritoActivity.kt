package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarritoActivity : AppCompatActivity() {

    private lateinit var adapter: CarritoAdapter
    private lateinit var tvTotal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        val productos = CarritoManager.productos

        tvTotal = findViewById(R.id.tvTotal)
        val rv = findViewById<RecyclerView>(R.id.rvCarrito)

        adapter = CarritoAdapter(productos) { position ->
            adapter.eliminarItem(position)
            actualizarTotal()
        }

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        actualizarTotal()

        findViewById<TextView>(R.id.tvVolverCarrito).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnPagarQR).setOnClickListener {
            startActivity(Intent(this, QRActivity::class.java))
        }
    }

    private fun actualizarTotal() {
        tvTotal.text = "Bs. ${String.format("%.2f", adapter.getTotal())}"
    }
}
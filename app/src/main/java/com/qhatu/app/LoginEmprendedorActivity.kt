package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginEmprendedorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_emprendedor)

        val etEmail = findViewById<EditText>(R.id.etEmailEmprendedor)
        val etPassword = findViewById<EditText>(R.id.etPasswordEmprendedor)
        val btnIngresar = findViewById<Button>(R.id.btnIngresarEmprendedor)

        btnIngresar.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            when {
                email.isEmpty() -> {
                    etEmail.error = "Ingresá tu correo"
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    etEmail.error = "Correo inválido, debe tener @"
                }
                password.isEmpty() -> {
                    etPassword.error = "Ingresá tu contraseña"
                }
                password.length < 6 -> {
                    etPassword.error = "Mínimo 6 caracteres"
                }
                !password.any { it.isDigit() } -> {
                    etPassword.error = "Debe tener al menos un número"
                }
                else -> {
                    startActivity(Intent(this, CatalogoActivity::class.java))
                }
            }
        }

        findViewById<TextView>(R.id.tvCrearCuenta).setOnClickListener {
            startActivity(Intent(this, CrearTiendaActivity::class.java))
        }
    }
}
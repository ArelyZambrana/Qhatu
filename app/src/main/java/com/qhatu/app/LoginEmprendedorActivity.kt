package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
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

        if (FirebaseHelper.auth.currentUser != null) {
            startActivity(Intent(this, CatalogoActivity::class.java))
            finish()
            return
        }

        btnIngresar.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            when {
                email.isEmpty() -> etEmail.error = "Ingresá tu correo"
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> etEmail.error = "Correo inválido, debe tener @"
                password.isEmpty() -> etPassword.error = "Ingresá tu contraseña"
                password.length < 6 -> etPassword.error = "Mínimo 6 caracteres"
                else -> {
                    btnIngresar.isEnabled = false
                    btnIngresar.text = "Ingresando..."
                    FirebaseHelper.iniciarSesion(email, password,
                        onSuccess = {
                            startActivity(Intent(this, CatalogoActivity::class.java))
                            finish()
                        },
                        onError = { error ->
                            btnIngresar.isEnabled = true
                            btnIngresar.text = "Ingresar a mi tienda"
                            Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                        }
                    )
                }
            }
        }

        findViewById<TextView>(R.id.tvCrearCuenta).setOnClickListener {
            startActivity(Intent(this, CrearTiendaActivity::class.java))
        }
    }
}
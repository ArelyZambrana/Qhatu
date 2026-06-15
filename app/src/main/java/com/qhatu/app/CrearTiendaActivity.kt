package com.qhatu.app

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CrearTiendaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_tienda)

        val spinnerCategoria = findViewById<Spinner>(R.id.spinnerCategoria)
        val btnCrear = findViewById<Button>(R.id.btnCrearTienda)
        val etNombre = findViewById<EditText>(R.id.etNombreTienda)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val etEmail = findViewById<EditText>(R.id.etEmailRegistro)
        val etPassword = findViewById<EditText>(R.id.etPasswordRegistro)
        val etWhatsapp = findViewById<EditText>(R.id.etWhatsapp)

        val categorias = listOf(
            "Ropa y accesorios", "Comida y bebidas", "Tecnología",
            "Artesanías", "Belleza y cuidado personal", "Hogar y decoración",
            "Deportes", "Otros"
        )

        spinnerCategoria.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, categorias
        )

        btnCrear.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val whatsapp = etWhatsapp.text.toString().trim()

            when {
                nombre.isEmpty() -> etNombre.error = "Ingresá el nombre de tu tienda"
                email.isEmpty() -> etEmail.error = "Ingresá tu correo"
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> etEmail.error = "Correo inválido"
                password.length < 6 -> etPassword.error = "Mínimo 6 caracteres"
                else -> {
                    btnCrear.isEnabled = false
                    btnCrear.text = "Creando tu tienda..."
                    FirebaseHelper.registrarEmprendedor(
                        email, password, nombre, descripcion,
                        spinnerCategoria.selectedItem.toString(), whatsapp,
                        onSuccess = { idTienda ->
                            Toast.makeText(this, "¡Tienda creada! Tu ID es #$idTienda", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, CatalogoActivity::class.java))
                            finish()
                        },
                        onError = { error ->
                            btnCrear.isEnabled = true
                            btnCrear.text = "Crear mi tienda"
                            Toast.makeText(this, "Error: $error", Toast.LENGTH_LONG).show()
                        }
                    )
                }
            }
        }
    }
}
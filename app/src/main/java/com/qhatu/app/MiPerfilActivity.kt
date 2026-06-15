package com.qhatu.app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MiPerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_perfil)

        val etNombre = findViewById<EditText>(R.id.etNombrePerfil)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcionPerfil)
        val etWhatsapp = findViewById<EditText>(R.id.etWhatsappPerfil)
        val tvId = findViewById<TextView>(R.id.tvIdPerfil)

        val uid = FirebaseHelper.auth.currentUser?.uid

        if (uid != null) {
            FirebaseHelper.db.collection("tiendas").document(uid).get()
                .addOnSuccessListener { doc ->
                    etNombre.setText(doc.getString("nombre"))
                    etDescripcion.setText(doc.getString("descripcion"))
                    etWhatsapp.setText(doc.getString("whatsapp"))
                    tvId.text = "ID de tu tienda: #${doc.get("idTienda")}"
                }
        }

        findViewById<TextView>(R.id.tvVolverPerfil).setOnClickListener { finish() }

        findViewById<Button>(R.id.btnGuardarPerfil).setOnClickListener {
            if (uid != null) {
                val cambios = hashMapOf<String, Any>(
                    "nombre" to etNombre.text.toString(),
                    "descripcion" to etDescripcion.text.toString(),
                    "whatsapp" to etWhatsapp.text.toString()
                )
                FirebaseHelper.db.collection("tiendas").document(uid)
                    .update(cambios)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
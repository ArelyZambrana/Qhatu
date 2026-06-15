package com.qhatu.app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        findViewById<TextView>(R.id.tvVolverDashboard).setOnClickListener { finish() }

        val uid = FirebaseHelper.auth.currentUser?.uid
        if (uid != null) {
            FirebaseHelper.db.collection("tiendas").document(uid)
                .collection("productos").get()
                .addOnSuccessListener { docs ->
                    findViewById<TextView>(R.id.tvTotalProductos).text = docs.size().toString()
                }
        }
    }
}
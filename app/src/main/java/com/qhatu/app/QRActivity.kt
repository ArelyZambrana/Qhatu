package com.qhatu.app

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

class QRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        val total = CarritoManager.getTotal()
        val totalFormateado = String.format("%.2f", total)

        findViewById<TextView>(R.id.tvTotalQR).text = "Total: Bs. $totalFormateado"

        val contenidoQR = "QHATU-PAGO|Total:Bs.$totalFormateado|Productos:${CarritoManager.productos.size}"
        val qrBitmap = generarQR(contenidoQR)
        findViewById<ImageView>(R.id.ivQR).setImageBitmap(qrBitmap)

        findViewById<Button>(R.id.btnConfirmarPago).setOnClickListener {
            CarritoManager.limpiar()
            Toast.makeText(this, "¡Pedido confirmado! Gracias por tu compra", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun generarQR(contenido: String): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(contenido, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }
}
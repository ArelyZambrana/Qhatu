package com.qhatu.app

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseHelper {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun registrarEmprendedor(
        email: String,
        password: String,
        nombreTienda: String,
        descripcion: String,
        categoria: String,
        whatsapp: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener
                val idTienda = (1000..9999).random()
                val tienda = hashMapOf(
                    "nombre" to nombreTienda,
                    "descripcion" to descripcion,
                    "categoria" to categoria,
                    "whatsapp" to whatsapp,
                    "idTienda" to idTienda,
                    "uid" to uid
                )
                db.collection("tiendas").document(uid).set(tienda)
                    .addOnSuccessListener { onSuccess(idTienda.toString()) }
                    .addOnFailureListener { onError(it.message ?: "Error al guardar tienda") }
            }
            .addOnFailureListener { onError(it.message ?: "Error al registrar") }
    }

    fun iniciarSesion(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Error al iniciar sesión") }
    }

    fun guardarProducto(
        producto: Producto,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: return
        val data = hashMapOf(
            "nombre" to producto.nombre,
            "descripcion" to producto.descripcion,
            "precio" to producto.precio,
            "stock" to producto.stock
        )
        db.collection("tiendas").document(uid)
            .collection("productos").add(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Error") }
    }

    fun obtenerProductos(
        onSuccess: (List<Producto>) -> Unit,
        onError: (String) -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: return
        db.collection("tiendas").document(uid)
            .collection("productos").get()
            .addOnSuccessListener { docs ->
                val lista = docs.map { doc ->
                    Producto(
                        nombre = doc.getString("nombre") ?: "",
                        descripcion = doc.getString("descripcion") ?: "",
                        precio = doc.getString("precio") ?: "",
                        stock = doc.getString("stock") ?: ""
                    )
                }
                onSuccess(lista)
            }
            .addOnFailureListener { onError(it.message ?: "Error") }
    }

    fun obtenerTiendas(
        onSuccess: (List<Map<String, Any>>) -> Unit,
        onError: (String) -> Unit
    ) {
        db.collection("tiendas").get()
            .addOnSuccessListener { docs ->
                val lista = docs.map { it.data }
                onSuccess(lista)
            }
            .addOnFailureListener { onError(it.message ?: "Error") }
    }
}
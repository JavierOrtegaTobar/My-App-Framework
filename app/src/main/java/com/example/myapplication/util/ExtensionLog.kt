package com.example.myapplication.util
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun enviarDatos(documentId: String, fieldValue: Any) {
    val db = FirebaseFirestore.getInstance()
    val collection = db.collection("Logs")
    val usercollection = db.collection("nombre")


    val timestamp = Timestamp.now()
    val date = Date(timestamp.seconds * 1000)

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    val fieldName = "Fecha: ${dateFormat.format(date)}, Hora: ${timeFormat.format(date)}, nombre"

    val data = hashMapOf(
        fieldName to fieldValue
    )

    collection.document(documentId)
        .update(data)
        .addOnSuccessListener {
            // Éxito al enviar los datos al documento
        }
        .addOnFailureListener { e ->
            // Error al enviar los datos al documento
        }
}
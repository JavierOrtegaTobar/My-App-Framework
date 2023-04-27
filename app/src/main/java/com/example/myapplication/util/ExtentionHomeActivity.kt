package com.example.myapplication.util

import android.content.ContentValues
import android.util.Log
import com.example.myapplication.Class.MyAdapter
import com.example.myapplication.HomeActivity

fun HomeActivity.menuDependingBank(){
    db.collection("banco")
        .document(idDocumento)
        .get()
        .addOnSuccessListener { document ->
            if (document != null) {
                // Obtiene los valores de los botones del documento
                val btn1 = document.getString("btn1")
                val btn2 = document.getString("btn2")
                val btn3 = document.getString("btn3")
                val btn4 = document.getString("btn4")
                val btn5 = document.getString("btn5")
                val btn6 = document.getString("btn6")
                val btn7 = document.getString("btn7")
                val btn8 = document.getString("btn8")

                val items = listOf(btn1 ?: "", btn2 ?: "", btn3 ?: "", btn4 ?: "", btn5 ?: "", btn6 ?: "", btn7 ?: "", btn8 ?: "")
                val adapter = MyAdapter(items)
                recyclerView.adapter = adapter
            } else {
                Log.d(ContentValues.TAG, "No se encontró ningún documento con el id $idDocumento")
            }
        }
        .addOnFailureListener { exception ->
            Log.e(ContentValues.TAG, "Error al obtener datos de Firebase: ", exception)
        }
}



fun HomeActivity.bankUser() {
    val emailUserFirebase = intent.getStringExtra("emailUser")
    if (!emailUserFirebase.isNullOrEmpty()) {
        when {
            emailUserFirebase.contains("@estado") || emailUserFirebase.contains("@bancoestado") -> {
                idDocumento = "estado"
            }
            emailUserFirebase.contains("@bci") -> {
                idDocumento = "bci"
            }
            emailUserFirebase.contains("@santander") -> {
                idDocumento = "santander"
            }
        }
    }
}

fun HomeActivity.typeUser(){
    val emailUser = intent.getStringExtra("emailUser") as String
    val documentRef = db.collection("usuarios").document(emailUser)


    documentRef.get().addOnSuccessListener { documentSnapshot ->
        if (documentSnapshot != null) {
            val typeUser = documentSnapshot.getString("typeUser")
            val userName = documentSnapshot.getString("nombre")
            when {
                typeUser == "administrador" || typeUser == "Admin" -> {
                    binding.progressbar.textTyperUser.text = "Administrador :"
                    binding.progressbar.textNameUser.text = userName
                }
                typeUser == "cliente" -> {
                    binding.progressbar.textTyperUser.text = "Cliente :"
                    binding.progressbar.textNameUser.text = userName
                }
                else -> {
                    binding.progressbar.textTyperUser.text = "Cliente :"
                    binding.progressbar.textNameUser.text = userName
                }
            }
        } else {
            Log.d(ContentValues.TAG, "No se encontró el documento")
        }
    }.addOnFailureListener { e ->
        Log.w(ContentValues.TAG, "Error al obtener documento", e)
    }
}
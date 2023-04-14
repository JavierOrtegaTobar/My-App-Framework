package com.example.myapplication.util

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.myapplication.HomeActivity
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth


fun LoginActivity.onClickLogin() {
    val email = binding.lgUsuario.text.toString().trim()
    val password = binding.lgContrasena.text.toString().trim()

    if (email.isEmpty() || password.isEmpty()) {
        showAlertAddParameters()
        return
    }
    if (!isValidEmail(email)) {
        Toast.makeText(
            this,
            "Correo eléctronico invalido",
            Toast.LENGTH_SHORT
        ).show()
        return
    }
    if (password.length < 6) {
        binding.lgContrasena.error = "La contraseña debe tener al menos 6 caracteres"
        return
    }


    if (loginAttempts < 3) {
        val userRef = userFirebase.document(email)
        userRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val estado = documentSnapshot.getBoolean("estado") ?: false
                if (estado) {
                    showAlertUserBlocked()
                    binding.btnIngresar.isEnabled = false
                    return@addOnSuccessListener
                }
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, HomeActivity::class.java)
                            val animation = ActivityOptions.makeCustomAnimation(
                                this,
                                R.anim.slide_in_right,
                                R.anim.slide_out_left
                            ).toBundle()
                            startActivity(intent, animation)
                            finish()
                        } else {
                            showAlert()
                            loginAttempts++
                            if (loginAttempts == 3) {
                                bloquearUsuarioEnFirebase(email)
                                Toast.makeText(
                                    this,
                                    "Se han superado el número de intentos permitidos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
            }
        }.addOnFailureListener { exception ->
            Log.e(TAG, "Error al obtener el documento de usuarios en Firebase", exception)
        }
    } else {

    }
}

private fun LoginActivity.bloquearUsuarioEnFirebase(email: String) {
    val userIdDocument = userFirebase.document(email)
    val data = hashMapOf("estado" to true)
    userIdDocument.update(data.toMap()).addOnSuccessListener {
        Log.d(TAG, "Usuario bloqueado en Firebase")
    }.addOnFailureListener { exception ->
        Log.e(TAG, "Error al bloquear usuario en Firebase", exception)
    }
}

private fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

private fun LoginActivity.showAlert() {

    val builder = AlertDialog.Builder(binding.root.context)
    builder.setTitle("Error")
    builder.setMessage("Se ha producido un error de autenticacion")
    builder.setPositiveButton("Aceptar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

private fun LoginActivity.showAlertAddParameters() {

    val builder = AlertDialog.Builder(binding.root.context)
    builder.setTitle("Error")
    builder.setMessage("Por favor rellena todos los campos")
    builder.setPositiveButton("Aceptar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

private fun LoginActivity.showAlertUserBlocked() {

    val builder = AlertDialog.Builder(binding.root.context)
    builder.setTitle("Usuario bloqueado")
    builder.setMessage("Tu usuario se encuentra bloqueado, por favor comunicate con el administrador")
    builder.setPositiveButton("Aceptar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}


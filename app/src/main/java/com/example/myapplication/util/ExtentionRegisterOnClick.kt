package com.example.myapplication.util

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import com.example.myapplication.HomeActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.RegistroActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties
import java.util.regex.Pattern


fun RegistroActivity.onClickResgisterUser() {
    val user = binding.lgUsuario.text.toString().trim()
    val email = binding.lgEmail.text.toString().trim()
    val password = binding.lgContrasena.text.toString().trim()
    val confirmPassword = binding.lgConfirmContrasena.text.toString().trim()
    val empresa = binding.lgEmail.text.split("@").get(1)

    val passwordCode = codificarContrasena(password,7)


    val pattern = Pattern.compile("[^a-zA-Z0-9]")
    val matcher = pattern.matcher(password)
    val containsSpecialChar = matcher.find()

    if (user.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
        showAlertAddParameters()
        return
    }
    if (!isValidEmail(email)) {
        binding.lgUsuario.error = "Correo electrónico inválido"
        return
    }
    if (password.length < 6) {
        binding.lgContrasena.error = "La contraseña debe tener al menos 6 caracteres"
        return
    }
    if (!containsSpecialChar) {
        binding.lgContrasena.error = "La contraseña debe contener al menos un caracter especial"
        return
    }
    if (password == confirmPassword && user.isNotEmpty() && email.isNotEmpty()) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val usuario = Usuario(user, email, passwordCode, empresa, estado = false)
                    usuariosRef.document(email).set(usuario)
                        .addOnSuccessListener {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("email", email)
                            val animation = ActivityOptions.makeCustomAnimation(
                                this,
                                R.anim.slide_in_right,
                                R.anim.slide_out_left
                            ).toBundle()
                            startActivity(intent, animation)
                            finish()
                            Toast.makeText(
                                this,
                                "Usuario registrado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener {
                            showAlert()
                        }
                } else {
                    showAlert()
                }
            }

    } else {
        Toast.makeText(this, "las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
    }
}

private fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

private fun RegistroActivity.showAlertAddParameters() {

    val builder = AlertDialog.Builder(binding.root.context)
    builder.setTitle("Campos vacios")
    builder.setMessage("Por favor rellena todos los campos")
    builder.setPositiveButton("Aceptar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

private fun RegistroActivity.showAlert() {

    val builder = AlertDialog.Builder(binding.root.context)
    builder.setTitle("Error")
    builder.setMessage("Se ha producido un error de autenticacion")
    builder.setPositiveButton("Aceptar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

data class Usuario(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val empresa: String = "",
    val estado: Boolean = false
) {
    //
}

private fun codificarContrasena(contrasena: String, desplazamiento: Int): String {
    var contrasenaCodificada = ""
    for (i in 0 until contrasena.length) {
        val letra = contrasena[i]
        if (letra.isLetter()) {
            var letraCodificada = letra + desplazamiento
            if (letra.isLowerCase() && letraCodificada > 'z') {
                letraCodificada -= 26
            } else if (letra.isUpperCase() && letraCodificada > 'Z') {
                letraCodificada -= 26
            }
            contrasenaCodificada += letraCodificada
        } else {
            contrasenaCodificada += letra
        }
    }
    return contrasenaCodificada
}

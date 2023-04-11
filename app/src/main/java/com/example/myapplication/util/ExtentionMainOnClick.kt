package com.example.myapplication.util

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import com.example.myapplication.HomeActivity
import com.example.myapplication.LoginActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.RegistroActivity
import com.google.firebase.auth.FirebaseAuth


fun LoginActivity.onClickLogin() {
    val email = binding.lgUsuario.text.toString().trim()
    val password = binding.lgContrasena.text.toString().trim()


    if (email.isEmpty() || password.isEmpty()) {
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
    if (userBloqued){
        showAlertUserBloq()
        binding.btnIngresar.isEnabled = false
    }

    if (loginAttempts < 3 && !userBloqued) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
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
                    Toast.makeText(this, "Se han superado el número de intentos permitidos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    } else {
        Toast.makeText(this, "Se han superado el número de intentos permitidos. Su usuario ha sido bloqueado", Toast.LENGTH_SHORT).show()
        userBloqued = true
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

private fun LoginActivity.showAlertUserBloq() {

    val builder = AlertDialog.Builder(binding.root.context)
    builder.setTitle("Usuario bloqueado")
    builder.setMessage("Tu usuario se encuentra bloqueado, por favor comunicate con el administrador")
    builder.setPositiveButton("Aceptar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}



package com.example.myapplication.util

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.myapplication.HomeActivity
import com.example.myapplication.LoginActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth


fun LoginActivity.onClickLogin() {
    val email = binding.lgUsuario.text.toString().trim()
    val password = binding.lgContrasena.text.toString().trim()
    val emailUser = intent.getStringExtra("email")


    if (email.isEmpty() || password.isEmpty()) {
        showAlertAddParameters()
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
                val userBloqued = documentSnapshot.getBoolean("userBloqued") ?: false
                if (userBloqued) {
                    showAlertUserBlocked()
                    enviarDatos("Warning", "${getString(R.string.User_bloq)}" )

                    binding.btnIngresar.isEnabled = false
                    binding.myProgressBar.visibility = View.GONE
                    return@addOnSuccessListener
                }
                binding.myProgressBar.visibility = View.VISIBLE
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.putExtra("emailUser", emailUser)
                            val animation = ActivityOptions.makeCustomAnimation(
                                this,
                                R.anim.slide_in_right,
                                R.anim.slide_out_left
                            ).toBundle()
                            startActivity(intent, animation)
                            finish()
                        } else {
                            if (loginAttempts <= 1) {
                                showAlert()
                                enviarDatos("Error", "${getString(R.string.invalid_password)}" )
                                binding.myProgressBar.visibility = View.VISIBLE
                                binding.btnIngresar.isEnabled = false
                            }
                            binding.btnIngresar.isEnabled = true
                            binding.myProgressBar.visibility = View.GONE
                            loginAttempts++
                            if (loginAttempts == 3) {
                                bloquearUsuarioEnFirebase(email)
                                enviarDatos("Warning", "${getString(R.string.User_bloq)}" )
                                showAlertUserBlockedAttempts()
                                binding.btnIngresar.isEnabled = false
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

/*
fun LoginActivity.getColorsBank(){
    getUserData()
    colorFirebase.document(typeBank)
        .get()
        .addOnSuccessListener { document ->
            if (document != null) {
                val Color1 = document.getString("Color1")
                val Color2 = document.getString("Color2")
                val Color3 = document.getString("Color3")
                val Color4 = document.getString("Color4")
            }else {
                Log.d(ContentValues.TAG, "No se encontró ningún documento con el id $typeBank")
            }
        }
}
*/

fun LoginActivity.getUserData() {
    val emailUser = intent.getStringExtra("email")
    if (!emailUser.isNullOrEmpty())
        binding.lgUsuario.text = Editable.Factory.getInstance().newEditable(emailUser)
    else {//..
    }
    if (!emailUser.isNullOrEmpty()) {
        when {
            emailUser.contains("@estado" ) || emailUser.contains("@bancoestado") -> {
                typeBank = "estado"
                binding.nameEnterprise.text = "Banco Estado"
                binding.myProgressBar

                binding.imgprofile.setBackgroundResource(R.drawable.bc_estado)
            }

            emailUser.contains("@bci") -> {
                typeBank = "bci"
                binding.nameEnterprise.text = "Banco BCI"
                binding.imgprofile.setBackgroundResource(R.drawable.ic_bci)
            }

            emailUser.contains("@santander") -> {
                typeBank = "santander"
                binding.nameEnterprise.text = "Banco Santander"
                binding.imgprofile.setBackgroundResource(R.drawable.ic_bci)
            }
            else -> {
                errorWhitUser()
                binding.btnIngresar.isEnabled = false
            }
        }
    }
}

private fun LoginActivity.bloquearUsuarioEnFirebase(email: String) {
    val userIdDocument = userFirebase.document(email)
    val data = hashMapOf("userBloqued" to true)
    userIdDocument.update(data.toMap()).addOnSuccessListener {
        Log.d(TAG, "Usuario bloqueado en Firebase")
    }.addOnFailureListener { exception ->
        Log.e(TAG, "Error al bloquear usuario en Firebase", exception)
    }
}

private fun LoginActivity.showAlert() {

    val builder = AlertDialog.Builder(binding.root.context)
    builder.setTitle("Clave invalida (${loginAttempts + 1}/3)")
    builder.setMessage("Contraseña incorrecta vuelva a intentarlo")
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

private fun LoginActivity.showAlertUserBlockedAttempts() {

    val builder = AlertDialog.Builder(binding.root.context)
    builder.setTitle("Usuario bloqueado (3/3)")
    builder.setMessage("Se han superado el numero de intentos. su usuario se encuentra bloqueado, por favor comunicate con el administrador")
    builder.setPositiveButton("Aceptar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

private fun LoginActivity.errorWhitUser() {

    val builder = AlertDialog.Builder(binding.root.context)
    builder.setTitle("Error de usuario")
    builder.setMessage("El usuario ingresado no es correcto. por favor ingrese uno valido.")
    builder.setPositiveButton("Aceptar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}


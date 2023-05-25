package com.example.myapplication.util

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.example.myapplication.Expand
import com.example.myapplication.LoginActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.RegistroActivity

fun MainActivity.onClickButtonEmail() {
    val emailUser = emailUserVar.trim()
    if (emailUser.isNotEmpty()) {
        userFirebase.document(emailUser).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    dataUserCorrect = true
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("email", emailUser)
                    val animation = ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                    ).toBundle()
                    startActivity(intent, animation)
                } else {
                    showAlertUserNotExist()
                }
            }.addOnFailureListener { exception ->
                Log.e(TAG, "Error al obtener el documento de usuarios en Firebase", exception)
            }
    } else {
        Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show()
    }
}


fun MainActivity.onClickResgister() {
    val intent = Intent(this, RegistroActivity::class.java)
    val animation = ActivityOptions.makeCustomAnimation(
        this,
        R.anim.slide_in_right,
        R.anim.slide_out_left
    ).toBundle()
    startActivity(intent, animation)

}

fun MainActivity.onClickExpand() {
    val intent = Intent(this, Expand::class.java)
    val animation = ActivityOptions.makeCustomAnimation(
        this,
        R.anim.slide_in_right,
        R.anim.slide_out_left
    ).toBundle()
    startActivity(intent, animation)

}

fun MainActivity.getUserData() {
    val emailUser = intent.getStringExtra("email")
    if (!emailUser.isNullOrEmpty()) {
        binding.txtEmail.text = Editable.Factory.getInstance().newEditable(emailUser)
        emailUserVar = binding.txtEmail.text.toString()
    } else {
        emailUserVar = binding.txtEmail.text.toString()
    }
}

fun MainActivity.buttonEnabledOrDisabled() {
    binding.btnIngresarEmail.isEnabled =
        (!binding.txtEmail.text.isEmpty() || !binding.txtEmail.text.isBlank()) && binding.txtEmail.text.contains(
            "@"
        )
}

private fun MainActivity.showAlertUserNotExist() {

    val builder = AlertDialog.Builder(binding.root.context)
    builder.setTitle("Usuario inválido")
    builder.setMessage("El usuario ingresado no existe, por favor intente registrarse")
    builder.setPositiveButton("Aceptar", null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}
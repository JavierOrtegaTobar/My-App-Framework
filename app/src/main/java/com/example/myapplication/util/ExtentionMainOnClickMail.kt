package com.example.myapplication.util

import android.app.ActivityOptions
import android.content.Intent
import android.text.Editable
import android.widget.Toast
import com.example.myapplication.LoginActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.RegistroActivity

fun MainActivity.onClickButtonEmail() {
    getUserData()
    /*validateEmail()
    if (dataUserCorrect) {*/
    val emailUser = email
    val intent = Intent(this, LoginActivity::class.java)
    intent.putExtra("email", emailUser)
    val animation = ActivityOptions.makeCustomAnimation(
        this,
        R.anim.slide_in_right,
        R.anim.slide_out_left
    ).toBundle()
    startActivity(intent, animation)
    /*} else {
        Toast.makeText(
            this,
            "El correo ingresado no existe",
            Toast.LENGTH_SHORT
        ).show()
    }*/

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

fun MainActivity.getUserData() {
    val emailUser = intent.getStringExtra("email")
    if (!emailUser.isNullOrEmpty()) {
        binding.txtEmail.text = Editable.Factory.getInstance().newEditable(emailUser)
        email = binding.txtEmail.text.toString()

        btnOrderEnabled = true
    } else {
        email = binding.txtEmail.text.toString()
        btnOrderEnabled = true
    }
}

fun MainActivity.buttonEnabledOrDisabled() {
    binding.btnIngresarEmail.isEnabled =
        (!binding.txtEmail.text.isEmpty() || !binding.txtEmail.text.isBlank()) && binding.txtEmail.text.contains(
            "@"
        )
}

/*
fun MainActivity.validateEmail() {
    val userRef = userFirebase.document(email)
    userRef.get().addOnSuccessListener { documentSnapshot ->
        if (documentSnapshot.exists()) {
            val emailUser = documentSnapshot.getString("email")
            if (emailUser == email) {
                buttonEnabledOrDisabled()
                dataUserCorrect = true
            } else {
                binding.btnIngresarEmail.isEnabled = false
                dataUserCorrect = false
            }
        }
    }
}*/

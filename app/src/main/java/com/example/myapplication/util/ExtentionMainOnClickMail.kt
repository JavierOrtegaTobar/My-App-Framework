package com.example.myapplication.util

import android.app.ActivityOptions
import android.content.Intent
import android.text.Editable
import com.example.myapplication.LoginActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.RegistroActivity

fun MainActivity.onClickButtonEmail() {
    getUserData()
    val emailUser = email
    val intent = Intent(this, LoginActivity::class.java)
    intent.putExtra("email", emailUser)
    val animation = ActivityOptions.makeCustomAnimation(
        this,
        R.anim.slide_in_right,
        R.anim.slide_out_left
    ).toBundle()
    startActivity(intent, animation)
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
    } else {
        email = binding.txtEmail.text.toString()
    }
}
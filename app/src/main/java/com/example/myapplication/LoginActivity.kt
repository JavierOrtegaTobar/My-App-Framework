package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.util.getUserData
import com.example.myapplication.util.onClickLogin
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityLoginBinding
    lateinit var typeBank: String

    var loginAttempts = 0
    val db = Firebase.firestore
    val userFirebase = db.collection("usuarios")
    val colorFirebase = db.collection("Color")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //getColorsBank()
        getUserData()
        binding.btnIngresar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnIngresar -> onClickLogin()
        }
    }

}
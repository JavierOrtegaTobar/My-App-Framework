package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.util.buttonEnabledOrDisabled
import com.example.myapplication.util.getUserData
import com.example.myapplication.util.onClickButtonEmail
import com.example.myapplication.util.onClickResgister
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityMainBinding
    var text = 3
    var emailUserVar: String = ""
    val db = Firebase.firestore
    val userFirebase = db.collection("usuarios")
    var dataUserCorrect: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // splash
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttonEnabledOrDisabled()
        binding.txtEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                emailUserVar = binding.txtEmail.text.toString()
                buttonEnabledOrDisabled()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // No se usa
            }
        })
        binding.btnRegister.setOnClickListener(this)
        binding.btnIngresarEmail.setOnClickListener(this)

        getUserData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnIngresarEmail -> onClickButtonEmail()
            R.id.btnRegister -> onClickResgister()
        }
    }
}



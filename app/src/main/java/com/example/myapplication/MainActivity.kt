package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.View.OnClickListener
import android.view.View.getDefaultSize
import android.widget.Button
import android.widget.EditText
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.util.getUserData
import com.example.myapplication.util.onClickButtonEmail
import com.example.myapplication.util.onClickLogin
import com.example.myapplication.util.onClickResgister
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityMainBinding
    var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // splash
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUserData()
        binding.btnRegister.setOnClickListener(this)
        binding.btnIngresarEmail.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnIngresarEmail -> onClickButtonEmail()
            R.id.btnRegister -> onClickResgister()
        }
    }
}



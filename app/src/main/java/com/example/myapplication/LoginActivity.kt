package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.marginBottom
import androidx.core.view.marginStart
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.util.onClickLogin
import com.example.myapplication.util.onClickResgister
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityLoginBinding
    var loginAttempts = 0
    val db = Firebase.firestore
    val userFirebase = db.collection("usuarios")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUserData()
        binding.btnIngresar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnIngresar -> onClickLogin()
        }
    }

    fun getUserData() {
        val emailUser = intent.getStringExtra("email")
        if (!emailUser.isNullOrEmpty())
            binding.lgUsuario.text = Editable.Factory.getInstance().newEditable(emailUser)
        else {//..
        }
        if (!emailUser.isNullOrEmpty()) {
            when {
                emailUser.contains("@estado") -> {
                    binding.textView.text = "Bienvenido Usuario de banco estado"
                }

                emailUser.contains("@bci") -> {
                    binding.textView.text = "Bienvenido Usuario de Banco BCI"
                    binding.imgprofile.setBackgroundResource(R.drawable.logo_bci)
                    binding.imgprofile.layoutParams.width = 500
                    binding.imgprofile.layoutParams.height = 400
                }

                emailUser.contains("@santander") -> {
                    binding.textView.text = "Bienvenido Usuario de Banco Santander"
                }

                else -> {

                }
            }
        }
    }

}
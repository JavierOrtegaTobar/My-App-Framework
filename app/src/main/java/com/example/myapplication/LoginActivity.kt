package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes.Margins
import android.text.Editable
import android.view.View
import android.view.View.OnClickListener
import android.view.View.TEXT_ALIGNMENT_CENTER
import androidx.core.view.marginBottom
import androidx.core.view.marginRight
import androidx.core.view.marginStart
import com.example.myapplication.databinding.ActivityLoginBinding
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
                emailUser.contains("@estado" ) || emailUser.contains("@bancoestado") -> {
                    binding.nameEnterprise.text = "Banco Estado"
                }

                emailUser.contains("@bci") -> {
                    binding.nameEnterprise.text = "Banco BCI"
                    binding.imgprofile.setBackgroundResource(R.drawable.ic_bci)
                }

                emailUser.contains("@santander") -> {
                    binding.nameEnterprise.text = "Banco Santander"
                }

                else -> {

                }
            }
        }
    }

}
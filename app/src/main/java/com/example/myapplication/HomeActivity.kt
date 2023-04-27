package com.example.myapplication

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.databinding.ActivityRegistroBinding
import com.example.myapplication.util.bankUser
import com.example.myapplication.util.menuDependingBank
import com.example.myapplication.util.typeUser
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {


    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var recyclerView: RecyclerView
    lateinit var menu: Menu
    lateinit var idDocumento: String
    lateinit var binding: ActivityHomeBinding
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        recyclerView = findViewById(R.id.rvMenuComponent)
        recyclerView.layoutManager = LinearLayoutManager(this)
        typeUser()
        bankUser()
        menuDependingBank()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_burguer, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionProfile -> {
                if (binding.rvComponentMenu.root.isVisible) {
                    binding.rvComponentMenu.root.visibility = View.GONE
                } else {
                    binding.rvComponentMenu.root.visibility = View.VISIBLE
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


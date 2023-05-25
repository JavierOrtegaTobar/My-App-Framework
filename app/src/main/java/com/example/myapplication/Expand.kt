package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityExpandBinding
import com.example.myapplication.util.expandirTexto

class Expand : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityExpandBinding
    private lateinit var linearLayout: LinearLayout
    var isExpanded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        linearLayout = binding.linearLayout8
        binding.up.setOnClickListener(this)
        binding.down.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.up -> {
                isExpanded = false
                expandirTexto(linearLayout)
            }
            R.id.down -> {
                isExpanded = true
                expandirTexto(linearLayout)
            }
        }
    }
}

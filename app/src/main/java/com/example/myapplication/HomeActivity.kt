package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.databinding.ActivityRegistroBinding

class HomeActivity : AppCompatActivity() {


    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var recyclerView: RecyclerView
    lateinit var menu: Menu
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar)


        recyclerView = findViewById(R.id.rvMenuComponent)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Crea una lista de elementos para mostrar en el RecyclerView
        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        // Crea un adaptador para el RecyclerView

        val adapter = MyAdapter(items)
        recyclerView.adapter = adapter
        binding.rvComponentMenu.root.visibility = View.GONE

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Infla el menu de opciones en la Toolbar
        menuInflater.inflate(R.menu.menu_burguer, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionProfile -> {

                if(binding.rvComponentMenu.root.isVisible){
                    binding.rvComponentMenu.root.visibility = View.GONE
                }else {
                    binding.rvComponentMenu.root.visibility = View.VISIBLE
                }
                // Agrega el código aquí para manejar el clic en el elemento de menú de búsqueda
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}

class MyAdapter(private val items: List<String>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_menu_options, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textMenu)
    }
}

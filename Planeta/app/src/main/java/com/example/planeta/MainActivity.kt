package com.example.planeta

import adaptador.PlanetAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import modelo.PlanetaData

class MainActivity : AppCompatActivity() {
    val planets = PlanetaData.getPlanetas()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val  recyclerView: RecyclerView = findViewById(R.id.rvPlanetaa)
        recyclerView.layoutManager =  LinearLayoutManager(this)
        val planetAdapter= PlanetAdapter(planets)
        recyclerView.adapter = planetAdapter
    }
}
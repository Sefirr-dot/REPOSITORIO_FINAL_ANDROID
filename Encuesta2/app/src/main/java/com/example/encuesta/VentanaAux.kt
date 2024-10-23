package com.example.encuesta

import adaptador.EstudianteAdaptador
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.encuesta.databinding.ActivityVentanaAuxBinding
import modelo.Almacen

class VentanaAux : AppCompatActivity() {
    lateinit var binding: ActivityVentanaAuxBinding
    val estudiante = Almacen.getEstudiante()
    private val SECOND_ACTIVITYREQUEST_CODE = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVentanaAuxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btVolver.setOnClickListener{
            finish()
        }

        val  recyclerView: RecyclerView = findViewById(R.id.rvEstudiantes)
        recyclerView.layoutManager =  LinearLayoutManager(this)
        val planetAdapter= EstudianteAdaptador(estudiante)
        recyclerView.adapter = planetAdapter

    }
}
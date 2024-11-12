package com.example.encuesta

import EstudianteAdaptador
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.encuesta.R
import com.example.encuesta.databinding.ActivityVentanaAuxBinding
import modelo.Almacen

class VentanaAux : AppCompatActivity() {
    lateinit var binding: ActivityVentanaAuxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVentanaAuxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Agregar padding para los sistemas con barras de estado
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botón de Volver
        binding.btVolver.setOnClickListener {
            finish()
        }

        // Obtener lista de estudiantes desde el almacen
        val estudiantes = Almacen.getEstudiante()

        // Verificar si la lista de estudiantes está vacía o no
        Log.d("Estudiantes", "Número de estudiantes: ${estudiantes.size}")
        if (estudiantes.isEmpty()) {
            Log.d("Estudiantes", "La lista de estudiantes está vacía")
        } else {
            Log.d("Estudiantes", "Estudiantes obtenidos: ${estudiantes.joinToString(", ") { it.nombre }}")
        }

        // Configuración del RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.rvEstudiantes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Pasar la lista de estudiantes al adaptador
        val planetAdapter = EstudianteAdaptador(estudiantes)
        recyclerView.adapter = planetAdapter
    }
}
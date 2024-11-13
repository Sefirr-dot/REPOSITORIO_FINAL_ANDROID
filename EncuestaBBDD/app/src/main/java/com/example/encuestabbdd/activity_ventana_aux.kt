package com.example.encuestabbdd


import adaptador.EstudianteAdaptador
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.encuestabbdd.databinding.ActivityVentanaAuxBinding
import modelo.Almacen

class activity_ventana_aux : AppCompatActivity() {
    private lateinit var binding: ActivityVentanaAuxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVentanaAuxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply window insets padding
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up the "Back" button
        binding.btVolver.setOnClickListener {
            Almacen.eliminarTodosLosEstudiantes()
            finish()
        }

        // Retrieve list of students from storage
        val estudiantes = Almacen.getEstudiante()
        Log.d("Estudiantes", "Número de estudiantes: ${estudiantes.size}")

        if (estudiantes.isEmpty()) {
            Log.d("Estudiantes", "La lista de estudiantes está vacía")
        } else {
            Log.d("Estudiantes", "Estudiantes obtenidos: ${estudiantes.joinToString(", ") { it.nombre }}")
        }

        // Set up RecyclerView with adapter
        binding.rvEstudiantes.layoutManager = LinearLayoutManager(this)
        val studentAdapter = EstudianteAdaptador(estudiantes)
        binding.rvEstudiantes.adapter = studentAdapter
    }
}
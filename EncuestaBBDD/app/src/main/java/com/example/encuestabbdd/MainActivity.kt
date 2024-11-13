package com.example.encuestabbdd

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import Auxiliar.Conexion
import com.example.encuestabbdd.databinding.ActivityMainBinding
import modelo.Almacen
import modelo.Estudiante

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply edge-to-edge window insets padding
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toggle anonymous name
        binding.swAnonimo.setOnCheckedChangeListener { _, isChecked ->
            binding.etNoombre.isEnabled = !isChecked
            if (isChecked) {
                binding.etNoombre.text = null
            }
        }


        // Configure SeekBar for study hours
        val labels = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        binding.skHorasEstudio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val labelIndex = (progress / 10).coerceIn(0, labels.size - 1)
                binding.txtProgresoSK.text = labels[labelIndex]
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Validate and save the student
        binding.btValidar.setOnClickListener {
            // Collect selected specializations
            val especialidadess = mutableListOf<String>().apply {
                if (binding.cbDAM.isChecked) add("DAM")
                if (binding.cbASIR.isChecked) add("ASIR")
                if (binding.cnDAW.isChecked) add("DAW")
            }

            // Collect preferred OS
            val sistemaFav = when {
                binding.rbMac.isChecked -> "Mac"
                binding.rbWindows.isChecked -> "Windows"
                binding.rbLinux.isChecked -> "Linux"
                else -> ""
            }

            // Check required fields before saving
            val nombre = binding.etNoombre.text.toString()
            val horasEstudio = binding.txtProgresoSK.text.toString().toIntOrNull() ?: 0

            if (nombre.isEmpty() && !binding.swAnonimo.isChecked) {
                Toast.makeText(this, "Por favor, ingrese el nombre o active el modo anónimo.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (sistemaFav.isEmpty()) {
                Toast.makeText(this, "Seleccione un sistema operativo favorito.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create Estudiante object and save to database
            val estudiante = Estudiante(nombre, sistemaFav, especialidadess, horasEstudio)
            val id = Conexion.addPersona(this, estudiante)
            var estudiantes = Conexion.obtenerPersonas(this)
            for (estudiante in estudiantes) {
                Almacen.agregarEstudiante(estudiante)
            }

            if (id != -1L) {
                Toast.makeText(this, "Estudiante guardado con éxito", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, activity_ventana_aux::class.java)
                intent.putExtra("ESTUDIANTE", estudiante)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error al guardar el estudiante", Toast.LENGTH_SHORT).show()
            }
        }

        // Reset the form
        binding.btReiniciar.setOnClickListener {
            binding.etNoombre.text.clear()
            binding.radioGroup.clearCheck()
            binding.cbASIR.isChecked = false
            binding.cbDAM.isChecked = false
            binding.cnDAW.isChecked = false
            binding.skHorasEstudio.progress = 0
            binding.txtProgresoSK.text = "1"

        }

    }
}

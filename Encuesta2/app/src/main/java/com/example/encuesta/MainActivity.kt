package com.example.encuesta

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.encuesta.databinding.ActivityMainBinding
import Auxiliar.Conexion  // Importamos la clase Conexion

import modelo.Estudiante

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración del switch para nombre anónimo
        binding.swAnonimo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.etNoombre.isEnabled = false
                binding.etNoombre.text = null
            } else {
                binding.etNoombre.isEnabled = true
            }
        }

        // Configuración del SeekBar
        val labels = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        binding.skHorasEstudio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val labelIndex = (progress / 10).coerceIn(0, labels.size - 1)
                binding.txtProgresoSK.text = labels[labelIndex]
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Botón de Validar
        binding.btValidar.setOnClickListener {
            val especialidades = ArrayList<String>()
            var sistemaFav = ""

            // Obtener especialidades seleccionadas
            if (binding.cbDAM.isChecked) especialidades.add("DAM")
            if (binding.cbASIR.isChecked) especialidades.add("ASIR")
            if (binding.cnDAW.isChecked) especialidades.add("DAW")

            // Obtener sistema operativo favorito
            sistemaFav = when {
                binding.rbMac.isChecked -> "Mac"
                binding.rbWindows.isChecked -> "Windows"
                binding.rbLinux.isChecked -> "Linux"
                else -> ""
            }

            // Crear objeto Estudiante
            val estudiante = Estudiante(
                nombre = binding.etNoombre.text.toString(),
                sistemaOperativo = sistemaFav,
                especialidad = especialidades,
                horasEstudio = binding.txtProgresoSK.text.toString().toInt()
            )

            // Insertar estudiante en la base de datos
            val id = Conexion.addPersona(this, estudiante)
            if (id != -1L) {
                Toast.makeText(this, "Estudiante guardado con éxito", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, VentanaAux::class.java)
                intent.putExtra("ESTUDIANTE", estudiante)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error al guardar el estudiante", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón de Reiniciar
        binding.btReiniciar.setOnClickListener {
            binding.etNoombre.text.clear()
            binding.radioGroup.clearCheck()
            binding.cbASIR.isChecked = false
            binding.cbDAM.isChecked = false
            binding.cnDAW.isChecked = false
            binding.skHorasEstudio.progress = 0
            binding.txtProgresoSK.text = "1"
            binding.txtLista.text = ""
        }

        // Botón de Cantidad de Estudiantes
        binding.btCuantas.setOnClickListener {
            val numEstudiantes = Conexion.obtenerPersonas(this).size
            Toast.makeText(this, "Número de estudiantes: $numEstudiantes", Toast.LENGTH_SHORT).show()
        }

        // Botón de Resumen de Estudiantes
        binding.btResumen.setOnClickListener {
            val estudiantes = Conexion.obtenerPersonas(this)
            val resumen = estudiantes.joinToString(separator = "\n") { it.toString() }
            binding.txtLista.text = resumen
        }
    }
}

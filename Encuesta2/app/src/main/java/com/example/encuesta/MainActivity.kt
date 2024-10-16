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
import androidx.transition.Visibility
import com.example.encuesta.databinding.ActivityMainBinding
import modelo.Almacen
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


        binding.swAnonimo.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.etNoombre.isEnabled = false
                binding.etNoombre.text=null
            } else {
                binding.etNoombre.isEnabled = true


            }
        }
            val labels = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

            binding.skHorasEstudio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    val labelIndex = (progress / 10).coerceIn(0, labels.size - 1)
                    binding.txtProgresoSK.text = labels[labelIndex]
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

        binding.btValidar.setOnClickListener{
            var especiliadades: ArrayList<String> = ArrayList()
            var sistemaFav = ""
            if (binding.cbDAM.isChecked) {
                especiliadades.add("DAM")
            }
            if (binding.cbASIR.isChecked) {
                especiliadades.add("ASIR")
            }
            if (binding.cnDAW.isChecked) {
                especiliadades.add("DAW")
            }
            if(binding.rbMac.isChecked){
                sistemaFav="Mac"
            }
            if(binding.rbWindows.isChecked){
                sistemaFav="Windows"
            }
            if(binding.rbLinux.isChecked) {
                sistemaFav = "Linux"
            }
            var estu = Estudiante(binding.etNoombre.text.toString(),sistemaFav,especiliadades,binding.txtProgresoSK.text.toString().toInt())

            Almacen.addEstudiante(estu)
            val intent: Intent = Intent(this, VentanaAux::class.java)
            intent.putExtra("estudiante", estu)
            startActivity(intent)
        }
        binding.btReiniciar.setOnClickListener{
            binding.etNoombre.text.clear()
            binding.radioGroup.clearCheck()
            binding.cbASIR.isChecked = false
            binding.cbDAM.isChecked = false
            binding.cnDAW.isChecked = false
            binding.skHorasEstudio.progress = 0
            binding.txtProgresoSK.text = "1"
            Almacen.clearlistaEstudiantes()
            binding.txtLista.text = ""
        }
        binding.btCuantas.setOnClickListener{
            Toast.makeText(this, "Numero de estudiantes: " + Almacen.getEstudiante().size, Toast.LENGTH_SHORT).show()
        }

        binding.btResumen.setOnClickListener{
            var auxiliar = ""
            for (Almacen in Almacen.listaEstudiantes) {
                auxiliar += Almacen.toString()+"\n"
            }
            binding.txtLista.text = auxiliar

        }


}}
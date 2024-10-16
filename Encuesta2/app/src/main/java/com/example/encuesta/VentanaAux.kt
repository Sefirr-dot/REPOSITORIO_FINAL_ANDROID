package com.example.encuesta

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.encuesta.databinding.ActivityMainBinding
import com.example.encuesta.databinding.ActivityVentanaAuxBinding
import modelo.Estudiante

class VentanaAux : AppCompatActivity() {
    lateinit var binding: ActivityVentanaAuxBinding
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
        val estudiante = intent.getSerializableExtra("estudiante") as Estudiante
        if(estudiante != null ){
            binding.txtNom.text = estudiante.nombre
            binding.txtSists.text = estudiante.sistemaOperativo
            var auxEspe = ""
            for(esp in estudiante.especialidad){
                auxEspe+=" "+esp
            }
            binding.txtEspee.text = auxEspe
            binding.txtHours.text = estudiante.horasEstudio.toString()
        }

    }
}
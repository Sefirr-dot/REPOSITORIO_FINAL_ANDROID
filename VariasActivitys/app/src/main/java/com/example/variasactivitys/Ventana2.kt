package com.example.variasactivitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.variasactivitys.databinding.ActivityMainBinding
import com.example.variasactivitys.databinding.ActivityVentana2Binding
import modelo.Persona

class Ventana2 : AppCompatActivity() {
    lateinit var binding: ActivityVentana2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVentana2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        //con Intent y pares clave valor
//        var nom = intent.getStringExtra("nombre1")
//        var eda = intent.getStringExtra("edad1")
//
//        val p: Persona = intent.getSerializableExtra("obj") as Persona
//        binding.tvVolver.text = p.toString()

//        val bundle = intent.getBundleExtra("objeto")
//        val nom = bundle!!.getString("nombre1")
//        val eda = bundle!!.getString("edad1")
//        val p: Persona = bundle!!.getSerializable("persona") as Persona

//        binding.tvVolver.text = p.toString()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btVolver.setOnClickListener {
            finish()
        }

        binding.btVolverVentana1.setOnClickListener{
            val stringToPassBack = binding.etValorADevolver.text.toString()

            val intent = Intent()
            intent.putExtra("keyName", stringToPassBack)
            setResult(Activity.RESULT_OK, intent)
            finish()

        }

        binding.btVolverVentana12.setOnClickListener{
            val stringToPassBack = binding.etValorADevolver.text.toString()

            val intent = Intent()
            intent.putExtra("keyName", stringToPassBack)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
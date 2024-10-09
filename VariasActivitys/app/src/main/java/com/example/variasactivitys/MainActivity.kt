package com.example.variasactivitys

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.variasactivitys.databinding.ActivityMainBinding
import modelo.Persona

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btPasar.setOnClickListener {
            //con intent clave - valor

            val intent1: Intent = Intent(this, Ventana2::class.java)
//            intent1.putExtra("nombre1",binding.etNombre.text.toString())
//            intent1.putExtra("edad1",binding.etEdad.text.toString())
//
//
            var pe = Persona(binding.etNombre.text.toString(),binding.etEdad.text.toString().toInt())
//            intent1.putExtra("obj",pe)
//            startActivity(intent1)
//
            val bundle = Bundle()
            bundle.putString("nombre1",binding.etNombre.text.toString())
            bundle.putString("edad1",binding.etEdad.text.toString())
            bundle.putSerializable("persona",pe)
            intent1.putExtra("objeto",bundle)
            startActivity(intent1)


        }
    }
}
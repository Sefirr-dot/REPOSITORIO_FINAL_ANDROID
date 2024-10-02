package com.example.calculadora

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btSuma.setOnClickListener { suma() }
        binding.btResta.setOnClickListener { resta() }
        binding.btMultiplicacion.setOnClickListener { multiplicacion() }
        binding.btDivision.setOnClickListener {
            if(binding.etxtn2.text.toString().toDouble() == 0.0 || binding.etxtn1.text.toString().isEmpty() || binding.etxtn2.text.toString().isEmpty()){
                Toast.makeText(this, "No se puede dividir entre 0", Toast.LENGTH_SHORT).show()
            } else{
                division()
            }
             }


    }
    fun suma(){
        val num1 = binding.etxtn1.text.toString().toDouble()
        val num2 = binding.etxtn2.text.toString().toDouble()
        val resultado = num1 + num2
        binding.txtResultado.text = resultado.toString()
    }
    fun resta(){
        val num1 = binding.etxtn1.text.toString().toDouble()
        val num2 = binding.etxtn2.text.toString().toDouble()
        val resultado = num1 - num2
        binding.txtResultado.text = resultado.toString()
    }
    fun multiplicacion(){
        val num1 = binding.etxtn1.text.toString().toDouble()
        val num2 = binding.etxtn2.text.toString().toDouble()
        val resultado = num1 * num2
        binding.txtResultado.text = resultado.toString()
    }
    fun division(){
        val num1 = binding.etxtn1.text.toString().toDouble()
        val num2 = binding.etxtn2.text.toString().toDouble()
        val resultado = num1 / num2
        binding.txtResultado.text = resultado.toString()
        binding.txtModuloDivision.text = (num1 % num2).toString()
    }
}
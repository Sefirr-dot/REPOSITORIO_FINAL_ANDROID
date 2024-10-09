package com.example.imagenesyseekbar

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imagenesyseekbar.databinding.ActivityMainBinding
import modelo.PedidoPizzeria

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val image1 = R.drawable.pizzita1
        val image2 = R.drawable.ic_comida_foreground
        var currentImage = R.drawable.pizzita1

        val miTag = "Mi Logg Maggg"

        binding.btAceptar.setOnClickListener {

            if(binding.swLicencia.isChecked){
                var mensaje = "Hola ${binding.ptNombre.text} has pedido una Pizza de "
                if(binding.cbBacon.isChecked){
                    mensaje += "Bacon "
                }
                if(binding.cbCebolla.isChecked){
                    mensaje += "Cebolla "
                }
                if(binding.cbQueso.isChecked) {
                    mensaje += "Queso "
                }
                if(!binding.cbBacon.isChecked && !binding.cbQueso.isChecked && !binding.cbCebolla.isChecked){
                    mensaje += "Margarita "

                }
                if (binding.rbBordeFino.isChecked){
                    mensaje += "con Borde Fino"
                    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                }else{
                    mensaje += "con Borde Pan"
                    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, "Acepta la Licencia", Toast.LENGTH_SHORT).show()
            }

            val order = PedidoPizzeria(binding.ptNombre.text.toString(),binding.cbQueso.isChecked,binding.cbBacon.isChecked,binding.cbCebolla.isChecked,binding.rbBordeFino.isChecked, binding.rdBordePan.isChecked, binding.seekBar1.progress)
            Log.i(miTag, order.toString())
        }
        binding.btBorrar.setOnClickListener {
            binding.ptNombre.text.clear()
            binding.cbBacon.isChecked = false
            binding.cbCebolla.isChecked = false
            binding.cbQueso.isChecked = false
            binding.rbBordeFino.isChecked = true
            binding.swLicencia.isChecked = false
            binding.seekBar1.progress = 0
        }

        binding.imgBoton.setOnClickListener {
            if(currentImage == image1){
                binding.imgPizza.setImageResource(image2)
                currentImage = image2
            }else{
                binding.imgPizza.setImageResource(image1)
                currentImage = image1
            }
        }
        binding.seekBar1.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(miTag, "Progress: $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.i(miTag, "Start ${binding.seekBar1.progress}")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.i(miTag, "Start ${binding.seekBar1.progress}")
            }

        })
    }

}
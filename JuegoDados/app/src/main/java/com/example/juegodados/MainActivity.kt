package com.example.juegodados

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.juegodados.databinding.ActivityMainBinding

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
        var contadorPuntosJ1 = 0
        var contadorPuntosJ2 = 0
        var contadorTiradasJ1 = 0
        var contadorTiradasJ2 = 0
        var contadorGanadasJ1 = 0
        var contadorGanadasJ2 = 0
        var salir = false
        val dado1 = R.drawable.cara1
        val dado2 = R.drawable.cara2
        val dado3 = R.drawable.cara3
        val dado4 = R.drawable.cara4
        val dado5 = R.drawable.cara5
        val dado6 = R.drawable.cara6
        binding.btReiniciar.visibility = View.INVISIBLE

        binding.btJ1.setOnClickListener {
            var dado = (1..6).random()
            if(contadorTiradasJ1!=5){
                if(dado == 1){
                    binding.imgDados1.setImageResource(dado1)
                    contadorPuntosJ1+=1
                } else if(dado == 2){
                    binding.imgDados1.setImageResource(dado2)
                    contadorPuntosJ1+=2
                } else if(dado == 3){
                    binding.imgDados1.setImageResource(dado3)
                    contadorPuntosJ1+=3
                } else if(dado == 4){
                    binding.imgDados1.setImageResource(dado4)
                    contadorPuntosJ1+=4
                } else if(dado == 5){
                    binding.imgDados1.setImageResource(dado5)
                    contadorPuntosJ1+=5
                } else if(dado == 6){
                    binding.imgDados1.setImageResource(dado6)
                    contadorPuntosJ1+=6
                }
                contadorTiradasJ1++
                binding.txtContadorPuntosJ1.text = contadorPuntosJ1.toString()
                binding.txtContadorTiradasJ1.text = contadorTiradasJ1.toString()

            } else {
                Toast.makeText(this, "El jugador 1 ya ha hecho las 5 tiradas", Toast.LENGTH_SHORT).show()
            }
            if((contadorTiradasJ1==5 && contadorTiradasJ2==5)&& !salir){
                binding.btReiniciar.visibility = View.VISIBLE
                salir = true
                if(contadorPuntosJ1>contadorPuntosJ2){
                    contadorGanadasJ1++
                } else  {
                    contadorGanadasJ2++
                }

                binding.txtContadorGanadasJ1.text = contadorGanadasJ1.toString()
                binding.txtContadorGanadasJ2.text = contadorGanadasJ2.toString()
            }

        }

        binding.btJ2.setOnClickListener{
            var dado = (1..6).random()
            if(contadorTiradasJ2!=5){
                if(dado == 1){
                    binding.imgDados2.setImageResource(dado1)
                    contadorPuntosJ2+=1
                } else if(dado == 2){
                    binding.imgDados2.setImageResource(dado2)
                    contadorPuntosJ2+=2
                } else if(dado == 3){
                    binding.imgDados2.setImageResource(dado3)
                    contadorPuntosJ2+=3
                } else if(dado == 4){
                    binding.imgDados2.setImageResource(dado4)
                    contadorPuntosJ2+=4
                } else if(dado == 5){
                    binding.imgDados2.setImageResource(dado5)
                    contadorPuntosJ2+=5
                } else if(dado == 6){
                    binding.imgDados2.setImageResource(dado6)
                    contadorPuntosJ2+=6
                }
                contadorTiradasJ2++
                binding.txtContadorPuntosJ2.text = contadorPuntosJ2.toString()
                binding.txtContadorTiradasJ2.text = contadorTiradasJ2.toString()

            }else {
                Toast.makeText(this, "El jugador 2 ya ha hecho las 5 tiradas", Toast.LENGTH_SHORT).show()
            }

            if((contadorTiradasJ1==5 && contadorTiradasJ2==5) && !salir){
                binding.btReiniciar.visibility = View.VISIBLE
                salir = true
                if(contadorPuntosJ1>contadorPuntosJ2){
                    contadorGanadasJ1++
                } else  {
                    contadorGanadasJ2++
                }
                binding.txtContadorGanadasJ1.text = contadorGanadasJ1.toString()
                binding.txtContadorGanadasJ2.text = contadorGanadasJ2.toString()

            }
        }
        binding.btReiniciar.setOnClickListener{
            salir = false
            binding.imgDados1.setImageResource(R.drawable.dadosiniciales)
            binding.imgDados2.setImageResource(R.drawable.dadosiniciales)
            contadorPuntosJ1=0
            contadorTiradasJ1=0
            contadorPuntosJ2=0
            contadorTiradasJ2=0
            binding.btReiniciar.visibility = View.INVISIBLE
        }


    }
}
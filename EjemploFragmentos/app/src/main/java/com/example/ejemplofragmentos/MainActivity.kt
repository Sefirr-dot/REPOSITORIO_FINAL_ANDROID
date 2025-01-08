package com.example.ejemplofragmentos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejemplofragmentos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btF1.setOnClickListener {
            val mFragmentManager = supportFragmentManager
            val fragmentTransaction = mFragmentManager.beginTransaction()
            val fragment1 = fragment_fragmento_a()

            val mBundle = Bundle()
            mBundle.putString("variabe1",binding.edCaja.text.toString())
            fragment1.arguments = mBundle
            fragmentTransaction.replace(R.id.miFragmento, fragment1).commit()
        }

        binding.btF2.setOnClickListener {
            val fragmentoB = fragment_fragmento_b()

            val fragmentTransaction =supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.miFragmento, fragmentoB)
            fragmentTransaction.commit()
        }
    }
}
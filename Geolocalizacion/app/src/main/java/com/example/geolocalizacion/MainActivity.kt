package com.example.geolocalizacion

import android.annotation.SuppressLint
import android.content.res.Resources
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.tabsfragments.PageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var tableLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var muteSwitch: Switch
    private lateinit var infoImageView: ImageView // Imagen que al hacer click mostrará el dialog
    private var mediaPlayer: MediaPlayer? = null
    private var isMuted = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tableLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.viewPager)
        muteSwitch = findViewById(R.id.mute_switch) // Asegúrate de agregar este elemento al diseño
        infoImageView = findViewById(R.id.imgInfo) // Asegúrate de tener un ImageView con el ID imgInfo

        // Configurar el ViewPager y el TabLayout
        viewPager.adapter = PageAdapter(this)
        TabLayoutMediator(tableLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Museo del Prado"
                1 -> "Plaza de toros de Sevilla"
                2 -> "La Sagrada Familia"
                3 -> "Corral de Comedias Almagro"
                4 -> "Santiago de Compostela"
                else -> throw Resources.NotFoundException("Position not found")
            }
        }.attach()

        // Inicializar el sonido
        mediaPlayer = MediaPlayer.create(this, R.raw.mp3)

        // Escuchar cambios de fragment
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                playSound()
            }
        })

        // Configurar el Switch para mutear
        muteSwitch.setOnCheckedChangeListener { _, isChecked ->
            isMuted = isChecked
        }

        // Configurar el ImageView (ícono de información)
        infoImageView.setOnClickListener {
            showInfoDialog()
        }
    }

    private fun playSound() {
        if (!isMuted) {
            mediaPlayer?.start()
        }
    }

    private fun showInfoDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Información")
        builder.setMessage("Nombre de la app: GeoLocalización\nVersión: 1.0\nAutor: Tu Nombre")
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setCancelable(true)
        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}

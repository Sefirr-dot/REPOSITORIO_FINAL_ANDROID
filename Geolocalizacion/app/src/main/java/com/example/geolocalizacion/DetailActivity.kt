package com.example.geolocalizacion



import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mymultimediaapp.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Extract data from the Intent
        val placeName = intent.getStringExtra("PLACE_NAME") ?: "Unknown Place"
        val latitude = intent.getDoubleExtra("LATITUDE", 0.0)
        val longitude = intent.getDoubleExtra("LONGITUDE", 0.0)
        val videoUrl = intent.getStringExtra("VIDEO_URL")

        // Display the place name
        findViewById<TextView>(R.id.placeNameTextView).text = placeName

        // TODO: Initialize map and video player
    }
}

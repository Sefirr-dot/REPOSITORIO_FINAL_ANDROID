package com.example.geolocalizacion

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.text
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.geolocalizacion.databinding.ActivityDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private const val LOCATION_REQUEST_CODE = 0

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityDetailBinding
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private lateinit var googleMap: GoogleMap
    private var mediaPlayer: MediaPlayer? = null
    private var videoUri: Uri? = null
    private var isMuted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Extract data from the Intent
        val placeName = intent.getStringExtra("PLACE_NAME") ?: "Unknown Place"
        latitude = intent.getDoubleExtra("LATITUDE", 0.0)
        longitude = intent.getDoubleExtra("LONGITUDE", 0.0)
        val videoResId = intent.getIntExtra("video", 0) // Video resource

        // Display the place name
        binding.placeNameTextView.text = placeName

        // Initialize MapView
        binding.mvMap.onCreate(savedInstanceState)
        binding.mvMap.getMapAsync(this)

        // Handle video button click
        binding.btVideo.setOnClickListener {
            if (videoResId != 0) {
                videoUri = Uri.parse("android.resource://$packageName/$videoResId")
                showVideoDialog(videoUri!!)
            } else {
                Toast.makeText(this, "No video available for this place.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showVideoDialog(videoUri: Uri) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_video_player, null)
        val videoView = dialogView.findViewById<VideoView>(R.id.videoView)
        val closeButton = dialogView.findViewById<ImageView>(R.id.btnClose)
        val playButton = dialogView.findViewById<ImageView>(R.id.btnPlay)
        val stopButton = dialogView.findViewById<ImageView>(R.id.btnStop)
        val muteButton = dialogView.findViewById<ImageView>(R.id.btnMute)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Initialize the video
        videoView.setVideoURI(videoUri)

        // Configure behavior when the video is prepared
        videoView.setOnPreparedListener { mp ->
            mediaPlayer = mp
            videoView.start() // Start playback automatically

        }
        videoView.setOnErrorListener { _, _, _ ->
            Toast.makeText(this, "Error playing video", Toast.LENGTH_SHORT).show()
            false
        }

        // Play/Resume button
        playButton.setOnClickListener {
            mediaPlayer?.let {
                if (!videoView.isPlaying) {
                    videoView.start()

                } else {
                    videoView.pause()

                }
            }
        }

        // Stop/Pause button
        stopButton.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()

            }
        }

        // Mute/Unmute button
        muteButton.setOnClickListener {
            mediaPlayer?.let {
                isMuted = !isMuted
                val volume = if (isMuted) 0f else 1f
                it.setVolume(volume, volume)

            }
        }

        // Close dialog button
        closeButton.setOnClickListener {
            videoView.stopPlayback() // Stop playback
            dialog.dismiss() // Close the dialog
        }

        dialog.setOnDismissListener {
            mediaPlayer?.release()
            mediaPlayer = null
        }

        dialog.show()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val location = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(location).title("Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        enableMyLocation()
    }

    @SuppressLint("MissingPermission")
    fun enableMyLocation() {
        if (!::googleMap.isInitialized) return
        if (isPermissionsGranted()) {
            googleMap.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    fun isPermissionsGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(this, "Go to settings and accept permissions", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mvMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mvMap.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mvMap.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mvMap.onLowMemory()
    }
}
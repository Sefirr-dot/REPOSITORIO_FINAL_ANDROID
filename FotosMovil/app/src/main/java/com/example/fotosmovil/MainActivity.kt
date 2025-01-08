package com.example.fotosmovil

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fotosmovil.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val storage: FirebaseStorage = Firebase.storage
    private lateinit var binding : ActivityMainBinding
    private val cameraRequest = 1888
    private lateinit var bitmap : Bitmap
    var uriImagen : Uri? = null
    val TAG  = "ACSCO"

    //------------------------- Funciones de callback para activities results -------------------------
    //Activity para lanzar la galería de imágenes.
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d(TAG, "Selected URI: $uri")
            this.uriImagen = uri
            binding.img.setImageURI(uri)
            Log.d(TAG, "Cargada")
        } else {
            Log.d(TAG, "No media selected")
        }
    }

    //Segunda activity para lanzar la cámara.
    val openCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data!!
            this.bitmap = data.extras!!.get("data") as Bitmap
            binding.img.setImageBitmap(bitmap)
        }
    }

    //Activity para pedir permisos de cámara.
    val requestCameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //si quisieras vídeo. Poner el punto y ver resto de opciones que ofrece, que prueben alguna.
            //val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            openCamera.launch(intent)
        } else {
            Log.e(TAG,"Permiso de cámara no concedido")
        }
    }


    //-----------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraRequest)

        binding.firebstore.setOnClickListener {
            if (this::bitmap.isInitialized) {
                uploadBitmapToFirebase(bitmap)
            } else if (uriImagen != null) {
                uploadImageToFirebase(uriImagen!!)
            } else {
                Toast.makeText(this, "Please select or capture an image first", Toast.LENGTH_SHORT).show()
            }
        }
        binding.openCamera.setOnClickListener {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        binding.saveToGallery.setOnClickListener {
            saveImageToGallery(binding.txtImagen.text.toString())
        }

        binding.openGallery.setOnClickListener{
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            //si quisieras que se vean imágenes y vídeos de la galería.
            // pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
    }


    //----------------------- Funciones lanzadas por los botones -------------------------

    /**
     * Guardar imagen en la galería. Se guardará con el nombre que pongamos en la editText.
     */
    private fun saveImageToGallery(name: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }

        val resolver = contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            uri?.let {
                resolver.openOutputStream(it)?.use { outputStream ->
                    if (this::bitmap.isInitialized) { // Check if bitmap is initialized
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        uploadBitmapToFirebase(bitmap) // Upload to Firebase Storage
                    } else if (uriImagen != null) {
                        val inputStream = resolver.openInputStream(uriImagen!!)
                        inputStream?.use { input ->
                            outputStream.write(input.readBytes())
                        }
                        uploadImageToFirebase(uriImagen!!) // Upload to Firebase Storage
                    }
                    Toast.makeText(this, "Imagen guardada en la galería", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: IOException) {
            Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Error al guardar la imagen", e)
        }
    }

    private fun uploadBitmapToFirebase(bitmap: Bitmap) {
        val storageRef = storage.reference.child("images/${System.currentTimeMillis()}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        storageRef.putBytes(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Imagen guardada en Firebase Storage", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebase(uri: Uri) {
        val storageRef = storage.reference.child("images/${System.currentTimeMillis()}.jpg")
        storageRef.putFile(uri)
            .addOnSuccessListener {
                Toast.makeText(this, "Imagen de galería subida a Firebase Storage", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al subir la imagen de galería", Toast.LENGTH_SHORT).show()
            }
    }
}
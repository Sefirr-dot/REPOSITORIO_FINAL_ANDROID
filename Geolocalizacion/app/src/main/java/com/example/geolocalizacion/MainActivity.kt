package com.example.geolocalizacion

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.gms.maps.model.PolylineOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnPoiClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {
    private val LOCATION_REQUEST_CODE: Int = 0
    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap

    private lateinit var edLong : EditText
    private lateinit var edLat : EditText

    var alMarcadores = ArrayList<Marker>()

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        edLong = findViewById(R.id.edLongitud)
        edLat = findViewById(R.id.edLatitud)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val location = LatLng(37.7749, -122.4194) // Coordenadas de San Francisco, por ejemplo

        //googleMap.addMarker(MarkerOptions().position(location).title("Marker in San Francisco"))
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))

        map = googleMap
        //Se pueden seleccionar varios tiops de mapas:
        //  None --> no muestra nada, solo los marcadores. (MAP_TYPE_NONE)
        //  Normal --> El mapa por defecto. (MAP_TYPE_NORMAL)
        //  Satélite --> Mapa por satélite.  (MAP_TYPE_SATELLITE)
        //  Híbrido --> Mapa híbrido entre Normal y Satélite. (MAP_TYPE_HYBRID) Muestra satélite y mapas de carretera, ríos, pueblos, etc... asociados.
        //  Terreno --> Mapa de terrenos con datos topográficos. (MAP_TYPE_TERRAIN)
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        map.setOnPoiClickListener(this)
        map.setOnMapLongClickListener (this)
        map.setOnMarkerClickListener(this)

        enableMyLocation() //--> Hanilita, pidiendo permisos, la localización actual.
        createMarker() //--> Nos coloca varios marcadores en el mapa y nos coloca en el CIFP Virgen de Gracia con un Zoom.
        //irubicacioActual() //--> Nos coloca en la ubicación actual directamente. Comenta createMarker par ver esto.
        pintarCirculo()
        pintarRuta()
    }


    //----------------------------------------------------------------------------------------
    @SuppressLint("MissingPermission")
    fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (isPermissionsGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    /**
     * función que usaremos a lo largo de nuestra app para comprobar si el permiso ha sido aceptado o no.
     */
    fun isPermissionsGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    /**
     * Método que solicita los permisos.
     */
    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE)
        }
    }

    //-----------------------------------------------------------------------------------------------------

    /**
     * Método en el que crearemos algunos marcadores de ejemplo.
     */
    private fun createMarker() {

        val coordenadaCorral = LatLng(38.888471397609955, -3.711895286210794)
        val markerCorral = map.addMarker(
            MarkerOptions().position(coordenadaCorral).title("Corral de Comedias de Almagro").icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )
        alMarcadores.add(markerCorral!!)

        val coordenadaMaestre = LatLng(38.991030,-3.920489 )
        /*
        Los markers se crean de una forma muy sencilla, basta con crear una instancia de un objeto LatLng() que recibirá dos
        parámetros, la latitud y la longitud. Yo en este ejemplo he puesto las coordenadas de mi playa favorita.
        */
        //map.addMarker(MarkerOptions().position(markerCIFP).title("Mi CIFP favorito!"))
        //Si queremos cambiar el color del icono, en este caso azul cyan, con un subtexto.
        val markerMaestre = map.addMarker(
            MarkerOptions().position(coordenadaMaestre).title("Mi instituto favorito!").icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).snippet("IES MAESTRE DE CALATRAVA"))
        alMarcadores.add(markerMaestre!!)


        //map.addMarker(MarkerOptions().position(paris).title("Paris").icon(sizeIcon(R.drawable.paris)))
        /*
        Otros atributos útiles:
            alpha(0.4f) --> Con un valor real indica la semitransparencia del icono.
            draggable(true)  --> Permite arrastralo.
            snippet("Otro texto") --> Añade un subtexto al título.
         */
        val markCR = map.addMarker(MarkerOptions().position(coordenadaCorral).title("Corral de comedias").icon(sizeIcon(R.drawable.corral)).alpha(0.8f).draggable(true))
        alMarcadores.add(markCR!!)

        /*
        La función animateCamera() recibirá tres parámetros:

            Un CameraUpdateFactory que a su vez llevará otros dos parámetros, el primero las coordenadas donde queremos hacer zoom
                y el segundo valor (es un float) será la cantidad de zoom que queremos hacer en dichas coordenadas.
            La duración de la animación en milisegundos, por ejemplo 4000 milisegundos son 4 segundos.
            Un listener que no vamos a utilizar, simplemente añadimos null.
         */
        //------------ Zoom hacia un marcador ------------
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadaMaestre, 18f),
            3000,
            null
        )

        //Esto la mueve sin efecto zoom.
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(markerCIFP, 18f))
    }
    /**
     * Con este método vamos a ajustar el tamaño de todos los iconos que usemos en los marcadores.
     */
    fun sizeIcon(idImage:Int): BitmapDescriptor {
        val altura = 60
        val anchura = 60

        var draw = ContextCompat.getDrawable(this,idImage) as BitmapDrawable
        val bitmap = draw.bitmap  //Aquí tenemos la imagen.

        //Le cambiamos el tamaño:
        val smallBitmap = Bitmap.createScaledBitmap(bitmap, anchura, altura, false)
        return BitmapDescriptorFactory.fromBitmap(smallBitmap)

    }



    /**
     * Con el parámetro podremos obtener información del punto de interés. Este evento se lanza cuando pulsamos en un POI.
     */
    override fun onPoiClick(p0: PointOfInterest) {
        Toast.makeText(this@MainActivity, "Pulsado.", Toast.LENGTH_LONG).show()
        val dialogBuilder = AlertDialog.Builder(this@MainActivity)
        dialogBuilder.run {
            setTitle("Información del lugar.")
            setMessage("Id: " + p0!!.placeId + "\nNombre: " + p0!!.name + "\nLatitud: " + p0!!.latLng.latitude.toString() + " \nLongitud: " + p0.latLng.longitude.toString())
            setPositiveButton("Aceptar"){ dialog: DialogInterface, i:Int ->
                Toast.makeText(this@MainActivity, "Salir", Toast.LENGTH_LONG).show()
            }
        }
        dialogBuilder.create().show()
        this.edLong.setText(p0.latLng.longitude.toString())
        this.edLat.setText(p0.latLng.latitude.toString())
    }

    /**
     * Con el parámetro crearemos un marcador nuevo. Este evento se lanzará al hacer un long click en alguna parte del mapa.
     */
    override fun onMapLongClick(p0: LatLng) {
        var marcador = map.addMarker(MarkerOptions().position(p0!!).title("Nuevo marcador"))
        alMarcadores.add(marcador!!)
        this.edLong.setText(p0!!.longitude.toString())
        this.edLat.setText(p0!!.latitude.toString())
        Log.e("ACSCO","Marcador añadido, marcadores actuales: ${alMarcadores.toString()}")
    }

    /**
     * Este evento se lanza cuando hacemos click en un marcador.
     */
    override fun onMarkerClick(p0: Marker): Boolean {
        Toast.makeText(this, "Estás en ${p0!!.title}, ${p0!!.position}", Toast.LENGTH_SHORT).show()
        this.edLong.setText(p0.position.longitude.toString())
        this.edLat.setText(p0.position.latitude.toString())
        p0.remove()  //---> Para borrarlo cuando hago click sobre él solo hay que descomentar esto.
        alMarcadores.removeAt(alMarcadores.indexOf(p0))
        Log.e("ACSCO","Marcador eliminado, marcadores actuales: ${alMarcadores.toString()}")

        return true;
    }

    /**
     * Nos coloca en la ubicación actual.
     */
    @SuppressLint("MissingPermission")
    private fun irUbicacioActual() {
        //COMENTO PQ ESTÁ DANDO ERRORES LA LOCALIZACION AUTOMATICA AL ACCEDER AL GPS DEL MOVIL
        //val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //val miUbicacion = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        //val latLng = LatLng(miUbicacion!!.latitude, miUbicacion.longitude)
        val latLng = LatLng(38.98491, -3.92862)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f)) //--> Mueve la cámara a esa posición, sin efecto. El valor real indica el nivel de Zoom, de menos a más.

    }

    //------------------------------------------------------------------------------------------------------

    /**
     * Dibuja una línea recta desde nuestra ubicación actual al CIFP Virgen de Gracia.
     */
    @SuppressLint("MissingPermission")
    fun pintarRuta() {
        // Coordenadas de los puntos
        ////38.888471397609955, -3.711895286210794
        val markerMaestre = LatLng(38.991030, -3.920489)
        val markerCorral = LatLng(38.888471397609955, -3.711895286210794)

        // Trazado de la línea
        map.addPolyline(PolylineOptions().run {
            add(markerMaestre, markerCorral)
            color(Color.RED) // Color de la línea
            width(8f) // Ancho de la línea
        })

        // Calcular y mostrar la distancia entre los puntos
        val locMaestre = Location("")
        locMaestre.latitude = markerMaestre.latitude
        locMaestre.longitude = markerMaestre.longitude

        val locCorral = Location("")
        locCorral.latitude = markerCorral.latitude
        locCorral.longitude = markerCorral.longitude

        val distanceInMeters = locMaestre.distanceTo(locCorral)
        Log.e("ACSCO", "Distancia entre puntos: $distanceInMeters metros")
    }


    /**
     * Dibuja una línea recta desde nuestra ubicación actual al CIFP Virgen de Gracia.
     */
    fun pintarCirculo(){
        val markerInstituto = LatLng(38.991030,-3.920489)

        map.addCircle(CircleOptions().run{
            center(markerInstituto)
            radius(9.0)
            strokeColor(Color.BLUE)
            fillColor(Color.GREEN)
        })
    }

    override fun onMyLocationButtonClick(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMyLocationClick(p0: Location) {
        TODO("Not yet implemented")
    }
}
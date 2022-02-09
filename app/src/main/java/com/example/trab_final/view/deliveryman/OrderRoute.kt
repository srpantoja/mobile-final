package com.example.trab_final.view.deliveryman

import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.trab_final.R
import com.example.trab_final.databinding.ActivityOrderRouteBinding
import com.example.trab_final.models.GoogleMapDTO
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class OrderRoute : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityOrderRouteBinding
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private lateinit var geocoder : Geocoder
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderRouteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }

    private fun getLocation(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
        val task = fusedLocationProvider.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null){
                currentLocation = location
                val mapFragment = (supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment?)!!
                mapFragment.getMapAsync(this)
                geocoder = Geocoder(this)
            }
        }
    }

    fun getDirectionURL(origin:LatLng,dest:LatLng) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${dest.latitude},${dest.longitude}&destination=${origin.latitude},${origin.longitude}&sensor=false&mode=driving&key=${getString(R.string.google_maps_key)}"
    }

    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()
            Log.d("GoogleMap" , " data : $data")
            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)

                val path =  ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
                    val startLatLng = LatLng(respObj.routes[0].legs[0].steps[i].start_location.lat.toDouble()
                            ,respObj.routes[0].legs[0].steps[i].start_location.lng.toDouble())
                    path.add(startLatLng)
                    val endLatLng = LatLng(respObj.routes[0].legs[0].steps[i].end_location.lat.toDouble()
                            ,respObj.routes[0].legs[0].steps[i].end_location.lng.toDouble())
                    path.add(endLatLng)
                }
                result.add(path)
            }catch (e:Exception){
                Log.d("GoogleMap" , " error : $e")

                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
            }
            mMap.addPolyline(lineoption)
        }
    }



    override fun onMapReady(googleMap: GoogleMap) {
        val orderAddress = LatLng(currentLocation.latitude, currentLocation.longitude)
        val street = intent.getStringExtra("street")
        val district = intent.getStringExtra("district")
        val number = intent.getStringExtra("number")

        val _stringAddress = "$street, $number - $district"
        mMap = googleMap
        try {
            val addresses: ArrayList<Address> = geocoder.getFromLocationName(_stringAddress, 1) as ArrayList<Address>
            val address : Address = addresses[0]
            val deliveryAddress = LatLng(address.latitude, address.longitude)
            mMap.addMarker(MarkerOptions().position(deliveryAddress).title("delivery address"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(orderAddress))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(orderAddress, 18f))
            mMap.addMarker(MarkerOptions().position(orderAddress).title("order address"))
            mMap.isTrafficEnabled = true
            Log.d("GoogleMap", "before URL")
            val URL = getDirectionURL(deliveryAddress,orderAddress)
            Log.d("GoogleMap", "URL : $URL")
            GetDirection(URL).execute()



        }catch (e: IOException){
            e.printStackTrace()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            permissionCode -> if(grantResults.isEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation()
            }
        }
    }

}
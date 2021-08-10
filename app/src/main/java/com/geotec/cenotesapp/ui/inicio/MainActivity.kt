package com.geotec.cenotesapp.ui.inicio

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.ActivityMainBinding
import java.util.ArrayList

private const val PERMISSION_CAMERA_REQUEST_CODE = 201
private const val PERMISSION_STORAGE_REQUEST_CODE = 202
private const val PERMISSION_FINE_LOCATION_REQUEST_CODE = 203
private const val PERMISSION_COARSE_LOCATION_REQUEST_CODE = 203

class MainActivity : AppCompatActivity() {
    private lateinit var bv : ActivityMainBinding
    private val fragmentTransition = supportFragmentManager.beginTransaction()

    ///
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    val PERMISSION_ID = 1010
    ///

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bv = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bv.root)

        fragmentTransition.add(R.id.fragmentToolbarMain, ToolbarMainFragment())
        fragmentTransition.commit()

        fillPermissions()

        ///
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getpos.setOnClickListener {
            Log.d("Debug:",CheckPermission().toString())
            Log.d("Debug:",isLocationEnabled().toString())
            RequestPermission()
           /* fusedLocationProviderClient.lastLocation.addOnSuccessListener{location: Location? ->
                textView.text = location?.latitude.toString() + "," + location?.longitude.toString()
            }*/
            getLastLocation()
        }
    }

    override fun onResume() {
        super.onResume()
        checkAllPermissions()
    }

    class Permission(val name: String, val code: Int)
    private val permissions = ArrayList<Permission>()
    private fun fillPermissions() {
        permissions.apply {
            add(Permission(
                Manifest.permission.CAMERA, PERMISSION_CAMERA_REQUEST_CODE
            ))
            add(Permission(
                Manifest.permission.READ_EXTERNAL_STORAGE, PERMISSION_STORAGE_REQUEST_CODE
            ))
            add(Permission(
                Manifest.permission.ACCESS_FINE_LOCATION, PERMISSION_FINE_LOCATION_REQUEST_CODE
            ))
            add(Permission(
                Manifest.permission.ACCESS_COARSE_LOCATION, PERMISSION_COARSE_LOCATION_REQUEST_CODE
            ))
        }
    }
    private fun checkAllPermissions() {
        for (permission in permissions) {
            checkPermission(permission)
        }
    }
    private fun checkPermission(permission: Permission) {
        if (ContextCompat.checkSelfPermission(this, permission.name) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission.name),permission.code)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.permissionCameraGrated, Toast.LENGTH_SHORT).show()
            }
        }
    }

    ///
    fun getLastLocation(){
        if(CheckPermission()){
            if(isLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {task->
                    var location:Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        Log.d("Debug:" ,"Your Location:"+ location.longitude)
                        textView.text = "You Current Location is : Long: "+ location.longitude + " , Lat: " + location.latitude + "\n" + getCityName(location.latitude,location.longitude)
                    }
                }
            }else{
                Toast.makeText(this,"Please Turn on Your device Location",Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }
    fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,locationCallback,Looper.myLooper()
        )
    }
    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
            textView.text = "You Last Location is : Long: "+ lastLocation.longitude + " , Lat: " + lastLocation.latitude + "\n" + getCityName(lastLocation.latitude,lastLocation.longitude)
        }
    }
    private fun CheckPermission():Boolean{
        //this function will return a boolean
        //true: if we have permission
        //false if not
        if(
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                ){
            return true
        }

        return false

    }
    fun RequestPermission(){
        //this function will allows us to tell the user to requesut the necessary permsiion if they are not garented
        ActivityCompat.requestPermissions(
          this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }
    fun isLocationEnabled():Boolean{
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
      if(requestCode == PERMISSION_ID){
          if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
              Log.d("Debug:","You have the Permission")
          }
      }
    }
    private fun getCityName(lat: Double,long: Double):String{
        var cityName:String = ""
        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat,long,3)

        cityName = Adress.get(0).locality
        countryName = Adress.get(0).countryName
        Log.d("Debug:","Your City: " + cityName + " ; your Country " + countryName)
        return cityName
    }
}
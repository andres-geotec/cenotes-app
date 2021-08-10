package com.geotec.cenotesapp.ui.cenote.sections

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper.myLooper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenoteGeneralSecBinding
import com.geotec.cenotesapp.model.CenoteGeneralSec
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate
import com.google.android.gms.location.*
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_CENOTE_SAVED: String = "cenoteSaved"
private const val RESULT_CENOTE_CREATED: String = "cenoteCreated"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteGeneralSecFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteGeneralSecFragment : Fragment() {
    private var _v: FragmentCenoteGeneralSecBinding? = null
    private val v get() = _v!!

    private lateinit var sqlite: SqliteComunicate
    private lateinit var pCenoteSaved: CenoteSaved
    private var recienCreado: Boolean = false
    private lateinit var cGeneralSec: CenoteGeneralSec

    // location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    // lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pCenoteSaved = it.getSerializable(ARG_CENOTE_SAVED) as CenoteSaved
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _v = FragmentCenoteGeneralSecBinding.inflate(inflater, container, false)
        return v.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.sqlite = SqliteComunicate(context)

        val callback: OnBackPressedCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                println("Se regresará ")
                backFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (pCenoteSaved.saved) {
            cGeneralSec = getDataSec()
            fillCampos()
        } else {
            checkIsFill(v.txtCenoteName)
            getLocation()
        }

        v.btnSaveData.setOnClickListener {
            if (validFieldsRequired()) {
                if (pCenoteSaved.saved) {
                    editData()
                } else {
                    saveData()
                }
            }
        }

        v.btnCloseCenoteSection.setOnClickListener {
            backFragment()
        }
    }

    private fun saveData() {
        // Crear nuevo cenote
        pCenoteSaved = CenoteSaved()
        pCenoteSaved.clave = "0000"
        pCenoteSaved.fecha = Date()

        cGeneralSec = CenoteGeneralSec(pCenoteSaved.clave)

        val rowIdSaved = sqlite.insertCenoteSaved(pCenoteSaved)
        if (rowIdSaved != null && rowIdSaved > -1) { // asegura que el cenoce se agregue a la bd
            savedMessage(R.string.savedSuccessful)
            recienCreado = true

            // Actualizar estatus del cenote (objeto)
            pCenoteSaved.id = rowIdSaved.toInt()
            pCenoteSaved.saved = true

            // Adecuar calve y guardar cambio
            pCenoteSaved.clave += "-${pCenoteSaved.id}"
            editData()

            fillCampos()
        } else { // Error al guardar
            savedMessage(R.string.savedError)
        }
    }

    private fun editData() {
        fillData()
        val countSaved = sqlite.updateCenoteSaved(pCenoteSaved)
        if (countSaved != null && countSaved > 0) {
            saveGeneralSec()
        } else {
            savedMessage(R.string.savedError)
        }
    }

    private fun saveGeneralSec() {// Guardar datos de sección
        if (cGeneralSec.saved) {// Actualizar sección
            val countSaved = sqlite.updateCenoteGeneralSec(cGeneralSec)
            if (countSaved != null && countSaved > 0) {
                savedMessage(R.string.savedSuccessfulSec)
            } else {
                savedMessage(R.string.savedErrorSec)
            }
        } else {// Registrar sección
            cGeneralSec.clave = pCenoteSaved.clave
            val rowIdCenoteGeneralSec = sqlite.insertCenoteGeneralSec(cGeneralSec)
            if (rowIdCenoteGeneralSec != null && rowIdCenoteGeneralSec > -1) {
                cGeneralSec.saved = true
                savedMessage(R.string.savedSuccessfulSec)
            } else {
                savedMessage(R.string.savedErrorSec)
            }
        }
    }

    private fun getDataSec(): CenoteGeneralSec {
        val list = sqlite.readCenotesGeneralSec(pCenoteSaved.clave)
        return if (list.size > 0) {
            list[0]
        } else {
            CenoteGeneralSec("---")
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun fillCampos() {
        this.v.txtCenoteClave.setText(this.pCenoteSaved.clave)
        this.v.txtCenoteName.setText(this.pCenoteSaved.nombre)
        this.v.txtCenoteAddress.setText(this.pCenoteSaved.domicilio)
        this.v.txtCenoteDate.setText(SimpleDateFormat().format(this.pCenoteSaved.fecha))

        this.v.txtCenoteStreet1.setText(this.cGeneralSec.entreCalle1)
        this.v.txtCenoteStreet2.setText(this.cGeneralSec.entreCalle2)
        this.v.txtCenoteAgeb.setText(this.cGeneralSec.ageb)
        this.v.txtCenoteLongitude.setText(this.cGeneralSec.longitude.toString())
        this.v.txtCenoteLatitude.setText(this.cGeneralSec.latitude.toString())
    }
    private fun fillData() {
        // cenoteSaved.clave = this.bv.txtCenoteClave.text.toString()
        // cenoteSaved.fecha = SimpleDateFormat().parse(this.bv.txtCenoteDate.text.toString()) as Date
        pCenoteSaved.nombre = v.txtCenoteName.text.toString()
        pCenoteSaved.domicilio = v.txtCenoteAddress.text.toString()
        pCenoteSaved.progreso_general = 4

        cGeneralSec.ageb = getDataField(v.txtCenoteAgeb)
        cGeneralSec.entreCalle1 = getDataField(v.txtCenoteStreet1)
        cGeneralSec.entreCalle2 = getDataField(v.txtCenoteStreet2)
        /**
         * Obtener coordenadas xy
         */
        cGeneralSec.longitude = getDataField(v.txtCenoteLongitude)?.toDouble()
        cGeneralSec.latitude = getDataField(v.txtCenoteLatitude)?.toDouble()
        cGeneralSec.accuracy = accuracy
    }

    private fun getDataField(editText: TextInputEditText): String? {
        return if (isFill(editText)) {
            pCenoteSaved.progreso_general += 1
            editText.text.toString()
        } else null
    }
    private fun validFieldsRequired(): Boolean {
        return (checkIsFill(v.txtCenoteName)
                && checkIsFill(v.txtCenoteAddress)
                && checkIsFill(v.txtCenoteAgeb))
    }
    private fun isFill(editText: TextInputEditText) = editText.text.toString().trim().isNotEmpty()
    private fun checkIsFill(editText: TextInputEditText): Boolean {
        return if (isFill(editText)) true else {
            editText.error = getString(R.string.errorFieldRequired)
            editText.requestFocus()
            false
        }
    }
    private fun savedMessage(rString: Int) {
        Toast.makeText(context, getString(rString), Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(pCenoteSaved: CenoteSaved) = CenoteGeneralSecFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CENOTE_SAVED, pCenoteSaved)
            }
        }
    }

    private fun backFragment() {
        if (pCenoteSaved.saved && recienCreado) {
            val savedStateHandle = findNavController().previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set(RESULT_CENOTE_CREATED, pCenoteSaved)
        }
        findNavController().navigateUp()
    }

    private var accuracy: Float? = null
    private fun showLocation(location: Location) {
        v.txtCenoteLongitude.setText(location.longitude.toString())
        v.txtCenoteLatitude.setText(location.latitude.toString())
        accuracy = location.accuracy
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if(!checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location:Location? = task.result
                    if(location == null){
                        newLocationData()
                    }else{
                        Log.d("Debug:" ,"Your Location:"+ location.longitude)
                        showLocation(location)
                        //textView.text = "You Current Location is : Long: "+ location.longitude + " , Lat: " + location.latitude + "\n" + getCityName(location.latitude,location.longitude)
                    }
                }
            } // else: Encender gps
        } // else: No hay permisos
    }

    @SuppressLint("MissingPermission")
    private fun newLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,locationCallback, myLooper()
        )
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
            showLocation(lastLocation)
            //textView.text = "You Last Location is : Long: "+ lastLocation.longitude + " , Lat: " + lastLocation.latitude + "\n" + getCityName(lastLocation.latitude,lastLocation.longitude)
        }
    }

    private fun checkPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )
    }

    private fun isLocationEnabled():Boolean{
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }
}
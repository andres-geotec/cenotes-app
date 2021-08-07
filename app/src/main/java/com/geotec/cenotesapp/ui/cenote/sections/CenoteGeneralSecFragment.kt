package com.geotec.cenotesapp.ui.cenote.sections

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenoteGeneralSecBinding
import com.geotec.cenotesapp.model.CenoteGeneralSec
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate
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
    private lateinit var cenoteGeneralSec: CenoteGeneralSec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pCenoteSaved = it.getSerializable(ARG_CENOTE_SAVED) as CenoteSaved
        }
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
            cenoteGeneralSec = getDataSec()
            fillCampos()
        } else {
            checkIsFill(v.txtCenoteName)
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

        cenoteGeneralSec = CenoteGeneralSec(pCenoteSaved.clave)

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
        if (cenoteGeneralSec.saved) {// Actualizar sección
            val countSaved = sqlite.updateCenoteGeneralSec(cenoteGeneralSec)
            if (countSaved != null && countSaved > 0) {
                savedMessage(R.string.savedSuccessfulSec)
            } else {
                savedMessage(R.string.savedErrorSec)
            }
        } else {// Registrar sección
            cenoteGeneralSec.clave = pCenoteSaved.clave
            val rowIdCenoteGeneralSec = sqlite.insertCenoteGeneralSec(cenoteGeneralSec)
            if (rowIdCenoteGeneralSec != null && rowIdCenoteGeneralSec > -1) {
                cenoteGeneralSec.saved = true
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

        this.v.txtCenoteStreet1.setText(this.cenoteGeneralSec.entreCalle1)
        this.v.txtCenoteStreet2.setText(this.cenoteGeneralSec.entreCalle2)
        this.v.txtCenoteAgeb.setText(this.cenoteGeneralSec.ageb)
        this.v.txtCenoteLongitude.setText(this.cenoteGeneralSec.longitude.toString())
        this.v.txtCenoteLatitude.setText(this.cenoteGeneralSec.latitude.toString())
    }
    private fun fillData() {
        // cenoteSaved.clave = this.bv.txtCenoteClave.text.toString()
        // cenoteSaved.fecha = SimpleDateFormat().parse(this.bv.txtCenoteDate.text.toString()) as Date
        pCenoteSaved.nombre = v.txtCenoteName.text.toString()
        pCenoteSaved.domicilio = v.txtCenoteAddress.text.toString()
        pCenoteSaved.progreso_general = 4

        cenoteGeneralSec.ageb = v.txtCenoteAgeb.text.toString()
        pCenoteSaved.progreso_general += 1

        cenoteGeneralSec.entreCalle1 = v.txtCenoteStreet1.text.toString()
        if (isFill(v.txtCenoteStreet1)) pCenoteSaved.progreso_general += 1

        cenoteGeneralSec.entreCalle2 = v.txtCenoteStreet2.text.toString()
        if (isFill(v.txtCenoteStreet2)) pCenoteSaved.progreso_general += 1
        /**
         * Obtener coordenadas xy
         */
        cenoteGeneralSec.longitude = -99.5
        pCenoteSaved.progreso_general += 1

        cenoteGeneralSec.latitude = 19.5
        pCenoteSaved.progreso_general += 1
    }


    private fun savedMessage(rString: Int) {
        Toast.makeText(context, getString(rString), Toast.LENGTH_SHORT).show()
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
}
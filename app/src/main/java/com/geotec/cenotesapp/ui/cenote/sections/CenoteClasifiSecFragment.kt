package com.geotec.cenotesapp.ui.cenote.sections

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenoteClasifiSecBinding
import com.geotec.cenotesapp.model.CenoteClasifiSec
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate

private const val ARG_CENOTE_SAVED: String = "cenoteSaved"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteClasifiSecFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteClasifiSecFragment : Fragment() {
    private var _v: FragmentCenoteClasifiSecBinding? = null
    private val v get() = _v!!

    private lateinit var sqlite: SqliteComunicate
    private lateinit var pCenoteSaved: CenoteSaved
    private lateinit var cenoteClasifiSec: CenoteClasifiSec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pCenoteSaved = it.getSerializable(ARG_CENOTE_SAVED) as CenoteSaved
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _v = FragmentCenoteClasifiSecBinding.inflate(inflater, container, false)
        return v.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.sqlite = SqliteComunicate(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cenoteClasifiSec = getDataSec()
        if (cenoteClasifiSec.saved) {
            fillCampos()
        }

        v.btnSaveData.setOnClickListener {
            fillData()
            if (cenoteClasifiSec.saved) {
                editData()
            } else {
                saveData()
            }
        }

        v.btnCloseCenoteSection.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun getDataSec(): CenoteClasifiSec {
        val list = sqlite.readCenotesClasifiSec(pCenoteSaved.clave)
        return if (list.size > 0) {
            list[0]
        } else {
            CenoteClasifiSec(pCenoteSaved.clave)
        }
    }

    private fun editData() {
        val countSaved = sqlite.updateCenoteClasifiSec(cenoteClasifiSec)
        if (countSaved != null && countSaved > 0) {
            if(editProgress()) savedMessage(R.string.savedSuccessfulSec)
        } else {
            savedMessage(R.string.savedErrorSec)
        }
    }
    private fun editProgress(): Boolean {
        val countSaved = sqlite.updateCenoteSaved(pCenoteSaved)
        return if (!(countSaved != null && countSaved > 0)) {
            savedMessage(R.string.savedError)
            false
        } else true
    }
    private fun saveData() {
        val rowIdSaved = sqlite.insertCenoteClasifiSec(cenoteClasifiSec)
        if (rowIdSaved != null && rowIdSaved > -1) { // asegura que el cenoce se agregue a la bd
            savedMessage(R.string.savedSuccessfulSec)
            cenoteClasifiSec.saved = true
            editProgress()
        } else { // Error al guardar
            savedMessage(R.string.savedErrorSec)
        }
    }

    private fun fillCampos() {
        setRadioValue(cenoteClasifiSec.genesis.toString(), v.rdGrpSec1)
        setRadioValue(cenoteClasifiSec.geoforma.toString(), v.rdGrpSec2)
        setRadioValue(cenoteClasifiSec.tipo.toString(), v.rdGrpSec3)
        setRadioValue(cenoteClasifiSec.apertura.toString(), v.rdGrpSec4)
        setRadioValue(cenoteClasifiSec.cuerpoAgua.toString(), v.rdGrpSec5)
    }
    private fun fillData() {
        pCenoteSaved.progreso_clasifi = 0
        cenoteClasifiSec.genesis = getRadioValue(v.rdGrpSec1)
        cenoteClasifiSec.geoforma = getRadioValue(v.rdGrpSec2)
        cenoteClasifiSec.tipo = getRadioValue(v.rdGrpSec3)
        cenoteClasifiSec.apertura = getRadioValue(v.rdGrpSec4)
        cenoteClasifiSec.cuerpoAgua = getRadioValue(v.rdGrpSec5)
    }

    private fun getRadioValue(radioGroup: RadioGroup): String? {
        val radioId = radioGroup.checkedRadioButtonId
        return if (radioId != -1) {
            pCenoteSaved.progreso_clasifi += 1
            v.root.findViewById<RadioButton>(radioId).text.toString()
        } else null
    }
    private fun setRadioValue(value: String, rdGroup: RadioGroup) {
        for (viewChild in rdGroup.children) {
            if (viewChild is RadioButton) {
                if (viewChild.text.equals(value)) {
                    rdGroup.check(viewChild.id)
                }
            }
        }
    }
    private fun savedMessage(rString: Int) {
        Toast.makeText(context, getString(rString), Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(pCenoteSaved: CenoteSaved) = CenoteClasifiSecFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CENOTE_SAVED, pCenoteSaved)
            }
        }
    }
}
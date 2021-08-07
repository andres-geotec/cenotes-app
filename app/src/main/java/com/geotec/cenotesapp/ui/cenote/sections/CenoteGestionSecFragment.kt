package com.geotec.cenotesapp.ui.cenote.sections

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenoteGestionSecBinding
import com.geotec.cenotesapp.model.CenoteGestionSec
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate

private const val ARG_CENOTE_SAVED: String = "cenoteSaved"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteGestionSecFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteGestionSecFragment : Fragment() {
    private var _v: FragmentCenoteGestionSecBinding? = null
    private val v get() = _v!!

    private lateinit var sqlite: SqliteComunicate
    private lateinit var pCenoteSaved: CenoteSaved
    private lateinit var cGestionSec: CenoteGestionSec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pCenoteSaved = it.getSerializable(ARG_CENOTE_SAVED) as CenoteSaved
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _v = FragmentCenoteGestionSecBinding.inflate(inflater, container, false)
        return v.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.sqlite = SqliteComunicate(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cGestionSec = getDataSec()
        if (cGestionSec.saved) {
            fillCampos()
        }

        v.btnSaveData.setOnClickListener {
            fillData()
            if (cGestionSec.saved) {
                editData()
            } else {
                saveData()
            }
        }

        v.btnCloseCenoteSection.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun getDataSec(): CenoteGestionSec {
        val list = sqlite.readCenotesGestionSec(pCenoteSaved.clave)
        return if (list.size > 0) {
            list[0]
        } else {
            CenoteGestionSec(pCenoteSaved.clave)
        }
    }

    private fun saveData() {
        val rowIdSaved = sqlite.insertCenoteGestionSec(cGestionSec)
        if (rowIdSaved != null && rowIdSaved > -1) { // asegura que el cenoce se agregue a la bd
            savedMessage(R.string.savedSuccessfulSec)
            cGestionSec.saved = true
            editProgress()
        } else { // Error al guardar
            savedMessage(R.string.savedErrorSec)
        }
    }
    private fun editData() {
        val countSaved = sqlite.updateCenoteGestionSec(cGestionSec)
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

    private fun fillCampos() {
        setCheckValues(cGestionSec.estado_cenote.toString(), v.chGrpSec1)
        setCheckValues(cGestionSec.respuesta_estado.toString(), v.chGrpSec2)
        setCheckValues(cGestionSec.agentes.toString(), v.chGrpSec3)
    }
    private fun fillData() {
        pCenoteSaved.progreso_gestion = 0
        cGestionSec.estado_cenote = getCheckValues(v.chGrpSec1)
        cGestionSec.respuesta_estado = getCheckValues(v.chGrpSec2)
        cGestionSec.agentes = getCheckValues(v.chGrpSec3)
    }


    private fun getCheckValues(checkGroup: LinearLayout): String? {
        val checkList = ArrayList<String>()
        for (viewChild in checkGroup.children) {
            if (viewChild is CheckBox) {
                if (viewChild.isChecked) {
                    checkList.add(viewChild.text.toString())
                }
            }
        }
        return if (checkList.size > 0) {
            pCenoteSaved.progreso_gestion += 1
            checkList.joinToString(",")
        } else null
    }
    private fun setCheckValues(values: String, checkGroup: LinearLayout) {
        for (viewChild in checkGroup.children) {
            if (viewChild is CheckBox) {
                for (value in values.split(",")) {
                    if (viewChild.text.equals(value)) {
                        viewChild.isChecked = true
                    }
                }
            }
        }
    }
    private fun savedMessage(rString: Int) {
        Toast.makeText(context, getString(rString), Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(pCenoteSaved: CenoteSaved) = CenoteGestionSecFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CENOTE_SAVED, pCenoteSaved)}}
    }
}
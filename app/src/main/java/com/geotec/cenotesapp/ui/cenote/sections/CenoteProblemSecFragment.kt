package com.geotec.cenotesapp.ui.cenote.sections

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenoteProblemSecBinding
import com.geotec.cenotesapp.model.CenoteProblemSec
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.model.CenoteUsoSec
import com.geotec.cenotesapp.sqlite.SqliteComunicate

private const val ARG_CENOTE_SAVED: String = "cenoteSaved"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteProblemSecFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteProblemSecFragment : Fragment() {
    private var _v: FragmentCenoteProblemSecBinding? = null
    private val v get() = _v!!

    private lateinit var sqlite: SqliteComunicate
    private lateinit var pCenoteSaved: CenoteSaved
    private lateinit var cProblemSec: CenoteProblemSec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pCenoteSaved = it.getSerializable(ARG_CENOTE_SAVED) as CenoteSaved
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _v = FragmentCenoteProblemSecBinding.inflate(inflater, container, false)
        return v.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.sqlite = SqliteComunicate(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cProblemSec = getDataSec()
        if (cProblemSec.saved) {
            fillCampos()
        }

        v.btnSaveData.setOnClickListener {
            fillData()
            if (cProblemSec.saved) {
                editData()
            } else {
                saveData()
            }
        }

        v.btnCloseCenoteSection.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun getDataSec(): CenoteProblemSec {
        val list = sqlite.readCenotesProblemSec(pCenoteSaved.clave)
        return if (list.size > 0) {
            list[0]
        } else {
            CenoteProblemSec(pCenoteSaved.clave)
        }
    }

    private fun saveData() {
        val rowIdSaved = sqlite.insertCenoteProblemSec(cProblemSec)
        if (rowIdSaved != null && rowIdSaved > -1) { // asegura que el cenoce se agregue a la bd
            savedMessage(R.string.savedSuccessfulSec)
            cProblemSec.saved = true
            editProgress()
        } else { // Error al guardar
            savedMessage(R.string.savedErrorSec)
        }
    }
    private fun editData() {
        val countSaved = sqlite.updateCenoteProblemSec(cProblemSec)
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
        setCheckValues(cProblemSec.contaminacion.toString(), v.chGrpSec1)
        setRadioValue(cProblemSec.vertederos.toString(), v.rdGrpSec2)
        setCheckValues(cProblemSec.movimientos.toString(), v.chGrpSec3)
        setRadioValue(cProblemSec.depresiones.toString(), v.rdGrpSec4)
        setRadioValue(cProblemSec.visitas_masivas.toString(), v.rdGrpSec5)
        setRadioValue(cProblemSec.usos_previos.toString(), v.rdGrpSec6)
    }
    private fun fillData() {
        pCenoteSaved.progreso_uso = 0
        cProblemSec.contaminacion = getCheckValues(v.chGrpSec1)
        cProblemSec.vertederos = getRadioValue(v.rdGrpSec2)
        cProblemSec.movimientos = getCheckValues(v.chGrpSec3)
        cProblemSec.depresiones = getRadioValue(v.rdGrpSec4)
        cProblemSec.visitas_masivas = getRadioValue(v.rdGrpSec5)
        cProblemSec.usos_previos = getRadioValue(v.rdGrpSec6)
    }

    private fun getRadioValue(radioGroup: RadioGroup): String? {
        val radioId = radioGroup.checkedRadioButtonId
        return if (radioId != -1) {
            pCenoteSaved.progreso_problem += 1
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
            pCenoteSaved.progreso_problem += 1
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
        fun newInstance(pCenoteSaved: CenoteSaved) = CenoteProblemSecFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CENOTE_SAVED, pCenoteSaved)
            }
        }
    }
}
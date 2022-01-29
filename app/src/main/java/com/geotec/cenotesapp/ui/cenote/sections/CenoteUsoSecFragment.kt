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
import com.geotec.cenotesapp.databinding.FragmentCenoteUsoSecBinding
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.model.CenoteUsoSec
import com.geotec.cenotesapp.sqlite.SqliteComunicate

private const val ARG_CENOTE_SAVED: String = "cenoteSaved"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteUsoSecFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteUsoSecFragment : Fragment() {
    private var _v: FragmentCenoteUsoSecBinding? = null
    private val v get() = _v!!

    private lateinit var sqlite: SqliteComunicate
    private lateinit var pCenoteSaved: CenoteSaved
    private lateinit var cUsoSec: CenoteUsoSec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pCenoteSaved = it.getSerializable(ARG_CENOTE_SAVED) as CenoteSaved
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _v = FragmentCenoteUsoSecBinding.inflate(inflater, container, false)
        return v.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.sqlite = SqliteComunicate(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cUsoSec = getDataSec()
        if (cUsoSec.saved) {
            fillCampos()
        }

        v.btnSaveData.setOnClickListener {
            fillData()
            if (cUsoSec.saved) {
                editData()
            } else {
                saveData()
            }
        }

        v.btnCloseCenoteSection.setOnClickListener {
            findNavController().navigateUp()
        }

        v.rdGrpSec6.setOnCheckedChangeListener { _, i ->
            if (v.root.findViewById<RadioButton>(i).text.toString() == "Si") {
                v.txtGrpSec6op3.visibility = View.VISIBLE
            } else {
                v.txtGrpSec6op3.visibility = View.GONE
            }
        }
    }


    private fun getDataSec(): CenoteUsoSec {
        val list = sqlite.readCenotesUsoSec(pCenoteSaved.clave)
        return if (list.size > 0) {
            list[0]
        } else {
            CenoteUsoSec(pCenoteSaved.clave)
        }
    }

    private fun saveData() {
        val rowIdSaved = sqlite.insertCenoteUsoSec(cUsoSec)
        if (rowIdSaved != null && rowIdSaved > -1) { // asegura que el cenoce se agregue a la bd
            savedMessage(R.string.savedSuccessfulSec)
            cUsoSec.saved = true
            editProgress()
        } else { // Error al guardar
            savedMessage(R.string.savedErrorSec)
        }
    }
    private fun editData() {
        val countSaved = sqlite.updateCenoteUsoSec(cUsoSec)
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
        setRadioValue(cUsoSec.uso_actual.toString(), v.rdGrpSec1)
        setRadioValue(cUsoSec.densidad_urbana.toString(), v.rdGrpSec2)
        setRadioValue(cUsoSec.tipo_vialidad.toString(), v.rdGrpSec3)
        setCheckValues(cUsoSec.servicios_publicos.toString(), v.chGrpSec4)
        setCheckValues(cUsoSec.ecosistema.toString(), v.chGrpSec5)
    }
    private fun fillData() {
        pCenoteSaved.progreso_uso = 0
        cUsoSec.uso_actual = getRadioValue(v.rdGrpSec1)
        cUsoSec.densidad_urbana = getRadioValue(v.rdGrpSec2)
        cUsoSec.tipo_vialidad = getRadioValue(v.rdGrpSec3)
        cUsoSec.servicios_publicos = getCheckValues(v.chGrpSec4)
        cUsoSec.ecosistema = getCheckValues(v.chGrpSec5)
    }

    private fun getRadioValue(radioGroup: RadioGroup): String? {
        val radioId = radioGroup.checkedRadioButtonId
        return if (radioId != -1) {
            pCenoteSaved.progreso_uso += 1
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
            pCenoteSaved.progreso_uso += 1
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
        fun newInstance(pCenoteSaved: CenoteSaved) = CenoteUsoSecFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CENOTE_SAVED, pCenoteSaved)
            }
        }
    }
}
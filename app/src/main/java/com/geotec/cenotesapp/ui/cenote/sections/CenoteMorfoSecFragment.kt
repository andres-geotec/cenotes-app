package com.geotec.cenotesapp.ui.cenote.sections

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenoteMorfoSecBinding
import com.geotec.cenotesapp.model.CenoteMorfoSec
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate
import com.google.android.material.textfield.TextInputEditText

private const val ARG_CENOTE_SAVED: String = "cenoteSaved"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteMorfoSecFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteMorfoSecFragment : Fragment() {
    private var _v: FragmentCenoteMorfoSecBinding? = null
    private val v get() = _v!!

    private lateinit var sqlite: SqliteComunicate
    private lateinit var pCenoteSaved: CenoteSaved
    private lateinit var cMorfoSec: CenoteMorfoSec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pCenoteSaved = it.getSerializable(ARG_CENOTE_SAVED) as CenoteSaved
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _v = FragmentCenoteMorfoSecBinding.inflate(inflater, container, false)
        return v.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.sqlite = SqliteComunicate(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cMorfoSec = getDataSec()
        if (cMorfoSec.saved) {
            fillCampos()
        }

        v.btnSaveData.setOnClickListener {
            fillData()
            if (cMorfoSec.saved) {
                editData()
            } else {
                saveData()
            }
        }

        v.btnCloseCenoteSection.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun getDataSec(): CenoteMorfoSec {
        val list = sqlite.readCenotesMorfoSec(pCenoteSaved.clave)
        return if (list.size > 0) {
            list[0]
        } else {
            CenoteMorfoSec(pCenoteSaved.clave)
        }
    }

    private fun editData() {
        val countSaved = sqlite.updateCenoteMorfoSec(cMorfoSec)
        if (countSaved != null && countSaved > 0) {
            if(editProgress()) savedMessage(R.string.savedSuccessfulSec)
        } else {
            savedMessage(R.string.savedErrorSec)
        }
    }
    private fun saveData() {
        val rowIdSaved = sqlite.insertCenoteMorfoSec(cMorfoSec)
        if (rowIdSaved != null && rowIdSaved > -1) { // asegura que el cenoce se agregue a la bd
            savedMessage(R.string.savedSuccessfulSec)
            cMorfoSec.saved = true
            editProgress()
        } else { // Error al guardar
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
        v.txtCenoteArea.setText(cMorfoSec.area?.toString())
        v.txtCenotePerimetro.setText(cMorfoSec.perimetro?.toString())
        v.txtCenoteProfundidad.setText(cMorfoSec.profundidad?.toString())
        v.txtCenoteSemiMayor.setText(cMorfoSec.semiejeMayor?.toString())
        v.txtCenoteSemiMenor.setText(cMorfoSec.semiejeMenor?.toString())
        v.txtCenoteElongacion.setText(cMorfoSec.elongacion?.toString())
    }
    private fun fillData() {
        pCenoteSaved.progreso_morfo = 0
        cMorfoSec.area = getDataField(v.txtCenoteArea)?.toFloat()
        cMorfoSec.perimetro = getDataField(v.txtCenotePerimetro)?.toFloat()
        cMorfoSec.profundidad = getDataField(v.txtCenoteProfundidad)?.toFloat()
        cMorfoSec.semiejeMayor = getDataField(v.txtCenoteSemiMayor)?.toFloat()
        cMorfoSec.semiejeMenor = getDataField(v.txtCenoteSemiMenor)?.toFloat()
        cMorfoSec.elongacion = getDataField(v.txtCenoteElongacion)?.toFloat()
    }

    private fun getDataField(editText: TextInputEditText): String? {
        return if (isFill(editText)) {
            pCenoteSaved.progreso_morfo += 1
            editText.text.toString()
        } else null
    }
    private fun isFill(editText: TextInputEditText) = editText.text.toString().trim().isNotEmpty()
    private fun savedMessage(rString: Int) {
        Toast.makeText(context, getString(rString), Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(pCenoteSaved: CenoteSaved) = CenoteMorfoSecFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CENOTE_SAVED, pCenoteSaved)
            }
        }
    }
}
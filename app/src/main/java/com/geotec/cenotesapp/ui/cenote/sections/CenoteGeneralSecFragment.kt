package com.geotec.cenotesapp.ui.cenote.sections

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.geotec.cenotesapp.databinding.FragmentCenoteGeneralSecBinding
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_CENOTE_SAVED: String = "cenoteSaved"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteGeneralSecFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteGeneralSecFragment : Fragment() {
    private var _bv: FragmentCenoteGeneralSecBinding? = null
    private val bv get() = _bv!!

    private lateinit var sqlite: SqliteComunicate
    private lateinit var pCenoteSaved: CenoteSaved

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.pCenoteSaved = it.getSerializable(ARG_CENOTE_SAVED) as CenoteSaved
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bv = FragmentCenoteGeneralSecBinding.inflate(inflater, container, false)
        return bv.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.sqlite = SqliteComunicate(context)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(this.pCenoteSaved) {
            if (this.saved) {
                bv.txtCenoteClave.setText(this.clave)
                bv.txtCenoteName.setText(this.nombre)
                bv.txtCenoteDate.setText(SimpleDateFormat().format(this.fecha))
                bv.btnSaveData.setOnClickListener {editData(this)}
            } else {
                // bv.txtCenoteClave.setText("clave-${this.id}")
                bv.btnSaveData.setOnClickListener {saveData(this)}
            }
        }

        bv.btnCloseCenoteSection.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun saveData(cenoteSaved: CenoteSaved) {
        //sqlite.insertCenoteSaved(CenoteSaved())
    }

    private fun editData(cenoteSaved: CenoteSaved) {
        // cenoteSaved.clave = this.bv.txtCenoteClave.text.toString()
        // cenoteSaved.fecha = SimpleDateFormat().parse(this.bv.txtCenoteDate.text.toString()) as Date
        var progress: Int = 1
        if (bv.txtCenoteName.text.toString().trim() !== "") {
            cenoteSaved.nombre = this.bv.txtCenoteName.text.toString()
            progress+=1
        }
        cenoteSaved.progreso_general = progress
        val count = sqlite.updateCenoteSaved(cenoteSaved)
        Toast.makeText(this.context, "$count modificado", Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param pCenoteSaved Parameter 1.
         * @return A new instance of fragment CenoteGeneralSecFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(pCenoteSaved: CenoteSaved) =
            CenoteGeneralSecFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CENOTE_SAVED, pCenoteSaved)
                }
            }
    }
}
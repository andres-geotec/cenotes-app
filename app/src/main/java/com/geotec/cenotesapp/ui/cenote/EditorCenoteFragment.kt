package com.geotec.cenotesapp.ui.cenote

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentEditorCenoteBinding
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate
import java.util.*

private const val ARG_CENOTE_SAVED: String = "cenoteSaved"

/**
 * A simple [Fragment] subclass.
 */
class EditorCenoteFragment : Fragment() {
    private var _bv: FragmentEditorCenoteBinding? = null
    private val bv get() = _bv!!

    private lateinit var sqlite: SqliteComunicate
    private lateinit var paramCenoteSaved: CenoteSaved

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bv = FragmentEditorCenoteBinding.inflate(inflater, container, false)

        return bv.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            this.paramCenoteSaved = it.getSerializable(ARG_CENOTE_SAVED) as CenoteSaved
        }

        with(this.paramCenoteSaved) {
            bv.txtGeneralProgress.text = "${this.progreso_general} / ${getString(R.string.secGeneralCountAnswers)}"
            bv.pbrGeneralProgress.progress = getProgress(this.progreso_general, R.string.secGeneralCountAnswers)

            if (this.saved) {
                bv.txtDescEditorCenote.text = this.nombre
                bv.txtCenoteNotSavedYet.visibility = View.GONE
            }
        }

        bv.btnSabedTodo.setOnClickListener {
            this.savedNewCenote()
        }
        bv.btnCloseEditorCenote.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.sqlite = SqliteComunicate(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        _bv = null
    }

    private fun savedNewCenote() {
        val cenoteSaved: CenoteSaved = CenoteSaved()
        cenoteSaved.clave = "0000"
        cenoteSaved.nombre = "Cenote de Prueba"
        cenoteSaved.domicilio = "Domicilio de Prueba"
        cenoteSaved.fecha = Date()

        val newRowId = this.sqlite.insertCenoteSaved(cenoteSaved)
        Toast.makeText(this.context, "Saved $newRowId", Toast.LENGTH_SHORT).show()
    }

    private fun getProgress(value: Int, stringTotal: Int) = (
            (value.toFloat() / getString(stringTotal).toInt()) * 100).toInt()

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment EditorCenoteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            EditorCenoteFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CENOTE_SAVED, param1)
                }
            }
    }
}
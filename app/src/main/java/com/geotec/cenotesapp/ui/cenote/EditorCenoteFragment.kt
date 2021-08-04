package com.geotec.cenotesapp.ui.cenote

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentEditorCenoteBinding
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class EditorCenoteFragment : Fragment() {
    private var _bv: FragmentEditorCenoteBinding? = null
    private val bv get() = _bv!!

    private lateinit var sqlite: SqliteComunicate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bv = FragmentEditorCenoteBinding.inflate(inflater, container, false)

        bv.btnSabedTodo.setOnClickListener {
            this.savedNewCenote()
        }

        return bv.root
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
}
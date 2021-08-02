package com.geotec.cenotesapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentGeneralidadesDialogBinding
import com.geotec.cenotesapp.model.Cenote


/**
 * A simple [Fragment] subclass.
 */
class GeneralidadesDialogFragment : DialogFragment() {
    private var _bv : FragmentGeneralidadesDialogBinding? = null
    private val bv get() = _bv!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_generalidades_dialog, container, false)
        _bv = FragmentGeneralidadesDialogBinding.inflate(inflater, container, false)

        val cenote = arguments?.getSerializable("cenote") as Cenote

        // Usar dismiss() para cerrar
        bv.idTest.text = cenote.clave + " " + cenote.nombre

        return bv.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _bv = null
    }

}
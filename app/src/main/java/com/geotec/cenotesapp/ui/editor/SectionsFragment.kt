package com.geotec.cenotesapp.ui.editor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentSectionsBinding
import com.geotec.cenotesapp.model.Cenote

/**
 * A simple [Fragment] subclass.
 */
class SectionsFragment : Fragment() {
    private var _bv : FragmentSectionsBinding? = null
    private val bv get() = _bv!!
    private lateinit var cenote : Cenote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cenote = Cenote()
        cenote.clave = "0"
        cenote.nombre = "Soy el primer cenote"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_sections, container, false)
        _bv = FragmentSectionsBinding.inflate(inflater, container, false)

        bv.crdGeneralSection.setOnClickListener {
            // Toast.makeText(this.context, "mandar dialog...", Toast.LENGTH_SHORT).show()
            val bundle = bundleOf("cenote" to cenote)
            findNavController().navigate(R.id.action_sectionsFragment_to_generalidadesDialogFragment, bundle)
        }

        return bv.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _bv = null
    }
}
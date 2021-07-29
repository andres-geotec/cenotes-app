package com.geotec.cenotesapp.ui.editor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentSectionsBinding

/**
 * A simple [Fragment] subclass.
 */
class SectionsFragment : Fragment() {
    private var _bv : FragmentSectionsBinding? = null
    private val bv get() = _bv!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_sections, container, false)
        _bv = FragmentSectionsBinding.inflate(inflater, container, false)

        bv.crdGeneralSection.setOnClickListener {
            Toast.makeText(this.context, "mandar dialog...", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_sectionsFragment_to_generalidadesDialogFragment)
        }

        return bv.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _bv = null
    }
}
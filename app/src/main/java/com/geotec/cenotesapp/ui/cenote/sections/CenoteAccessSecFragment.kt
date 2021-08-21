package com.geotec.cenotesapp.ui.cenote.sections

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.fragment.findNavController
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenoteAccessSecBinding
import com.geotec.cenotesapp.model.CenoteAccessSec
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate

private const val ARG_CENOTE_SAVED: String = "cenoteSaved"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteAccessSecFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteAccessSecFragment : Fragment() {
    private var _v: FragmentCenoteAccessSecBinding? = null
    private val v get() = _v!!

    private lateinit var sqlite: SqliteComunicate
    private lateinit var pCenoteSaved: CenoteSaved
    private lateinit var cAccessSec: CenoteAccessSec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pCenoteSaved = it.getSerializable(ARG_CENOTE_SAVED) as CenoteSaved
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _v = FragmentCenoteAccessSecBinding.inflate(inflater, container, false)
        return v.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.sqlite = SqliteComunicate(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        v.rdGrpSec1.setOnCheckedChangeListener { _, i ->
            if (v.root.findViewById<RadioButton>(i).text.toString() == "Si") {
                v.rdGrpSec2.visibility = View.GONE
            } else {
                v.rdGrpSec2.visibility = View.VISIBLE
            }
        }

        v.btnSaveData.setOnClickListener {
        }

        v.btnCloseCenoteSection.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(pCenoteSaved: CenoteSaved) = CenoteAccessSecFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CENOTE_SAVED, pCenoteSaved)}}
    }
}
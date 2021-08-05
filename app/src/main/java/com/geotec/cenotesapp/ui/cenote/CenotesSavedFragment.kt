package com.geotec.cenotesapp.ui.cenote

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenotesSavedBinding
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CenotesSavedFragment : Fragment(), CenoteSavedListener {
    private var _bv : FragmentCenotesSavedBinding? = null
    private val bv get() = this._bv!!

    private lateinit var sqliteComunicate: SqliteComunicate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        this._bv = FragmentCenotesSavedBinding.inflate(inflater, container, false)

        this.bv.btnCreateNewCenote.setOnClickListener {
            this.toCenoteSectionsFragment(bundleOf("cenoteSaved" to CenoteSaved()))
        }

        this.bv.rvCenotesSaved.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CenoteSavedAdapter(this@CenotesSavedFragment, sqliteComunicate.readCenotesSaved())
            bv.pbCenotesSaved.visibility = View.INVISIBLE
        }

        return bv.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.sqliteComunicate = SqliteComunicate(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        this._bv = null
    }

    override fun onCenoteSavedClick(cenoteSaved: CenoteSaved) {
        this.toCenoteSectionsFragment(bundleOf("cenoteSaved" to cenoteSaved))
    }

    private fun toEditorCenoteFragment(param: Bundle) {
        findNavController().navigate(
            R.id.action_cenotesSavedFragment_to_editorCenoteFragment, param)
    }

    private fun toCenoteSectionsFragment(param: Bundle) {
        findNavController().navigate(
            R.id.action_cenotesSavedFragment_to_cenoteSectionsFragment, param)
    }
}
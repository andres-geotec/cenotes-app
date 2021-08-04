package com.geotec.cenotesapp.ui.cenote

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenotesSavedBinding
import com.geotec.cenotesapp.sqlite.SqliteComunicate
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class CenotesSavedFragment : Fragment() {
    private var _bv : FragmentCenotesSavedBinding? = null
    private val bv get() = this._bv!!

    private lateinit var cenoteSavedAdapter: CenoteSavedAdapter
    private lateinit var sqliteComunicate: SqliteComunicate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        this._bv = FragmentCenotesSavedBinding.inflate(inflater, container, false)

        this.bv.btnCreateNewCenote.setOnClickListener {
            findNavController().navigate(R.id.action_cenotesSavedFragment_to_editorCenoteFragment)
        }

        this.bv.rvCenotesSaved.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CenoteSavedAdapter(sqliteComunicate.readCenotesSaved())
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
}
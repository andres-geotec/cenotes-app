package com.geotec.cenotesapp.ui.cenote

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenotesSavedBinding
import com.geotec.cenotesapp.sqlite.SqliteComunicate

/**
 * A simple [Fragment] subclass.
 */
class CenotesSavedFragment : Fragment() {
    private var _bv : FragmentCenotesSavedBinding? = null
    private val bv get() = _bv!!

    private lateinit var cenoteSavedAdapter: CenoteSavedAdapter
    private lateinit var sqliteComunicate: SqliteComunicate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _bv = FragmentCenotesSavedBinding.inflate(inflater, container, false)

        // val navHostFragment = supportFragmentManager

        bv.btnCreateNewCenote.setOnClickListener {
            findNavController().navigate(R.id.action_cenotesSavedFragment_to_editorCenoteFragment)
        }


        bv.rvCenotesSaved.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cenoteSavedAdapter
        }

        return bv.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.sqliteComunicate = SqliteComunicate(context)
        this.cenoteSavedAdapter = CenoteSavedAdapter(context, this.sqliteComunicate.loadFromSqlite())
    }

    override fun onDestroy() {
        super.onDestroy()
        _bv = null
    }

    private fun getItemsList() : ArrayList<String> {
        val list = ArrayList<String>()
        for (i in 1..15) {
            list.add("Item $i")
        }
        return list
    }
}
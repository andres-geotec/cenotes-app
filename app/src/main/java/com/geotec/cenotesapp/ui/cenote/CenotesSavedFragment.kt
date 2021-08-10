package com.geotec.cenotesapp.ui.cenote

import android.content.Context
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenotesSavedBinding
import com.geotec.cenotesapp.export.Geojson
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class CenotesSavedFragment : Fragment(), CenoteSavedListener {
    private var _v : FragmentCenotesSavedBinding? = null
    private val v get() = this._v!!

    private lateinit var sqliteComunicate: SqliteComunicate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this._v = FragmentCenotesSavedBinding.inflate(inflater, container, false)

        this.v.btnCreateNewCenote.setOnClickListener {
            this.toCenoteSectionsFragment(bundleOf("cenoteSaved" to CenoteSaved()))
        }

        this.v.rvCenotesSaved.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CenoteSavedAdapter(this@CenotesSavedFragment, sqliteComunicate.readCenotesSaved())
            v.pbCenotesSaved.visibility = View.INVISIBLE
        }

        return v.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.sqliteComunicate = SqliteComunicate(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        this._v = null
    }

    override fun onCenoteSavedClick(cenoteSaved: CenoteSaved) {
        this.toCenoteSectionsFragment(bundleOf("cenoteSaved" to cenoteSaved))
    }

    override fun onDelete(cenoteSaved: CenoteSaved) {}

    override fun onExport(cenoteSaved: CenoteSaved) {
        println("Exportar")
        val timestamp: String = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(Date())
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

        val nameFile = "EXPORTED_${cenoteSaved.nombre?.uppercase()}"
        File.createTempFile("${nameFile}_$timestamp", ".geojson", storageDir).apply {
            appendText(Geojson(this@CenotesSavedFragment.requireContext(), ArrayList<CenoteSaved>().apply {
                add(cenoteSaved)
                Toast.makeText(context, "$nameFile.geojson exportado satisfactoriamente", Toast.LENGTH_SHORT).show()
            }).asText())
        }

    }

    private fun toCenoteSectionsFragment(param: Bundle) {
        findNavController().navigate(
            R.id.action_cenotesSavedFragment_to_cenoteSectionsFragment, param)
    }
}
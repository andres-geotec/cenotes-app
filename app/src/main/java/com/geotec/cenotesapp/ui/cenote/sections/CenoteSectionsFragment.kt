package com.geotec.cenotesapp.ui.cenote.sections

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenoteSectionsBinding
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.model.CenoteSection
import kotlin.math.log

private const val ARG_CENOTE_SAVED: String = "cenoteSaved"
private const val RESULT_CENOTE_CREATED: String = "cenoteCreated"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteSectionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteSectionsFragment : Fragment(), CenoteSectionsListener {
    private var _bv: FragmentCenoteSectionsBinding? = null
    private val bv get() = _bv!!

    private lateinit var pCenoteSaved: CenoteSaved

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pCenoteSaved = it.getSerializable(ARG_CENOTE_SAVED) as CenoteSaved
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bv = FragmentCenoteSectionsBinding.inflate(inflater, container, false)

        fillComponents()

        return bv.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentBackStackEntry = findNavController().currentBackStackEntry
        currentBackStackEntry?.savedStateHandle?.getLiveData<CenoteSaved>(RESULT_CENOTE_CREATED)
        ?.observe(currentBackStackEntry, Observer {
            println("${it.nombre}: ${pCenoteSaved.saved}")
            if (!pCenoteSaved.saved) {// Actualizar los datos en la vista"
                pCenoteSaved = it
                fillComponents()
            }
        })

        bv.btnCloseCenoteSections.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    fun fillComponents() {
        bv.rvCenoteSections.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CenoteSectionsAdapter(this@CenoteSectionsFragment, getSectionsList())
            bv.pbCenotesSections.visibility = View.GONE
        }
        if (pCenoteSaved.saved) {
            bv.txtDescCenoteSections.text = pCenoteSaved.nombre
        }
    }

    override fun onCenoteSectionClick(cenoteSection: CenoteSection) {
        findNavController().navigate(cenoteSection.navigate, bundleOf(ARG_CENOTE_SAVED to pCenoteSaved))
    }

    private fun getSectionsList(): ArrayList<CenoteSection> {
        val list: ArrayList<CenoteSection> = ArrayList<CenoteSection>()
        // Sección de datos generales
        list.add(CenoteSection(getString(R.string.secGeneralTitle),
            R.id.action_cenoteSectionsFragment_to_cenoteGeneralSecFragment,
            getString(R.string.secGeneralCountAnswers).toInt(), pCenoteSaved.progreso_general))

        // Sección de clasificación
        list.add(CenoteSection(getString(R.string.secClasifiTitle),
            R.id.action_cenoteSectionsFragment_to_cenoteClasifiSecFragment,
            getString(R.string.secClasifiCountAnswers).toInt(), pCenoteSaved.progreso_clasifi))

        return list
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param pCenoteSaved Parameter 1.
         * @return A new instance of fragment CenoteSectionsFragment.
         */
        @JvmStatic
        fun newInstance(pCenoteSaved: CenoteSaved) = CenoteSectionsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CENOTE_SAVED, pCenoteSaved)
            }
        }
    }
}
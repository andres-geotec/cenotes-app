package com.geotec.cenotesapp.ui.cenote.sections

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenoteSectionsBinding
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.model.CenoteSection

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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

    private fun fillComponents() {
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
        if (pCenoteSaved.saved) {
            navigateSection(cenoteSection.navigate)
        } else {
            if (cenoteSection.navigate == R.id.action_cenoteSectionsFragment_to_cenoteGeneralSecFragment) {
                navigateSection(cenoteSection.navigate)
            }
        }
    }

    private fun navigateSection(navigate: Int) {
        findNavController().navigate(navigate, bundleOf(ARG_CENOTE_SAVED to pCenoteSaved))
    }

    private fun getSectionsList(): ArrayList<CenoteSection> {
        val list: ArrayList<CenoteSection> = ArrayList()
        // Sección de Datos generales
        list.add(CenoteSection(getString(R.string.secGeneralTitle),
            R.id.action_cenoteSectionsFragment_to_cenoteGeneralSecFragment,
            getString(R.string.secGeneralCountAnswers).toInt(), pCenoteSaved.progreso_general))

        if (pCenoteSaved.saved) {
            // Sección de Accesibilidad
            list.add(
                CenoteSection(
                    getString(R.string.secAccessTitle),
                    R.id.action_cenoteSectionsFragment_to_cenoteAccessSecFragment,
                    getString(R.string.secAccessCountAnswers).toInt(),
                    pCenoteSaved.progreso_access
                )
            )

            // Sección de Clasificación
            list.add(
                CenoteSection(
                    getString(R.string.secClasifiTitle),
                    R.id.action_cenoteSectionsFragment_to_cenoteClasifiSecFragment,
                    getString(R.string.secClasifiCountAnswers).toInt(),
                    pCenoteSaved.progreso_clasifi
                )
            )

            // Sección de Morfometría
            list.add(
                CenoteSection(
                    getString(R.string.secMorfoTitle),
                    R.id.action_cenoteSectionsFragment_to_cenoteMorfoSecFragment,
                    getString(R.string.secMorfoCountAnswers).toInt(), pCenoteSaved.progreso_morfo
                )
            )

            // Sección de Uso actual
            list.add(
                CenoteSection(
                    getString(R.string.secUsoTitle),
                    R.id.action_cenoteSectionsFragment_to_cenoteUsoSecFragment,
                    getString(R.string.secUsoCountAnswers).toInt(), pCenoteSaved.progreso_uso
                )
            )

            // Sección de Problemática del Sitio
            list.add(
                CenoteSection(
                    getString(R.string.secProblemTitle),
                    R.id.action_cenoteSectionsFragment_to_cenoteProblemSecFragment,
                    getString(R.string.secProblemCountAnswers).toInt(), pCenoteSaved.progreso_problem
                )
            )

            // Sección de Gestión
            list.add(
                CenoteSection(
                    getString(R.string.secGestionTitle),
                    R.id.action_cenoteSectionsFragment_to_cenoteGestionSecFragment,
                    getString(R.string.secGestionCountAnswers).toInt(), pCenoteSaved.progreso_gestion
                )
            )

            // Sección de Fotografías
            list.add(
                CenoteSection(
                    getString(R.string.secFotosTitle),
                    R.id.action_cenoteSectionsFragment_to_cenoteFotosSecFragment,
                    getString(R.string.secFotosCountAnswers).toInt(), pCenoteSaved.progreso_fotos
                )
            )
        }

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
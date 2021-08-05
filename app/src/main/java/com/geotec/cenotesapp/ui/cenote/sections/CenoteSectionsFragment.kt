package com.geotec.cenotesapp.ui.cenote.sections

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenoteSectionsBinding
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.model.CenoteSection

private const val ARG_CENOTE_SAVED: String = "cenoteSaved"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteSectionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteSectionsFragment : Fragment(), CenoteSectionsListener {
    private var _bv: FragmentCenoteSectionsBinding? = null
    private val bv get() = _bv!!

    private lateinit var paramCenoteSaved: CenoteSaved

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bv = FragmentCenoteSectionsBinding.inflate(inflater, container, false)
        return bv.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            this.paramCenoteSaved = it.getSerializable(ARG_CENOTE_SAVED) as CenoteSaved
        }

        with(this.paramCenoteSaved) {
            bv.rvCenoteSections.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CenoteSectionsAdapter(this@CenoteSectionsFragment, getSectionsList(this@with))
                bv.pbCenotesSections.visibility = View.GONE
            }
            if (this.saved) {
                bv.txtDescCenoteSections.text = this.nombre
                // bv.txtCenoteNotSavedYet.visibility = View.GONE
            }
        }

        bv.btnCloseCenoteSections.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onCenoteSectionClick(cenoteSection: CenoteSection) {
        Toast.makeText(this.context, "ir a edición ${cenoteSection.nombre}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(cenoteSection.navigate)
    }

    private fun getSectionsList(cenoteSaved: CenoteSaved): ArrayList<CenoteSection> {
        val list: ArrayList<CenoteSection> = ArrayList<CenoteSection>()
        // Sección de datos generales
        list.add(CenoteSection(getString(R.string.secGeneralTitle),
            R.id.action_cenoteSectionsFragment_to_generalCenoteFragment,
            getString(R.string.secGeneralCountAnswers).toInt(), cenoteSaved.progreso_general))
        // Sección de datos generales
        list.add(CenoteSection(getString(R.string.secGeneralTitle),
            R.id.action_cenoteSectionsFragment_to_generalCenoteFragment,
            getString(R.string.secGeneralCountAnswers).toInt(), cenoteSaved.progreso_general))
        return list
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment CenoteSectionsFragment.
         */
        @JvmStatic
        fun newInstance(paramCenoteSaved: CenoteSaved) =
            CenoteSectionsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_CENOTE_SAVED, paramCenoteSaved)
                }
            }
    }
}
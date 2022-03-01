package com.geotec.cenotesapp.ui.cenotes.sections

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenoteSectionListBinding
import com.geotec.cenotesapp.model.CenoteSection
import com.geotec.cenotesapp.ui._utils.BottomMarginListAdapter
import com.geotec.cenotesapp.ui.cenotes.HeaderCenoteViewsAdapter
import com.geotec.cenotesapp.ui.cenotes.HeaderCenoteViewsListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteSectionListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteSectionListFragment : Fragment(), HeaderCenoteViewsListener, ItemCenoteSectionListener {
    // variable para acceder al contenido de las vistas
    private var _v: FragmentCenoteSectionListBinding? = null
    private val v get() = _v!!

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _v = FragmentCenoteSectionListBinding.inflate(inflater, container, false)
        return v.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        v.rvHeaderContent.layoutManager =  LinearLayoutManager(context)
        v.rvCenoteSectionsList.layoutManager = LinearLayoutManager(context)
        prepareListAdapter()
    }

    private fun prepareListAdapter() {
        v.rvHeaderContent.adapter = HeaderCenoteViewsAdapter(this, getString(R.string.cenote_saved_item_name))
        v.rvCenoteSectionsList.adapter = ConcatAdapter(
            ItemCenoteSectionAdapter(this, getModelList()),
            BottomMarginListAdapter()
        )
    }

    override fun onClickSection(navigation: Int) {
        findNavController().navigate(navigation)
    }

    override fun onClickSectionInactive() {
        Toast.makeText(
            this.context,
            getString(R.string.action_cenote_section_inactive, getString(R.string.cenote_section_general_title)),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onClickCloseView() {
        findNavController().navigateUp()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CenoteSectionListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CenoteSectionListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getModelList(): ArrayList<CenoteSection> {
        val list = ArrayList<CenoteSection>()

        list.add(CenoteSection(
            getString(R.string.cenote_section_general_title),
            R.id.action_cenoteSectionListFragment_to_cenoteSectionGeneralFragment,
            true,
            1,
            1
        ))
        list.add(CenoteSection(
            getString(R.string.cenote_section_access_title),
            R.id.action_cenoteSectionListFragment_to_cenoteSectionAccessFragment,
            true,
            0,
            1
        ))
        list.add(CenoteSection(
            getString(R.string.cenote_section_clasifi_title),
            R.id.action_cenoteSectionListFragment_to_cenoteSectionClasifiFragment,
            true,
            0,
            1
        ))
        list.add(CenoteSection(
            getString(R.string.cenote_section_morfo_title),
            R.id.action_cenoteSectionListFragment_to_cenoteSectionMorfoFragment,
            true,
            0,
            1
        ))
        list.add(CenoteSection(
            getString(R.string.cenote_section_uso_title),
            R.id.action_cenoteSectionListFragment_to_cenoteSectionUsoFragment,
            true,
            0,
            1
        ))
        list.add(CenoteSection(
            getString(R.string.cenote_section_problem_title),
            R.id.action_cenoteSectionListFragment_to_cenoteSectionProblemFragment,
            true,
            0,
            1
        ))
        list.add(CenoteSection(
            getString(R.string.cenote_section_gestion_title),
            R.id.action_cenoteSectionListFragment_to_cenoteSectionGestionFragment,
            true,
            0,
            1
        ))
        list.add(CenoteSection(
            getString(R.string.cenote_section_photos_title),
            R.id.action_cenoteSectionListFragment_to_cenoteSectionPhotosFragment,
            true,
            0,
            1
        ))

        return list
    }
}
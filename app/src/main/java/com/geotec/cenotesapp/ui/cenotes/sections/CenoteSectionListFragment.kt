package com.geotec.cenotesapp.ui.cenotes.sections

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenoteSectionListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteSectionListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteSectionListFragment : Fragment() {
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

        v.rvCenoteSectionsList.layoutManager = LinearLayoutManager(context)
        prepareListAdapter()
    }

    private fun prepareListAdapter() {
        v.rvCenoteSectionsList.adapter = CenoteSectionListAdapter(getModelList())
    }

    private fun getModelList(): ArrayList<String> {
        val list = ArrayList<String>()
        list.add(getString(R.string.cenote_section_general_title))
        list.add(getString(R.string.cenote_section_access_title))
        list.add(getString(R.string.cenote_section_clasifi_title))
        list.add(getString(R.string.cenote_section_morfo_title))
        list.add(getString(R.string.cenote_section_uso_title))
        list.add(getString(R.string.cenote_section_problem_title))
        list.add(getString(R.string.cenote_section_gestion_title))
        list.add(getString(R.string.cenote_section_photos_title))
        return list
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
}
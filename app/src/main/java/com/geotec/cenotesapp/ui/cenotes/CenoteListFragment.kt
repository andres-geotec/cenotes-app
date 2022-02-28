package com.geotec.cenotesapp.ui.cenotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentCenoteListBinding
import com.geotec.cenotesapp.ui._utils.BottomMarginListAdapter
import com.geotec.cenotesapp.ui.cenotes.sections.ItemCenoteSavedAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteListFragment : Fragment() {
    // variable para acceder al contenido de las vistas
    private var _v: FragmentCenoteListBinding? = null
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
        _v = FragmentCenoteListBinding.inflate(inflater, container, false)
        return v.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        v.btnAddNewCenote.setOnClickListener {
            toCenoteSectionsFragment()
        }

        v.rvCenoteSavedList.layoutManager = LinearLayoutManager(context)
        prepareListAdapter()
    }

    private fun prepareListAdapter() {
        v.rvCenoteSavedList.adapter = ConcatAdapter(
            ItemCenoteSavedAdapter(ArrayList((1..20).toList())),
            BottomMarginListAdapter()
        )
    }

    private fun toCenoteSectionsFragment() {
        findNavController().navigate(
            R.id.action_cenoteListFragment_to_cenoteSectionListFragment
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CenoteListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CenoteListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
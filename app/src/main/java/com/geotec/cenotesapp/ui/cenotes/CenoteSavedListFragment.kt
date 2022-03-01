package com.geotec.cenotesapp.ui.cenotes

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
import com.geotec.cenotesapp.databinding.FragmentCenoteSavedListBinding
import com.geotec.cenotesapp.ui._utils.BottomMarginListAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteSavedListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteSavedListFragment : Fragment(), ItemCenoteSavedListener {
    // variable para acceder al contenido de las vistas
    private var _v: FragmentCenoteSavedListBinding? = null
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
        _v = FragmentCenoteSavedListBinding.inflate(inflater, container, false)
        return v.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        v.btnAddNewCenote.setOnClickListener {
            toCenoteSectionsFragment()
        }

        v.rvHeaderContent.layoutManager =  LinearLayoutManager(context)
        v.rvCenoteSavedList.layoutManager = LinearLayoutManager(context)
        prepareListAdapter()
    }

    private fun prepareListAdapter() {
        v.rvHeaderContent.adapter = HeaderCenoteViewsAdapter(null, getString(R.string.cenote_saved_list_title))
        v.rvCenoteSavedList.adapter = ConcatAdapter(
            ItemCenoteSavedAdapter(this, ArrayList((1..10).toList())),
            BottomMarginListAdapter()
        )
    }

    override fun onCenoteEdit(id: Int) {
        toCenoteSectionsFragment()
    }

    override fun onCenoteExport(id: Int) {
        Toast.makeText(
            this.context,
            "$id - ${getString(R.string.cenote_saved_item_name)}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCenoteDelete(id: Int) {
        TODO("Not yet implemented")
    }

    private fun toCenoteSectionsFragment() {
        findNavController().navigate(
            R.id.action_cenoteSavedListFragment_to_cenoteSectionListFragment
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
            CenoteSavedListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
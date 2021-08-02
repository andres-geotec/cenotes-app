package com.geotec.cenotesapp.ui.inicio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.FragmentToolbarMainBinding


/**
 * A simple [Fragment] subclass.
 */
class ToolbarMainFragment : Fragment() {
    private var _bv : FragmentToolbarMainBinding? = null
    private val bv get() = _bv!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _bv = FragmentToolbarMainBinding.inflate(inflater, container, false)

        bv.btnInfoMain.setOnClickListener {
            Toast.makeText(this.context, R.string.appName, Toast.LENGTH_SHORT).show()
        }

        return bv.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _bv = null
    }
}
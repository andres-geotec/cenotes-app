package com.geotec.cenotesapp.ui._utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.databinding.BottomMarginListBinding

class BottomMarginListAdapter: RecyclerView.Adapter<BottomMarginListAdapter.ViewHolder>() {
    inner class ViewHolder(v: BottomMarginListBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        BottomMarginListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    override fun getItemCount(): Int = 1
}
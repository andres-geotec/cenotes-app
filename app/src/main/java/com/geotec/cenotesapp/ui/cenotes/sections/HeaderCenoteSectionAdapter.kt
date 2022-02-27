package com.geotec.cenotesapp.ui.cenotes.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.databinding.HeaderCenoteSectionBinding

class HeaderCenoteSectionAdapter: RecyclerView.Adapter<HeaderCenoteSectionAdapter.ViewHolder>() {
    inner class ViewHolder(v: HeaderCenoteSectionBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        HeaderCenoteSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    override fun getItemCount(): Int = 1
}
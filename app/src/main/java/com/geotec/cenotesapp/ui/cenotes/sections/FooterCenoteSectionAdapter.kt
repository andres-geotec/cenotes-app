package com.geotec.cenotesapp.ui.cenotes.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.databinding.FooterCenoteSectionBinding

class FooterCenoteSectionAdapter: RecyclerView.Adapter<FooterCenoteSectionAdapter.ViewHolder>() {
    inner class ViewHolder(v: FooterCenoteSectionBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FooterCenoteSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    override fun getItemCount(): Int = 1
}
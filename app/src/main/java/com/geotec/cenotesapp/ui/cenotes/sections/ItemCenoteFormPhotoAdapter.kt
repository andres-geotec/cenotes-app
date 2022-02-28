package com.geotec.cenotesapp.ui.cenotes.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.databinding.ItemCenoteFormPhotoBinding

class ItemCenoteFormPhotoAdapter: RecyclerView.Adapter<ItemCenoteFormPhotoAdapter.ViewHolder>() {
    inner class ViewHolder(v: ItemCenoteFormPhotoBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCenoteFormPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    override fun getItemCount(): Int = 1
}
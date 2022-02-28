package com.geotec.cenotesapp.ui.cenotes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.databinding.ItemCenoteSavedBinding

class ItemCenoteSavedAdapter(
    private val items: ArrayList<Int>
): RecyclerView.Adapter<ItemCenoteSavedAdapter.ViewHolder>() {
    inner class ViewHolder(val v: ItemCenoteSavedBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCenoteSavedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                v.txtCenoteName.text = "$position - Nombre del cenote"
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
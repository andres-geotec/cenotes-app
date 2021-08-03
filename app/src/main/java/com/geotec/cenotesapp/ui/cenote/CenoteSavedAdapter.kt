package com.geotec.cenotesapp.ui.cenote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.R

class CenoteSavedAdapter(val context: Context, val items: ArrayList<String>)
    : RecyclerView.Adapter<CenoteSavedAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNameCenoteSaved : TextView
        init {
            txtNameCenoteSaved = view.findViewById(R.id.txtNameCenoteSaved)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_cenote_saved,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNameCenoteSaved.text = items[position]
    }

    override fun getItemCount() = items.size
}
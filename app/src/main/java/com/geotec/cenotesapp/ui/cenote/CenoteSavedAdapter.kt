package com.geotec.cenotesapp.ui.cenote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.ItemCenoteSavedBinding
import com.geotec.cenotesapp.model.CenoteSaved
import java.text.SimpleDateFormat

class CenoteSavedAdapter(val items: ArrayList<CenoteSaved>): RecyclerView.Adapter<CenoteSavedAdapter.ViewHolder>() {

    inner class ViewHolder(val bv: ItemCenoteSavedBinding): RecyclerView.ViewHolder(bv.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCenoteSavedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                bv.txtNameCenoteSaved.text = this.nombre
                bv.txtClaveCenoteSaved.text = this.clave
                bv.txtDateCenoteSaved.text = SimpleDateFormat("MM/dd/yyyy - HH:mm").format(this.fecha)
                bv.txtDomicilioenoteSaved.text = this.domicilio
            }
        }
    }

    override fun getItemCount() = items.size
}
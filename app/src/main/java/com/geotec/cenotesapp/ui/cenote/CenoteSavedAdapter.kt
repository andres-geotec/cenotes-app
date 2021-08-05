package com.geotec.cenotesapp.ui.cenote

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.databinding.ItemCenoteSavedBinding
import com.geotec.cenotesapp.model.CenoteSaved
import java.text.SimpleDateFormat

class CenoteSavedAdapter(val listener: CenoteSavedListener, val items: ArrayList<CenoteSaved>): RecyclerView.Adapter<CenoteSavedAdapter.ViewHolder>() {

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
                itemView.setOnClickListener {
                    listener.onCenoteSavedClick(this)
                }
            }
        }
    }

    override fun getItemCount() = items.size
}
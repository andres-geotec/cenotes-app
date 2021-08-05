package com.geotec.cenotesapp.ui.cenote.sections

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.databinding.ItemCenoteSectionsBinding
import com.geotec.cenotesapp.model.CenoteSection

class CenoteSectionsAdapter(
    val listener: CenoteSectionsListener,
    val items: ArrayList<CenoteSection>
): RecyclerView.Adapter<CenoteSectionsAdapter.ViewHolder>() {

    inner class ViewHolder(val bv: ItemCenoteSectionsBinding): RecyclerView.ViewHolder(bv.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCenoteSectionsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                bv.txtSectionName.text = this.nombre
                bv.txtSectionProgress.text = "${this.avance} / ${this.total}"
                bv.pbrSectionProgress.progress = this.getProgress()
                itemView.setOnClickListener {
                    listener.onCenoteSectionClick(this)
                }
            }
        }
    }

    override fun getItemCount() = items.size
}
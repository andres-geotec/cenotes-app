package com.geotec.cenotesapp.ui.cenotes.sections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.databinding.ItemCenoteSectionBinding

class CenoteSectionListAdapter(
    private val listener: CenoteSectionListListener,
    private val items: ArrayList<String>
): RecyclerView.Adapter<CenoteSectionListAdapter.ViewHolder>() {
    inner class ViewHolder(val v: ItemCenoteSectionBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCenoteSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                v.txtSectionName.text = this
                v.pbrSectionProgress.progress = 10
                itemView.setOnClickListener {
                    listener.onClickSection()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
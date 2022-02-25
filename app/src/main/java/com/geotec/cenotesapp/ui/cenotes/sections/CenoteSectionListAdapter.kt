package com.geotec.cenotesapp.ui.cenotes.sections

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.databinding.ItemCenoteSectionBinding
import com.geotec.cenotesapp.model.CenoteSection

class CenoteSectionListAdapter(
    private val listener: CenoteSectionListListener,
    private val items: ArrayList<CenoteSection>
): RecyclerView.Adapter<CenoteSectionListAdapter.ViewHolder>() {
    inner class ViewHolder(val v: ItemCenoteSectionBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCenoteSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                v.txtSectionName.text = name
                v.txtSectionProgress.text = "$advance / $total"

                if (active) {
                    v.pbrSectionProgress.progress = getProgress()
                    itemView.setOnClickListener {
                        listener.onClickSection(navigation)
                    }
                } else {
                    v.txtSectionName.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                    v.pbrSectionProgress.progress = 0
                    v.txtSectionProgress.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                    itemView.setOnClickListener {
                        listener.onClickSectionInactive()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
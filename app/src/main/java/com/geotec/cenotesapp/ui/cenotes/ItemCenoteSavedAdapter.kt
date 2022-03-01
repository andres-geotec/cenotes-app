package com.geotec.cenotesapp.ui.cenotes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.ItemCenoteSavedBinding

class ItemCenoteSavedAdapter(
    private val listener: ItemCenoteSavedListener,
    private val items: ArrayList<Int>
): RecyclerView.Adapter<ItemCenoteSavedAdapter.ViewHolder>() {
    inner class ViewHolder(val v: ItemCenoteSavedBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCenoteSavedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val popupMenu = PopupMenu(v.root.context, v.btnCenoteSavedMenu)
            popupMenu.inflate(R.menu.menu_cenote_saved)
            with(items[position]) {
                v.txtCenoteName.text = "$position - Nombre del cenote"
                itemView.setOnClickListener {
                    listener.onCenoteEdit(position)
                }
                v.btnCenoteSavedMenu.setOnClickListener {
                    popupMenu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.opCenoteEdit -> listener.onCenoteEdit(position)
                            R.id.opCenoteExport -> listener.onCenoteExport(position)
                            R.id.opCenoteDelete -> listener.onCenoteDelete(position)
                        }
                        true
                    }
                    popupMenu.show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
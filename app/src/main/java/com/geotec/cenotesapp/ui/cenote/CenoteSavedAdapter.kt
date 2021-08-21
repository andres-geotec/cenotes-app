package com.geotec.cenotesapp.ui.cenote

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.ItemCenoteSavedBinding
import com.geotec.cenotesapp.model.CenoteSaved
import java.text.SimpleDateFormat

class CenoteSavedAdapter(
    val listener: CenoteSavedListener,
    val items: ArrayList<CenoteSaved>
): RecyclerView.Adapter<CenoteSavedAdapter.ViewHolder>() {

    inner class ViewHolder(val v: ItemCenoteSavedBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCenoteSavedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val popupMenu = PopupMenu(v.root.context, v.menuCenoteSaved)
            popupMenu.inflate(R.menu.menu_cenote_saved)
            if (position == items.lastIndex) {
                val params = this.itemView.layoutParams as RecyclerView.LayoutParams
                params.bottomMargin = 200
                this.itemView.layoutParams = params
            }
            with(items[position]) {
                v.txtNameCenoteSaved.text = this.nombre
                v.txtClaveCenoteSaved.text = this.clave
                v.txtDateCenoteSaved.text = SimpleDateFormat("MM/dd/yyyy - HH:mm").format(this.fecha)
                v.txtDomicilioCenoteSaved.text = this.domicilio
                itemView.setOnClickListener {
                    listener.onCenoteSavedClick(this)
                }
                v.menuCenoteSaved.setOnClickListener {
                    popupMenu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.opExport -> listener.onExport(this)
                            R.id.opDelete -> listener.onDelete(this)
                            R.id.opEdit -> listener.onCenoteSavedClick(this)
                        }
                        true
                    }
                    popupMenu.show()
                }
            }
        }
    }

    override fun getItemCount() = items.size
}
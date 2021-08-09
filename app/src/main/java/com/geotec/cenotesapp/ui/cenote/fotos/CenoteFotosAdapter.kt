package com.geotec.cenotesapp.ui.cenote.fotos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.databinding.FooterCenotesFotosBinding
import com.geotec.cenotesapp.databinding.HeaderCenoteFotosBinding
import com.geotec.cenotesapp.databinding.ItemCenoteFotoBinding
import com.geotec.cenotesapp.model.CenoteFoto

class CenoteFotosAdapter(
    private val listener: CenoteFotosListener,
    private val items: ArrayList<CenoteFoto>
): RecyclerView.Adapter<CenoteFotosAdapter.ViewHolder>() {

    inner class ViewHolder(val v: ItemCenoteFotoBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCenoteFotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            v.txtFotoDesc.setText(position.toString())
        }
    }

    override fun getItemCount() = items.size
}

class HeaderCenoteFotosAdapter: RecyclerView.Adapter<HeaderCenoteFotosAdapter.ViewHolder>() {

    inner class ViewHolder(v: HeaderCenoteFotosBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        HeaderCenoteFotosBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    override fun getItemCount() = 1
}

class FooterCenoteFotosAdapter: RecyclerView.Adapter<FooterCenoteFotosAdapter.ViewHolder>() {

    inner class ViewHolder(val v: FooterCenotesFotosBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FooterCenotesFotosBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            v.btnSaveData.setOnClickListener {
                println("guardar datos")
            }
        }
    }

    override fun getItemCount() = 1
}

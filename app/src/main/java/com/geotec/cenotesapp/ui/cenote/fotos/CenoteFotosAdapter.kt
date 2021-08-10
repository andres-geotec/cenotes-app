package com.geotec.cenotesapp.ui.cenote.fotos

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
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
            with(items[position]) {
                v.imgCenotePhoto.setImageBitmap(prepareImage(ruta.toString()))
                v.txtFotoDesc.setText(desc)
                v.txtFotoDesc.doOnTextChanged { text, _, _, _ ->
                    // println("$text, $start, $before, $count")
                    listener.onChangeText(position, text.toString())
                }
            }
        }
    }

    override fun getItemCount() = items.size

    fun prepareImage(path: String): Bitmap {
        val img = BitmapFactory.decodeFile(path)
        println(img.width)
        println(img.height)
        return img
    }
}

class HeaderCenoteFotosAdapter: RecyclerView.Adapter<HeaderCenoteFotosAdapter.ViewHolder>() {

    inner class ViewHolder(v: HeaderCenoteFotosBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        HeaderCenoteFotosBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    override fun getItemCount() = 1
}

class FooterCenoteFotosAdapter(
    private val listener: CenoteFotosListener
): RecyclerView.Adapter<FooterCenoteFotosAdapter.ViewHolder>() {

    inner class ViewHolder(val v: FooterCenotesFotosBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FooterCenotesFotosBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            v.btnSaveData.setOnClickListener {
                listener.onSavePhotos()
            }
        }
    }

    override fun getItemCount() = 1
}

package com.geotec.cenotesapp.ui.cenotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.databinding.HeaderCenoteViewsBinding

class HeaderCenoteViewsAdapter(
    private val listener: HeaderCenoteViewsListener?,
    private val title: String,
): RecyclerView.Adapter<HeaderCenoteViewsAdapter.ViewHolder>() {
    inner class ViewHolder(val v: HeaderCenoteViewsBinding): RecyclerView.ViewHolder(v.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderCenoteViewsAdapter.ViewHolder {
        return ViewHolder(HeaderCenoteViewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            v.txtTitleView.text = title
            if (listener == null) {
                v.btnCloseView.visibility = View.GONE
            } else {
                v.btnCloseView.setOnClickListener {
                    listener.onClickCloseView()
                }
            }
        }
    }

    override fun getItemCount(): Int = 1
}
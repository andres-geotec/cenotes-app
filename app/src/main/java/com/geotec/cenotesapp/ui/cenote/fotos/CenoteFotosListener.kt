package com.geotec.cenotesapp.ui.cenote.fotos

import com.geotec.cenotesapp.model.CenoteFoto

interface CenoteFotosListener {
    fun onSavePhotos()
    fun onChangeText(idx: Int, newText: String)
}
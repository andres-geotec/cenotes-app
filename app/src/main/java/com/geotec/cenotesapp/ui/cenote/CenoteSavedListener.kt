package com.geotec.cenotesapp.ui.cenote

import com.geotec.cenotesapp.model.CenoteSaved

interface CenoteSavedListener {
    fun onCenoteSavedClick(cenoteSaved: CenoteSaved)
    fun onExport(cenoteSaved: CenoteSaved)
    fun onDelete(cenoteSaved: CenoteSaved)
}
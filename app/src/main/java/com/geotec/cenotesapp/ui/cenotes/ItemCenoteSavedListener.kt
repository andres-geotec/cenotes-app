package com.geotec.cenotesapp.ui.cenotes

interface ItemCenoteSavedListener {
    fun onCenoteEdit(id: Int)
    fun onCenoteExport(id: Int)
    fun onCenoteDelete(id: Int)
}
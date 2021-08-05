package com.geotec.cenotesapp.model

import java.io.Serializable

class CenoteSection (nombre: String, navigate: Int, total: Int, avance: Int): Serializable {
    val nombre: String = nombre
    var navigate: Int = navigate
    val total: Int = total
    var avance: Int = avance
    fun getProgress() = ((avance.toFloat() / total) * 100).toInt()
}
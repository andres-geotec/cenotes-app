package com.geotec.cenotesapp.model

import java.util.*

class CenoteFotosSec(clave: String) {
    val clave: String = clave
    var nombre: String? = null
    var desc: String? = null
    var ruta: String? = null
    var saved: Boolean = false
    var timestamp: Date = Date()
}
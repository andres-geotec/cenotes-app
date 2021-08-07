package com.geotec.cenotesapp.model

import java.util.*

class CenoteClasifiSec(clave: String) {
    var clave: String = clave
    var genesis: String? = null
    var geoforma: String? = null
    var tipo: String? = null
    var apertura: String? = null
    var cuerpoAgua: String? = null
    var saved: Boolean = false
    var timestamp: Date = Date()
}
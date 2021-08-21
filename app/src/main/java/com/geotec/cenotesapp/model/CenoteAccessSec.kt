package com.geotec.cenotesapp.model

import java.util.*

class CenoteAccessSec(val clave: String) {
    lateinit var accesible: String
    var tipo: String? = null
    var descripcion: String? = null
    var saved: Boolean = false
    var timestamp: Date = Date()
}
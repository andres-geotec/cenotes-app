package com.geotec.cenotesapp.model

import java.util.*

class CenoteFoto(var id: Int, val clave: String) {
    var nombre: String? = null
    var desc: String? = null
    var ruta: String? = null
    var saved: Boolean = false
    var timestamp: Date = Date()
}
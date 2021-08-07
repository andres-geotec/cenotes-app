package com.geotec.cenotesapp.model

import java.util.*

class CenoteProblemSec(clave: String) {
    var clave: String = clave
    var contaminacion: String? = null
    var vertederos: String? = null
    var movimientos: String? = null
    var depresiones: String? = null
    var visitas_masivas: String? = null
    var usos_previos: String? = null
    var saved: Boolean = false
    var timestamp: Date = Date()
}
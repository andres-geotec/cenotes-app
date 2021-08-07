package com.geotec.cenotesapp.model

import java.util.*

class CenoteUsoSec(clave: String) {
    var clave: String = clave
    var usoActual: String? = null
    var densidadUrbana: String? = null
    var tipoVialidad: String? = null
    var serviciosPublicos: String? = null
    var ecosistema: String? = null
    var saved: Boolean = false
    var timestamp: Date = Date()
}
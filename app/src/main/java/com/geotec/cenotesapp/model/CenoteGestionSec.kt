package com.geotec.cenotesapp.model

import java.util.*

class CenoteGestionSec(clave: String) {
    val clave: String = clave
    var estado_cenote: String? = null
    var respuesta_estado: String? = null
    var agentes: String? = null
    var saved: Boolean = false
    var timestamp: Date = Date()
}
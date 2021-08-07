package com.geotec.cenotesapp.model

import java.util.*

class CenoteUsoSec(clave: String) {
    var clave: String = clave
    var uso_actual: String? = null
    var densidad_urbana: String? = null
    var tipo_vialidad: String? = null
    var servicios_publicos: String? = null
    var ecosistema: String? = null
    var saved: Boolean = false
    var timestamp: Date = Date()
}
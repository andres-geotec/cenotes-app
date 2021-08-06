package com.geotec.cenotesapp.model

import java.util.*

class CenoteGeneralSec(clave: String) {
    var clave: String = clave
    var entreCalle1: String? = null
    var entreCalle2: String? = null
    var ageb: String? = null
    var longitude: Double? = null
    var latitude: Double? = null
    var saved: Boolean = false
    var timestamp: Date = Date()
}
package com.geotec.cenotesapp.model

import java.util.*

class CenoteMorfoSec(clave: String) {
    var clave: String = clave
    var area: Float? = null
    var perimetro: Float? = null
    var profundidad: Float? = null
    var semiejeMayor: Float? = null
    var semiejeMenor: Float? = null
    var elongacion: Float? = null
    var saved: Boolean = false
    var timestamp: Date = Date()
}
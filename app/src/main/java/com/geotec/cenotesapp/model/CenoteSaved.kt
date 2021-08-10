package com.geotec.cenotesapp.model

import java.io.Serializable
import java.util.*

class CenoteSaved(): Serializable {
    var id: Int? = null
    lateinit var clave: String
    var nombre: String? = null
    var domicilio: String? = null
    var fecha: Date = Date()
    var progreso_general: Int = 0
    var progreso_clasifi: Int = 0
    var progreso_morfo: Int = 0
    var progreso_uso: Int = 0
    var progreso_problem: Int = 0
    var progreso_gestion: Int = 0
    var progreso_fotos: Int = 0
    var exported: Boolean = false
    var saved: Boolean = false
}
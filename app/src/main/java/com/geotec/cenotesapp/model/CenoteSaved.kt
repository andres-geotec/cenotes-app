package com.geotec.cenotesapp.model

import java.io.Serializable
import java.util.*

class CenoteSaved: Serializable {
    lateinit var id: String
    lateinit var clave: String
    lateinit var nombre: String
    lateinit var domicilio: String
    lateinit var fecha: Date
    var progreso_general: Int = 0
    var progreso_clasifi: Int = 0
    var progreso_morfo: Int = 0
    var progreso_uso: Int = 0
    var progreso_problem: Int = 0
    var progreso_gestion: Int = 0
    var progreso_fotos: Int = 0
    var saved: Boolean = false
}
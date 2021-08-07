package com.geotec.cenotesapp.sqlite

import android.provider.BaseColumns

class CenoteReaderContract {
    // TODO: Columnas base del cenote guardado
    object CenoteAlta: BaseColumns {
        const val TABLE_NAME = "cenotes_alta"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_CVE = "clave"
        const val COLUMN_NAME_NOMBRE = "nombre"
        const val COLUMN_NAME_DOMICILIO = "domicilio"
        const val COLUMN_NAME_FECHA = "fecha"
        const val COLUMN_NAME_PROGRESO_GENERAL = "progreso_general"
        const val COLUMN_NAME_PROGRESO_CLASIFI = "progreso_clasifi"
        const val COLUMN_NAME_PROGRESO_MORFO = "progreso_morfo"
        const val COLUMN_NAME_PROGRESO_USO = "progreso_uso"
        const val COLUMN_NAME_PROGRESO_PROBLEM = "progreso_problem"
        const val COLUMN_NAME_PROGRESO_GESTION = "progreso_gestion"
        const val COLUMN_NAME_PROGRESO_FOTOS = "progreso_fotos"
    }
    val SQL_CREATE_TABLE_CENOTE_ALTA =
        "CREATE TABLE ${CenoteAlta.TABLE_NAME} (" +
            "${CenoteAlta.COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
            "${CenoteAlta.COLUMN_NAME_CVE} TEXT," +
            "${CenoteAlta.COLUMN_NAME_NOMBRE} TEXT," +
            "${CenoteAlta.COLUMN_NAME_DOMICILIO} TEXT," +
            "${CenoteAlta.COLUMN_NAME_FECHA} DATE," +
            "${CenoteAlta.COLUMN_NAME_PROGRESO_GENERAL} INTEGER," +
            "${CenoteAlta.COLUMN_NAME_PROGRESO_CLASIFI} INTEGER," +
            "${CenoteAlta.COLUMN_NAME_PROGRESO_MORFO} INTEGER," +
            "${CenoteAlta.COLUMN_NAME_PROGRESO_USO} INTEGER," +
            "${CenoteAlta.COLUMN_NAME_PROGRESO_PROBLEM} INTEGER," +
            "${CenoteAlta.COLUMN_NAME_PROGRESO_GESTION} INTEGER," +
            "${CenoteAlta.COLUMN_NAME_PROGRESO_FOTOS} INTEGER)"
    val SQL_DELETE_TABLE_CENOTE_ALTA = "DROP TABLE IF EXISTS ${CenoteAlta.TABLE_NAME}"

    // TODO: Columnas de la sección Datos generales
    object CenoteGeneralSec: BaseColumns {
        const val TABLE_NAME = "cenotes_general_sec"
        const val COLUMN_NAME_CVE = "clave"
        const val COLUMN_NAME_CALLE1 = "calle1"
        const val COLUMN_NAME_CALLE2 = "calle2"
        const val COLUMN_NAME_AGEB = "ageb"
        const val COLUMN_NAME_LNG = "longitude"
        const val COLUMN_NAME_LAT = "latitude"
        const val COLUMN_NAME_TIMESTAMP = "timestamp"
    }
    val SQL_CREATE_TABLE_CENOTE_GENERAL_SEC =
        "CREATE TABLE ${CenoteGeneralSec.TABLE_NAME} (" +
                "${CenoteGeneralSec.COLUMN_NAME_CVE} TEXT PRIMARY KEY," +
                "${CenoteGeneralSec.COLUMN_NAME_CALLE1} TEXT," +
                "${CenoteGeneralSec.COLUMN_NAME_CALLE2} TEXT," +
                "${CenoteGeneralSec.COLUMN_NAME_AGEB} TEXT," +
                "${CenoteGeneralSec.COLUMN_NAME_LNG} REAL," +
                "${CenoteGeneralSec.COLUMN_NAME_LAT} REAL," +
                "${CenoteGeneralSec.COLUMN_NAME_TIMESTAMP} DATE)"
    val SQL_DELETE_TABLE_CENOTE_GENERAL_SEC = "DROP TABLE IF EXISTS ${CenoteGeneralSec.TABLE_NAME}"

    // TODO: Columnas de la sección Clasificación
    object CenoteClasifiSec: BaseColumns {
        const val TABLE_NAME = "cenotes_clasifi_sec"
        const val COLUMN_NAME_CVE = "clave"
        const val COLUMN_NAME_GENESIS = "genesis"
        const val COLUMN_NAME_GEOFORMA = "geoforma"
        const val COLUMN_NAME_TIPO = "tipo"
        const val COLUMN_NAME_APERTURA = "apertura"
        const val COLUMN_NAME_CUERPO_AGUA = "cuerpo_agua"
        const val COLUMN_NAME_TIMESTAMP = "timestamp"
    }
    val SQL_CREATE_TABLE_CENOTE_CLASIFI_SEC =
        "CREATE TABLE ${CenoteClasifiSec.TABLE_NAME} (" +
                "${CenoteClasifiSec.COLUMN_NAME_CVE} TEXT PRIMARY KEY," +
                "${CenoteClasifiSec.COLUMN_NAME_GENESIS} TEXT," +
                "${CenoteClasifiSec.COLUMN_NAME_GEOFORMA} TEXT," +
                "${CenoteClasifiSec.COLUMN_NAME_TIPO} TEXT," +
                "${CenoteClasifiSec.COLUMN_NAME_APERTURA} TEXT," +
                "${CenoteClasifiSec.COLUMN_NAME_CUERPO_AGUA} TEXT," +
                "${CenoteClasifiSec.COLUMN_NAME_TIMESTAMP} DATE)"
    val SQL_DELETE_TABLE_CENOTE_CLASIFI_SEC = "DROP TABLE IF EXISTS ${CenoteClasifiSec.TABLE_NAME}"

    // TODO: Columnas de la sección Morfometría
    object CenoteMorfoSec: BaseColumns {
        const val TABLE_NAME = "cenotes_morfo_sec"
        const val COLUMN_NAME_CVE = "clave"
        const val COLUMN_NAME_AREA = "area"
        const val COLUMN_NAME_PERIMETRO = "perimetro"
        const val COLUMN_NAME_PROFUNDIDAD = "profundidad"
        const val COLUMN_NAME_S_MAYOR = "semieje_mayor"
        const val COLUMN_NAME_S_MENOR = "semieje_menor"
        const val COLUMN_NAME_ELONGACION = "elongacion"
        const val COLUMN_NAME_TIMESTAMP = "timestamp"
    }
    val SQL_CREATE_TABLE_CENOTE_MORFO_SEC =
        "CREATE TABLE ${CenoteMorfoSec.TABLE_NAME} (" +
                "${CenoteMorfoSec.COLUMN_NAME_CVE} TEXT PRIMARY KEY," +
                "${CenoteMorfoSec.COLUMN_NAME_AREA} REAL," +
                "${CenoteMorfoSec.COLUMN_NAME_PERIMETRO} REAL," +
                "${CenoteMorfoSec.COLUMN_NAME_PROFUNDIDAD} REAL," +
                "${CenoteMorfoSec.COLUMN_NAME_S_MAYOR} REAL," +
                "${CenoteMorfoSec.COLUMN_NAME_S_MENOR} REAL," +
                "${CenoteMorfoSec.COLUMN_NAME_ELONGACION} REAL," +
                "${CenoteMorfoSec.COLUMN_NAME_TIMESTAMP} DATE)"
    val SQL_DELETE_TABLE_CENOTE_MORFO_SEC = "DROP TABLE IF EXISTS ${CenoteMorfoSec.TABLE_NAME}"

    // TODO: Columnas de la sección Uso actual
    object CenoteUsoSec: BaseColumns {
        const val TABLE_NAME = "cenotes_uso_sec"
        const val COLUMN_NAME_CVE = "clave"
        const val COLUMN_NAME_USO_ACTUAL = "uso_actual"
        const val COLUMN_NAME_DENCIDAD_URBANA = "densidad_urbana"
        const val COLUMN_NAME_TIPO_VIALIDAD = "tipo_vialidad"
        const val COLUMN_NAME_SERVICIOS = "servicios_publicos"
        const val COLUMN_NAME_ECOSISTEMA = "ecosistema"
        const val COLUMN_NAME_TIMESTAMP = "timestamp"
    }
    val SQL_CREATE_TABLE_CENOTE_USO_SEC =
        "CREATE TABLE ${CenoteUsoSec.TABLE_NAME} (" +
                "${CenoteUsoSec.COLUMN_NAME_CVE} TEXT PRIMARY KEY," +
                "${CenoteUsoSec.COLUMN_NAME_USO_ACTUAL} TEXT," +
                "${CenoteUsoSec.COLUMN_NAME_DENCIDAD_URBANA} TEXT," +
                "${CenoteUsoSec.COLUMN_NAME_TIPO_VIALIDAD} TEXT," +
                "${CenoteUsoSec.COLUMN_NAME_SERVICIOS} TEXT," +
                "${CenoteUsoSec.COLUMN_NAME_ECOSISTEMA} TEXT," +
                "${CenoteUsoSec.COLUMN_NAME_TIMESTAMP} DATE)"
    val SQL_DELETE_TABLE_CENOTE_USO_SEC = "DROP TABLE IF EXISTS ${CenoteUsoSec.TABLE_NAME}"

    // TODO: Columnas de la sección Uso actual
    object CenoteProblemSec: BaseColumns {
        const val TABLE_NAME = "cenotes_problem_sec"
        const val COLUMN_NAME_CVE = "clave"
        const val COLUMN_NAME_CONTAMINACION = "contaminacion"
        const val COLUMN_NAME_VERTEDEROS = "vertederos"
        const val COLUMN_NAME_MOVIMIENTOS = "movimientos"
        const val COLUMN_NAME_DEPRESIONES = "depresiones"
        const val COLUMN_NAME_VISITAS_MASIVAS = "visitas_masivas"
        const val COLUMN_NAME_USOS_PREVIOS = "usos_previos"
        const val COLUMN_NAME_TIMESTAMP = "timestamp"
    }
    val SQL_CREATE_TABLE_CENOTE_PROBLEM_SEC =
        "CREATE TABLE ${CenoteProblemSec.TABLE_NAME} (" +
                "${CenoteProblemSec.COLUMN_NAME_CVE} TEXT PRIMARY KEY," +
                "${CenoteProblemSec.COLUMN_NAME_CONTAMINACION} TEXT," +
                "${CenoteProblemSec.COLUMN_NAME_VERTEDEROS} TEXT," +
                "${CenoteProblemSec.COLUMN_NAME_MOVIMIENTOS} TEXT," +
                "${CenoteProblemSec.COLUMN_NAME_DEPRESIONES} TEXT," +
                "${CenoteProblemSec.COLUMN_NAME_VISITAS_MASIVAS} TEXT," +
                "${CenoteProblemSec.COLUMN_NAME_USOS_PREVIOS} TEXT," +
                "${CenoteProblemSec.COLUMN_NAME_TIMESTAMP} DATE)"
    val SQL_DELETE_TABLE_CENOTE_PROBLEM_SEC = "DROP TABLE IF EXISTS ${CenoteProblemSec.TABLE_NAME}"
}
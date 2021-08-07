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
}
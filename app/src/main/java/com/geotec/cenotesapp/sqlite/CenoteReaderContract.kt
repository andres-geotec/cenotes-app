package com.geotec.cenotesapp.sqlite

import android.provider.BaseColumns

class CenoteReaderContract {
    object CenoteEntry : BaseColumns {
        const val TABLE_NAME = "cenotes_test"
        const val COLUMN_NAME_CVE = "id"
        const val COLUMN_NAME_NAME = "nombre"
    }
    val SQL_CREATE_TABLE_CENOTE_ENTRIY =
        "CREATE TABLE ${CenoteEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${CenoteEntry.COLUMN_NAME_CVE} TEXT," +
                "${CenoteEntry.COLUMN_NAME_NAME} TEXT)"
    val SQL_DELETE_TABLE_CENOTE_ENTRY = "DROP TABLE IF EXISTS ${CenoteEntry.TABLE_NAME}"
    val COLUMNS_TABLE_CENOTE_ENTRY = arrayOf(
        BaseColumns._ID,
        CenoteEntry.COLUMN_NAME_CVE,
        CenoteEntry.COLUMN_NAME_NAME
    )

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
    val COLUMNS_TABLE_CENOTE_ALTA = arrayOf(
        CenoteAlta.COLUMN_NAME_ID,
        CenoteAlta.COLUMN_NAME_CVE,
        CenoteAlta.COLUMN_NAME_NOMBRE,
        CenoteAlta.COLUMN_NAME_DOMICILIO,
        CenoteAlta.COLUMN_NAME_FECHA,
        CenoteAlta.COLUMN_NAME_PROGRESO_GENERAL,
        CenoteAlta.COLUMN_NAME_PROGRESO_CLASIFI,
        CenoteAlta.COLUMN_NAME_PROGRESO_MORFO,
        CenoteAlta.COLUMN_NAME_PROGRESO_USO,
        CenoteAlta.COLUMN_NAME_PROGRESO_PROBLEM,
        CenoteAlta.COLUMN_NAME_PROGRESO_GESTION,
        CenoteAlta.COLUMN_NAME_PROGRESO_FOTOS
    )
}
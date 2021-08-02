package com.geotec.cenotesapp.sqlite

import android.provider.BaseColumns

class CenoteReaderContract {
    object CenoteEntry : BaseColumns {
        const val TABLE_NAME = "cenotes_test"
        const val COLUMN_NAME_CLAVE = "id"
        const val COLUMN_NAME_NAME = "nombre"
    }

    val SQL_CREATE_TABLE_CENOTES =
        "CREATE TABLE ${CenoteEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${CenoteEntry.COLUMN_NAME_CLAVE} TEXT," +
                "${CenoteEntry.COLUMN_NAME_NAME} TEXT)"

    val SQL_DELETE_TABLE_CENOTES = "DROP TABLE IF EXISTS ${CenoteEntry.TABLE_NAME}"

}
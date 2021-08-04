package com.geotec.cenotesapp.sqlite

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.geotec.cenotesapp.model.CenoteSaved
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SqliteComunicate(context: Context) {
    private var dbHelper: AdminSQLiteOpenHelper = AdminSQLiteOpenHelper(context)
    private val contract: CenoteReaderContract = CenoteReaderContract()

    public fun loadFromSqlite() : ArrayList<String> {
        val db = this.dbHelper.readableDatabase
        val columns = contract.COLUMNS_TABLE_CENOTE_ENTRY
        val cursor = db.query(CenoteReaderContract.CenoteEntry.TABLE_NAME,   // The table to query
            columns, null, null, null, null, null)

        val items = ArrayList<String>()
        with(cursor) {
            while (moveToNext()) {
                // val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                // val itemCve = getLong(getColumnIndexOrThrow(CenoteReaderContract.CenoteEntry.COLUMN_NAME_CVE))
                val itemName = getString(getColumnIndexOrThrow(CenoteReaderContract.CenoteEntry.COLUMN_NAME_NAME))
                // Toast.makeText(this.context, "$itemId: $itemName ($itemCve)", Toast.LENGTH_SHORT).show()
                items.add(itemName)
            }
        }
        return items
    }

    public fun readCenotesSaved(): ArrayList<CenoteSaved> {
        val db = this.dbHelper.readableDatabase
        val columns = contract.COLUMNS_TABLE_CENOTE_ALTA
        val cenoteAlta = CenoteReaderContract.CenoteAlta
        val cursor = db.query(cenoteAlta.TABLE_NAME,   // The table to query
            columns, null, null, null, null, null)

        val list = ArrayList<CenoteSaved>()
        with(cursor) {
            while (moveToNext()) {
                val cenoteSaved = CenoteSaved()
                cenoteSaved.id = getString(getColumnIndexOrThrow(cenoteAlta.COLUMN_NAME_ID))
                cenoteSaved.clave = getString(getColumnIndexOrThrow(cenoteAlta.COLUMN_NAME_CVE))
                cenoteSaved.nombre = getString(getColumnIndexOrThrow(cenoteAlta.COLUMN_NAME_NOMBRE))
                cenoteSaved.fecha = SimpleDateFormat().parse(getString(getColumnIndexOrThrow(cenoteAlta.COLUMN_NAME_FECHA)))
                list.add(cenoteSaved)
            }
        }
        return list
    }
    public fun insertCenoteSaved(cenoteSaved: CenoteSaved): Long? {
        val db = dbHelper.writableDatabase
        val cenoteAlta = CenoteReaderContract.CenoteAlta

        return db?.insert(
            cenoteAlta.TABLE_NAME,
            null,
            ContentValues().apply {
                put(cenoteAlta.COLUMN_NAME_CVE, cenoteSaved.clave)
                put(cenoteAlta.COLUMN_NAME_NOMBRE, cenoteSaved.nombre)
                put(cenoteAlta.COLUMN_NAME_DOMICILIO, cenoteSaved.domicilio)
                put(cenoteAlta.COLUMN_NAME_FECHA, SimpleDateFormat().format(cenoteSaved.fecha))
                put(cenoteAlta.COLUMN_NAME_PROGRESO_GENERAL, cenoteSaved.progreso_general)
                put(cenoteAlta.COLUMN_NAME_PROGRESO_CLASIFI, cenoteSaved.progreso_clasifi)
                put(cenoteAlta.COLUMN_NAME_PROGRESO_MORFO, cenoteSaved.progreso_morfo)
                put(cenoteAlta.COLUMN_NAME_PROGRESO_USO, cenoteSaved.progreso_uso)
                put(cenoteAlta.COLUMN_NAME_PROGRESO_PROBLEM, cenoteSaved.progreso_problem)
                put(cenoteAlta.COLUMN_NAME_PROGRESO_GESTION, cenoteSaved.progreso_gestion)
                put(cenoteAlta.COLUMN_NAME_PROGRESO_FOTOS, cenoteSaved.progreso_fotos)
            })
    }
}
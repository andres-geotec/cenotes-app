package com.geotec.cenotesapp.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.geotec.cenotesapp.model.CenoteGeneralSec
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

    @SuppressLint("SimpleDateFormat")
    public fun readCenotesSaved(): ArrayList<CenoteSaved> {
        val db = this.dbHelper.readableDatabase
        val columns = contract.COLUMNS_TABLE_CENOTE_ALTA
        val table = CenoteReaderContract.CenoteAlta
        val cursor = db.query(table.TABLE_NAME,   // The table to query
            columns, null, null, null, null,
            "${table.COLUMN_NAME_ID} DESC")

        val list = ArrayList<CenoteSaved>()
        with(cursor) {
            while (moveToNext()) {
                val cenoteSaved = CenoteSaved()
                cenoteSaved.id = getInt(getColumnIndexOrThrow(table.COLUMN_NAME_ID))
                cenoteSaved.clave = getString(getColumnIndexOrThrow(table.COLUMN_NAME_CVE))
                cenoteSaved.nombre = getString(getColumnIndexOrThrow(table.COLUMN_NAME_NOMBRE))
                cenoteSaved.domicilio = getString(getColumnIndexOrThrow(table.COLUMN_NAME_DOMICILIO))
                cenoteSaved.fecha = SimpleDateFormat().parse(getString(getColumnIndexOrThrow(table.COLUMN_NAME_FECHA))) as Date
                cenoteSaved.progreso_general = getInt(getColumnIndexOrThrow(table.COLUMN_NAME_PROGRESO_GENERAL))
                cenoteSaved.progreso_clasifi = getInt(getColumnIndexOrThrow(table.COLUMN_NAME_PROGRESO_CLASIFI))
                cenoteSaved.progreso_morfo = getInt(getColumnIndexOrThrow(table.COLUMN_NAME_PROGRESO_MORFO))
                cenoteSaved.progreso_uso = getInt(getColumnIndexOrThrow(table.COLUMN_NAME_PROGRESO_USO))
                cenoteSaved.progreso_problem = getInt(getColumnIndexOrThrow(table.COLUMN_NAME_PROGRESO_PROBLEM))
                cenoteSaved.progreso_gestion = getInt(getColumnIndexOrThrow(table.COLUMN_NAME_PROGRESO_GESTION))
                cenoteSaved.progreso_fotos = getInt(getColumnIndexOrThrow(table.COLUMN_NAME_PROGRESO_FOTOS))
                cenoteSaved.saved = true
                list.add(cenoteSaved)
            }
        }
        return list
    }
    @SuppressLint("SimpleDateFormat")
    private fun contentValuesCenoteSaved(cenoteSaved: CenoteSaved): ContentValues {
        val cenoteAlta = CenoteReaderContract.CenoteAlta
        return ContentValues().apply {
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
        }
    }
    public fun insertCenoteSaved(cenoteSaved: CenoteSaved): Long? {
        val db = dbHelper.writableDatabase
        return db?.insert(
            CenoteReaderContract.CenoteAlta.TABLE_NAME,
            null,
            contentValuesCenoteSaved(cenoteSaved))
    }
    public fun updateCenoteSaved(cenoteSaved: CenoteSaved): Int? {
        val db = dbHelper.writableDatabase
         return db?.update(
             CenoteReaderContract.CenoteAlta.TABLE_NAME,
             contentValuesCenoteSaved(cenoteSaved),
            "${CenoteReaderContract.CenoteAlta.COLUMN_NAME_ID} = ?",
            arrayOf(cenoteSaved.id.toString()))
    }


    @SuppressLint("SimpleDateFormat")
    public fun readCenotesGeneralSec(clave: String?): ArrayList<CenoteGeneralSec> {
        val db = this.dbHelper.readableDatabase
        val columns = contract.COLUMNS_TABLE_CENOTE_GENERAL_SEC
        val table = CenoteReaderContract.CenoteGeneralSec
        val cursor = db.query(table.TABLE_NAME, null,
            if (clave != null) "${CenoteReaderContract.CenoteGeneralSec.COLUMN_NAME_CVE} = ?" else null,
            if (clave != null) arrayOf(clave) else null,
            null, null, null)

        val list = ArrayList<CenoteGeneralSec>()
        with(cursor) {
            while (moveToNext()) {
                val cenoteGeneralSec = CenoteGeneralSec(getString(getColumnIndexOrThrow(table.COLUMN_NAME_CVE)))
                //cenoteSaved.clave = getString(getColumnIndexOrThrow(table.COLUMN_NAME_CVE))
                cenoteGeneralSec.entreCalle1 = getString(getColumnIndexOrThrow(table.COLUMN_NAME_CALLE1))
                cenoteGeneralSec.entreCalle2 = getString(getColumnIndexOrThrow(table.COLUMN_NAME_CALLE2))
                cenoteGeneralSec.ageb = getString(getColumnIndexOrThrow(table.COLUMN_NAME_AGEB))
                cenoteGeneralSec.longitude = getDouble(getColumnIndexOrThrow(table.COLUMN_NAME_LNG))
                cenoteGeneralSec.latitude = getDouble(getColumnIndexOrThrow(table.COLUMN_NAME_LAT))
                cenoteGeneralSec.timestamp = SimpleDateFormat().parse(getString(getColumnIndexOrThrow(table.COLUMN_NAME_TIMESTAMP))) as Date
                cenoteGeneralSec.saved = true
                list.add(cenoteGeneralSec)
            }
        }
        return list
    }
    @SuppressLint("SimpleDateFormat")
    private fun contentValuesCenoteGeneralSec(cenoteGeneralSec: CenoteGeneralSec): ContentValues {
        val table = CenoteReaderContract.CenoteGeneralSec
        return ContentValues().apply {
            put(table.COLUMN_NAME_CVE, cenoteGeneralSec.clave)
            put(table.COLUMN_NAME_CALLE1, cenoteGeneralSec.entreCalle1)
            put(table.COLUMN_NAME_CALLE2, cenoteGeneralSec.entreCalle2)
            put(table.COLUMN_NAME_AGEB, cenoteGeneralSec.ageb)
            put(table.COLUMN_NAME_LNG, cenoteGeneralSec.longitude)
            put(table.COLUMN_NAME_LAT, cenoteGeneralSec.latitude)
            put(table.COLUMN_NAME_TIMESTAMP, SimpleDateFormat().format(cenoteGeneralSec.timestamp))
            // put(table.COLUMN_NAME_FECHA, SimpleDateFormat().format(cenoteGeneralSec.fecha))
        }
    }
    public fun insertCenoteGeneralSec(cenoteGeneralSec: CenoteGeneralSec): Long? {
        val db = dbHelper.writableDatabase
        return db?.insert(
            CenoteReaderContract.CenoteGeneralSec.TABLE_NAME,
            null,
            contentValuesCenoteGeneralSec(cenoteGeneralSec))
    }
    public fun updateCenoteGeneralSec(cenoteGeneralSec: CenoteGeneralSec): Int? {
        val db = dbHelper.writableDatabase
        return db?.update(
            CenoteReaderContract.CenoteGeneralSec.TABLE_NAME,
            contentValuesCenoteGeneralSec(cenoteGeneralSec),
            "${CenoteReaderContract.CenoteGeneralSec.COLUMN_NAME_CVE} = ?",
            arrayOf(cenoteGeneralSec.clave))
    }
}
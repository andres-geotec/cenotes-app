package com.geotec.cenotesapp.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.geotec.cenotesapp.model.CenoteClasifiSec
import com.geotec.cenotesapp.model.CenoteGeneralSec
import com.geotec.cenotesapp.model.CenoteSaved
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SqliteComunicate(context: Context) {
    private var dbHelper: AdminSQLiteOpenHelper = AdminSQLiteOpenHelper(context)

    // TODO: Comunicación con datos base del cenote guardado
    @SuppressLint("SimpleDateFormat")
    fun readCenotesSaved(): ArrayList<CenoteSaved> {
        val db = this.dbHelper.readableDatabase
        val table = CenoteReaderContract.CenoteAlta
        val cursor = db.query(table.TABLE_NAME,   // The table to query
            null, null, null, null, null,
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
    fun insertCenoteSaved(cenoteSaved: CenoteSaved): Long? {
        val db = dbHelper.writableDatabase
        return db?.insert(
            CenoteReaderContract.CenoteAlta.TABLE_NAME,
            null,
            contentValuesCenoteSaved(cenoteSaved))
    }
    fun updateCenoteSaved(cenoteSaved: CenoteSaved): Int? {
        val db = dbHelper.writableDatabase
         return db?.update(
             CenoteReaderContract.CenoteAlta.TABLE_NAME,
             contentValuesCenoteSaved(cenoteSaved),
            "${CenoteReaderContract.CenoteAlta.COLUMN_NAME_ID} = ?",
            arrayOf(cenoteSaved.id.toString()))
    }


    // TODO: Comunicación con datos de la sección Datos generales
    @SuppressLint("SimpleDateFormat")
    fun readCenotesGeneralSec(clave: String?): ArrayList<CenoteGeneralSec> {
        val db = this.dbHelper.readableDatabase
        val table = CenoteReaderContract.CenoteGeneralSec
        val cursor = db.query(table.TABLE_NAME, null,
            if (clave != null) "${CenoteReaderContract.CenoteGeneralSec.COLUMN_NAME_CVE} = ?" else null,
            if (clave != null) arrayOf(clave) else null,
            null, null, null)

        val list = ArrayList<CenoteGeneralSec>()
        with(cursor) {
            while (moveToNext()) {
                val cenoteGeneralSec = CenoteGeneralSec(getString(getColumnIndexOrThrow(table.COLUMN_NAME_CVE)))
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
        }
    }
    fun insertCenoteGeneralSec(cenoteGeneralSec: CenoteGeneralSec): Long? {
        val db = dbHelper.writableDatabase
        return db?.insert(
            CenoteReaderContract.CenoteGeneralSec.TABLE_NAME,
            null,
            contentValuesCenoteGeneralSec(cenoteGeneralSec))
    }
    fun updateCenoteGeneralSec(cenoteGeneralSec: CenoteGeneralSec): Int? {
        val db = dbHelper.writableDatabase
        return db?.update(
            CenoteReaderContract.CenoteGeneralSec.TABLE_NAME,
            contentValuesCenoteGeneralSec(cenoteGeneralSec),
            "${CenoteReaderContract.CenoteGeneralSec.COLUMN_NAME_CVE} = ?",
            arrayOf(cenoteGeneralSec.clave))
    }

    // TODO: Comunicación con datos de la sección Clasificación
    @SuppressLint("SimpleDateFormat")
    fun readCenotesClasifiSec(clave: String?): ArrayList<CenoteClasifiSec> {
        val db = this.dbHelper.readableDatabase
        val table = CenoteReaderContract.CenoteClasifiSec
        val cursor = db.query(table.TABLE_NAME, null,
            if (clave != null) "${CenoteReaderContract.CenoteClasifiSec.COLUMN_NAME_CVE} = ?" else null,
            if (clave != null) arrayOf(clave) else null,
            null, null, null)

        val list = ArrayList<CenoteClasifiSec>()
        with(cursor) {
            while (moveToNext()) {
                val cenoteClasifiSec = CenoteClasifiSec(getString(getColumnIndexOrThrow(table.COLUMN_NAME_CVE)))
                cenoteClasifiSec.genesis = getString(getColumnIndexOrThrow(table.COLUMN_NAME_GENESIS))
                cenoteClasifiSec.geoforma = getString(getColumnIndexOrThrow(table.COLUMN_NAME_GEOFORMA))
                cenoteClasifiSec.tipo = getString(getColumnIndexOrThrow(table.COLUMN_NAME_TIPO))
                cenoteClasifiSec.apertura = getString(getColumnIndexOrThrow(table.COLUMN_NAME_APERTURA))
                cenoteClasifiSec.cuerpoAgua = getString(getColumnIndexOrThrow(table.COLUMN_NAME_CUERPO_AGUA))
                cenoteClasifiSec.timestamp = SimpleDateFormat().parse(getString(getColumnIndexOrThrow(table.COLUMN_NAME_TIMESTAMP))) as Date
                cenoteClasifiSec.saved = true
                list.add(cenoteClasifiSec)
            }
        }
        return list
    }
    @SuppressLint("SimpleDateFormat")
    private fun contentValuesCenoteClasifiSec(cenoteClasifiSec: CenoteClasifiSec): ContentValues {
        val table = CenoteReaderContract.CenoteClasifiSec
        return ContentValues().apply {
            put(table.COLUMN_NAME_CVE, cenoteClasifiSec.clave)
            put(table.COLUMN_NAME_GENESIS, cenoteClasifiSec.genesis)
            put(table.COLUMN_NAME_GEOFORMA, cenoteClasifiSec.geoforma)
            put(table.COLUMN_NAME_TIPO, cenoteClasifiSec.tipo)
            put(table.COLUMN_NAME_APERTURA, cenoteClasifiSec.apertura)
            put(table.COLUMN_NAME_CUERPO_AGUA, cenoteClasifiSec.cuerpoAgua)
            put(table.COLUMN_NAME_TIMESTAMP, SimpleDateFormat().format(cenoteClasifiSec.timestamp))
        }
    }
    fun insertCenoteClasifiSec(cenoteClasifiSec: CenoteClasifiSec): Long? {
        val db = dbHelper.writableDatabase
        return db?.insert(
            CenoteReaderContract.CenoteClasifiSec.TABLE_NAME,
            null,
            contentValuesCenoteClasifiSec(cenoteClasifiSec))
    }
    fun updateCenoteClasifiSec(cenoteClasifiSec: CenoteClasifiSec): Int? {
        val db = dbHelper.writableDatabase
        return db?.update(
            CenoteReaderContract.CenoteClasifiSec.TABLE_NAME,
            contentValuesCenoteClasifiSec(cenoteClasifiSec),
            "${CenoteReaderContract.CenoteClasifiSec.COLUMN_NAME_CVE} = ?",
            arrayOf(cenoteClasifiSec.clave))
    }

}
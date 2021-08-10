package com.geotec.cenotesapp.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import androidx.core.database.getFloatOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.geotec.cenotesapp.model.*
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


    // TODO: Comunicación con datos de la sección Morfometría
    @SuppressLint("SimpleDateFormat")
    fun readCenotesMorfoSec(clave: String?): ArrayList<CenoteMorfoSec> {
        val db = this.dbHelper.readableDatabase
        val table = CenoteReaderContract.CenoteMorfoSec
        val cursor = db.query(table.TABLE_NAME, null,
            if (clave != null) "${CenoteReaderContract.CenoteMorfoSec.COLUMN_NAME_CVE} = ?" else null,
            if (clave != null) arrayOf(clave) else null,
            null, null, null)

        val list = ArrayList<CenoteMorfoSec>()
        with(cursor) {
            while (moveToNext()) {
                val cMorfoSec = CenoteMorfoSec(getString(getColumnIndexOrThrow(table.COLUMN_NAME_CVE)))
                cMorfoSec.area = fltColumn(this, table.COLUMN_NAME_AREA)
                cMorfoSec.perimetro = fltColumn(this, table.COLUMN_NAME_PERIMETRO)
                cMorfoSec.profundidad = fltColumn(this, table.COLUMN_NAME_PROFUNDIDAD)
                cMorfoSec.semiejeMayor = fltColumn(this, table.COLUMN_NAME_S_MAYOR)
                cMorfoSec.semiejeMenor = fltColumn(this, table.COLUMN_NAME_S_MENOR)
                cMorfoSec.elongacion = fltColumn(this, table.COLUMN_NAME_ELONGACION)
                cMorfoSec.timestamp = SimpleDateFormat().parse(getString(getColumnIndexOrThrow(table.COLUMN_NAME_TIMESTAMP))) as Date
                cMorfoSec.saved = true
                list.add(cMorfoSec)
            }
        }
        return list
    }
    @SuppressLint("SimpleDateFormat")
    private fun contentValuesCenoteMorfoSec(cMorfoSec: CenoteMorfoSec): ContentValues {
        val table = CenoteReaderContract.CenoteMorfoSec
        return ContentValues().apply {
            put(table.COLUMN_NAME_CVE, cMorfoSec.clave)
            put(table.COLUMN_NAME_AREA, cMorfoSec.area)
            put(table.COLUMN_NAME_PERIMETRO, cMorfoSec.perimetro)
            put(table.COLUMN_NAME_PROFUNDIDAD, cMorfoSec.profundidad)
            put(table.COLUMN_NAME_S_MAYOR, cMorfoSec.semiejeMayor)
            put(table.COLUMN_NAME_S_MENOR, cMorfoSec.semiejeMenor)
            put(table.COLUMN_NAME_ELONGACION, cMorfoSec.elongacion)
            put(table.COLUMN_NAME_TIMESTAMP, SimpleDateFormat().format(cMorfoSec.timestamp))
        }
    }
    fun insertCenoteMorfoSec(cMorfoSec: CenoteMorfoSec): Long? {
        val db = dbHelper.writableDatabase
        return db?.insert(
            CenoteReaderContract.CenoteMorfoSec.TABLE_NAME,
            null,
            contentValuesCenoteMorfoSec(cMorfoSec))
    }
    fun updateCenoteMorfoSec(cMorfoSec: CenoteMorfoSec): Int? {
        val db = dbHelper.writableDatabase
        return db?.update(
            CenoteReaderContract.CenoteMorfoSec.TABLE_NAME,
            contentValuesCenoteMorfoSec(cMorfoSec),
            "${CenoteReaderContract.CenoteMorfoSec.COLUMN_NAME_CVE} = ?",
            arrayOf(cMorfoSec.clave))
    }


    // TODO: Comunicación con datos de la sección Uso actual
    @SuppressLint("SimpleDateFormat")
    fun readCenotesUsoSec(clave: String?): ArrayList<CenoteUsoSec> {
        val db = this.dbHelper.readableDatabase
        val table = CenoteReaderContract.CenoteUsoSec
        val cursor = db.query(table.TABLE_NAME, null,
            if (clave != null) "${CenoteReaderContract.CenoteUsoSec.COLUMN_NAME_CVE} = ?" else null,
            if (clave != null) arrayOf(clave) else null,
            null, null, null)

        val list = ArrayList<CenoteUsoSec>()
        with(cursor) {
            while (moveToNext()) {
                val cUsoSec = CenoteUsoSec(getString(getColumnIndexOrThrow(table.COLUMN_NAME_CVE)))
                cUsoSec.uso_actual = strColumn(this, table.COLUMN_NAME_USO_ACTUAL)
                cUsoSec.densidad_urbana = strColumn(this, table.COLUMN_NAME_DENCIDAD_URBANA)
                cUsoSec.tipo_vialidad = strColumn(this, table.COLUMN_NAME_TIPO_VIALIDAD)
                cUsoSec.servicios_publicos = strColumn(this, table.COLUMN_NAME_SERVICIOS)
                cUsoSec.ecosistema = strColumn(this, table.COLUMN_NAME_ECOSISTEMA)
                cUsoSec.timestamp = SimpleDateFormat().parse(getString(getColumnIndexOrThrow(table.COLUMN_NAME_TIMESTAMP))) as Date
                cUsoSec.saved = true
                list.add(cUsoSec)
            }
        }
        return list
    }
    @SuppressLint("SimpleDateFormat")
    private fun contentValuesCenoteUsoSec(cUsoSec: CenoteUsoSec): ContentValues {
        val table = CenoteReaderContract.CenoteUsoSec
        return ContentValues().apply {
            put(table.COLUMN_NAME_CVE, cUsoSec.clave)
            put(table.COLUMN_NAME_USO_ACTUAL, cUsoSec.uso_actual)
            put(table.COLUMN_NAME_DENCIDAD_URBANA, cUsoSec.densidad_urbana)
            put(table.COLUMN_NAME_TIPO_VIALIDAD, cUsoSec.tipo_vialidad)
            put(table.COLUMN_NAME_SERVICIOS, cUsoSec.servicios_publicos)
            put(table.COLUMN_NAME_ECOSISTEMA, cUsoSec.ecosistema)
            put(table.COLUMN_NAME_TIMESTAMP, SimpleDateFormat().format(cUsoSec.timestamp))
        }
    }
    fun insertCenoteUsoSec(cUsoSec: CenoteUsoSec): Long? {
        val db = dbHelper.writableDatabase
        return db?.insert(
            CenoteReaderContract.CenoteUsoSec.TABLE_NAME,
            null,
            contentValuesCenoteUsoSec(cUsoSec))
    }
    fun updateCenoteUsoSec(cUsoSec: CenoteUsoSec): Int? {
        val db = dbHelper.writableDatabase
        return db?.update(
            CenoteReaderContract.CenoteUsoSec.TABLE_NAME,
            contentValuesCenoteUsoSec(cUsoSec),
            "${CenoteReaderContract.CenoteUsoSec.COLUMN_NAME_CVE} = ?",
            arrayOf(cUsoSec.clave))
    }


    // TODO: Comunicación con datos de la sección Problemática del Sitio
    @SuppressLint("SimpleDateFormat")
    fun readCenotesProblemSec(clave: String?): ArrayList<CenoteProblemSec> {
        val db = this.dbHelper.readableDatabase
        val table = CenoteReaderContract.CenoteProblemSec
        val cursor = db.query(table.TABLE_NAME, null,
            if (clave != null) "${CenoteReaderContract.CenoteProblemSec.COLUMN_NAME_CVE} = ?" else null,
            if (clave != null) arrayOf(clave) else null,
            null, null, null)

        val list = ArrayList<CenoteProblemSec>()
        with(cursor) {
            while (moveToNext()) {
                val cProblemSec = CenoteProblemSec(getString(getColumnIndexOrThrow(table.COLUMN_NAME_CVE)))
                cProblemSec.contaminacion = strColumn(this, table.COLUMN_NAME_CONTAMINACION)
                cProblemSec.vertederos = strColumn(this, table.COLUMN_NAME_VERTEDEROS)
                cProblemSec.movimientos = strColumn(this, table.COLUMN_NAME_MOVIMIENTOS)
                cProblemSec.depresiones = strColumn(this, table.COLUMN_NAME_DEPRESIONES)
                cProblemSec.visitas_masivas = strColumn(this, table.COLUMN_NAME_VISITAS_MASIVAS)
                cProblemSec.usos_previos = strColumn(this, table.COLUMN_NAME_USOS_PREVIOS)
                cProblemSec.timestamp = SimpleDateFormat().parse(getString(getColumnIndexOrThrow(table.COLUMN_NAME_TIMESTAMP))) as Date
                cProblemSec.saved = true
                list.add(cProblemSec)
            }
        }
        return list
    }
    @SuppressLint("SimpleDateFormat")
    private fun contentValuesCenoteProblemSec(cProblemSec: CenoteProblemSec): ContentValues {
        val table = CenoteReaderContract.CenoteProblemSec
        return ContentValues().apply {
            put(table.COLUMN_NAME_CVE, cProblemSec.clave)
            put(table.COLUMN_NAME_CONTAMINACION, cProblemSec.contaminacion)
            put(table.COLUMN_NAME_VERTEDEROS, cProblemSec.vertederos)
            put(table.COLUMN_NAME_MOVIMIENTOS, cProblemSec.movimientos)
            put(table.COLUMN_NAME_DEPRESIONES, cProblemSec.depresiones)
            put(table.COLUMN_NAME_VISITAS_MASIVAS, cProblemSec.visitas_masivas)
            put(table.COLUMN_NAME_USOS_PREVIOS, cProblemSec.usos_previos)
            put(table.COLUMN_NAME_TIMESTAMP, SimpleDateFormat().format(cProblemSec.timestamp))
        }
    }
    fun insertCenoteProblemSec(cProblemSec: CenoteProblemSec): Long? {
        val db = dbHelper.writableDatabase
        return db?.insert(
            CenoteReaderContract.CenoteProblemSec.TABLE_NAME,
            null,
            contentValuesCenoteProblemSec(cProblemSec))
    }
    fun updateCenoteProblemSec(cProblemSec: CenoteProblemSec): Int? {
        val db = dbHelper.writableDatabase
        return db?.update(
            CenoteReaderContract.CenoteProblemSec.TABLE_NAME,
            contentValuesCenoteProblemSec(cProblemSec),
            "${CenoteReaderContract.CenoteProblemSec.COLUMN_NAME_CVE} = ?",
            arrayOf(cProblemSec.clave))
    }


    // TODO: Comunicación con datos de la sección Gestión
    @SuppressLint("SimpleDateFormat")
    fun readCenotesGestionSec(clave: String?): ArrayList<CenoteGestionSec> {
        val db = this.dbHelper.readableDatabase
        val table = CenoteReaderContract.CenoteGestionSec
        val cursor = db.query(table.TABLE_NAME, null,
            if (clave != null) "${CenoteReaderContract.CenoteGestionSec.COLUMN_NAME_CVE} = ?" else null,
            if (clave != null) arrayOf(clave) else null,
            null, null, null)

        val list = ArrayList<CenoteGestionSec>()
        with(cursor) {
            while (moveToNext()) {
                val cGestionSec = CenoteGestionSec(getString(getColumnIndexOrThrow(table.COLUMN_NAME_CVE)))
                cGestionSec.estado_cenote = strColumn(this, table.COLUMN_NAME_ESTADO)
                cGestionSec.respuesta_estado = strColumn(this, table.COLUMN_NAME_RESPUESTA)
                cGestionSec.agentes = strColumn(this, table.COLUMN_NAME_AGENTES)
                cGestionSec.timestamp = SimpleDateFormat().parse(getString(getColumnIndexOrThrow(table.COLUMN_NAME_TIMESTAMP))) as Date
                cGestionSec.saved = true
                list.add(cGestionSec)
            }
        }
        return list
    }
    @SuppressLint("SimpleDateFormat")
    private fun contentValuesCenoteGestionSec(cGestionSec: CenoteGestionSec): ContentValues {
        val table = CenoteReaderContract.CenoteGestionSec
        return ContentValues().apply {
            put(table.COLUMN_NAME_CVE, cGestionSec.clave)
            put(table.COLUMN_NAME_ESTADO, cGestionSec.estado_cenote)
            put(table.COLUMN_NAME_RESPUESTA, cGestionSec.respuesta_estado)
            put(table.COLUMN_NAME_AGENTES, cGestionSec.agentes)
            put(table.COLUMN_NAME_TIMESTAMP, SimpleDateFormat().format(cGestionSec.timestamp))
        }
    }
    fun insertCenoteGestionSec(cGestionSec: CenoteGestionSec): Long? {
        val db = dbHelper.writableDatabase
        return db?.insert(
            CenoteReaderContract.CenoteGestionSec.TABLE_NAME,
            null,
            contentValuesCenoteGestionSec(cGestionSec))
    }
    fun updateCenoteGestionSec(cGestionSec: CenoteGestionSec): Int? {
        val db = dbHelper.writableDatabase
        return db?.update(
            CenoteReaderContract.CenoteGestionSec.TABLE_NAME,
            contentValuesCenoteGestionSec(cGestionSec),
            "${CenoteReaderContract.CenoteGestionSec.COLUMN_NAME_CVE} = ?",
            arrayOf(cGestionSec.clave))
    }


    // TODO: Comunicación con datos de la sección Fotografías
    @SuppressLint("SimpleDateFormat")
    fun readCenotesFotoSec(clave: String?): ArrayList<CenoteFoto> {
        val db = this.dbHelper.readableDatabase
        val table = CenoteReaderContract.CenoteFotosSec
        val cursor = db.query(table.TABLE_NAME, null,
            if (clave != null) "${CenoteReaderContract.CenoteFotosSec.COLUMN_NAME_CVE} = ?" else null,
            if (clave != null) arrayOf(clave) else null,
            null, null, null)

        val list = ArrayList<CenoteFoto>()
        with(cursor) {
            while (moveToNext()) {
                val cFotosSec = CenoteFoto(getInt(getColumnIndexOrThrow(table.COLUMN_NAME_ID)),
                    getString(getColumnIndexOrThrow(table.COLUMN_NAME_CVE)))
                cFotosSec.nombre = strColumn(this, table.COLUMN_NAME_NOMBRE)
                cFotosSec.desc = strColumn(this, table.COLUMN_NAME_DESC)
                cFotosSec.ruta = strColumn(this, table.COLUMN_NAME_RUTA)
                cFotosSec.timestamp = SimpleDateFormat().parse(getString(getColumnIndexOrThrow(table.COLUMN_NAME_TIMESTAMP))) as Date
                cFotosSec.saved = true
                list.add(cFotosSec)
            }
        }
        return list
    }
    @SuppressLint("SimpleDateFormat")
    private fun contentValuesCenoteFotoSec(cFoto: CenoteFoto): ContentValues {
        val table = CenoteReaderContract.CenoteFotosSec
        return ContentValues().apply {
            put(table.COLUMN_NAME_CVE, cFoto.clave)
            put(table.COLUMN_NAME_NOMBRE, cFoto.nombre)
            put(table.COLUMN_NAME_DESC, cFoto.desc)
            put(table.COLUMN_NAME_RUTA, cFoto.ruta)
            put(table.COLUMN_NAME_TIMESTAMP, SimpleDateFormat().format(cFoto.timestamp))
        }
    }
    fun insertCenoteFotoSec(cFoto: CenoteFoto): Long? {
        val db = dbHelper.writableDatabase
        return db?.insert(
            CenoteReaderContract.CenoteFotosSec.TABLE_NAME,
            null,
            contentValuesCenoteFotoSec(cFoto))
    }
    fun updateCenoteFotoSec(cFoto: CenoteFoto): Int? {
        val db = dbHelper.writableDatabase
        return db?.update(
            CenoteReaderContract.CenoteFotosSec.TABLE_NAME,
            contentValuesCenoteFotoSec(cFoto),
            "${CenoteReaderContract.CenoteFotosSec.COLUMN_NAME_ID} = ?",
            arrayOf(cFoto.id.toString()))
    }

    fun cenoteForGeojson(cenoteSaved: CenoteSaved): CenoteGeojson {
        var listGestionSec = readCenotesGeneralSec(cenoteSaved.clave)
        return CenoteGeojson(
            cenoteSaved,
            if (listGestionSec.size > 0) listGestionSec[0] else CenoteGeneralSec(cenoteSaved.clave),

            readCenotesFotoSec(cenoteSaved.clave)
        )
    }


    private fun intColumn(cursor: Cursor, columnName: String) = cursor.getIntOrNull(cursor.getColumnIndexOrThrow(columnName))
    private fun fltColumn(cursor: Cursor, columnName: String) = cursor.getFloatOrNull(cursor.getColumnIndexOrThrow(columnName))
    private fun strColumn(cursor: Cursor, columnName: String) = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(columnName))
}
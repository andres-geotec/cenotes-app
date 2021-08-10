package com.geotec.cenotesapp.export

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.model.CenoteFoto
import com.geotec.cenotesapp.model.CenoteGeojson
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Geojson(val context: Context, val cenoteSaved: ArrayList<CenoteSaved>) {
    private val jsonHeader = JSONObject(context.getString(R.string.geojsonHeader))
    private val feature = JSONObject("{\"type\": \"Feature\"}")
    private val geometry = JSONObject("{\"type\":\"Point\"}")
    var conFotos: Boolean = false

    private val sqlite = SqliteComunicate(context)

    private fun getData(cenote: CenoteSaved): CenoteGeojson {
        return sqlite.cenoteForGeojson(cenote)
    }

    private fun strValue(value: String?) = value ?: JSONObject.NULL
    private fun fltValue(value: Float?) = value ?: JSONObject.NULL
    private fun imgValue(path: String): String {
        val file = File(path)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(file.readBytes()).toString()
        } else {
            "foto no soportada"
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createProperties(cenote: CenoteGeojson): JSONObject {
        val properties = JSONObject()
        properties.put("id", cenote.cenoteSaved.id)
        properties.put("clave", cenote.cenoteSaved.clave)
        properties.put("nombre", cenote.cenoteSaved.nombre)
        properties.put("domicilio", cenote.cenoteSaved.domicilio)

        // TODO: Agregar sección Datos generales
        properties.put("entre_calle1", strValue(cenote.generalSec.entreCalle1))
        properties.put("entre_calle2", strValue(cenote.generalSec.entreCalle2))
        properties.put("ageb", strValue(cenote.generalSec.ageb))

        // TODO: Agregar sección Clasificación
        properties.put("genesis", strValue(cenote.clasifiSec.genesis))
        properties.put("geoforma", strValue(cenote.clasifiSec.geoforma))
        properties.put("tipo", strValue(cenote.clasifiSec.tipo))
        properties.put("apertura", strValue(cenote.clasifiSec.apertura))
        properties.put("cuerpoAgua", strValue(cenote.clasifiSec.cuerpoAgua))

        // TODO: Agregar sección Morfometría
        properties.put("area", fltValue(cenote.morfoSec.area))
        properties.put("perimetro", fltValue(cenote.morfoSec.perimetro))
        properties.put("profundidad", fltValue(cenote.morfoSec.profundidad))
        properties.put("semiejeMayor", fltValue(cenote.morfoSec.semiejeMayor))
        properties.put("semiejeMenor", fltValue(cenote.morfoSec.semiejeMenor))
        properties.put("elongacion", fltValue(cenote.morfoSec.elongacion))

        // TODO: Agregar sección Uso actual
        properties.put("uso_actual", strValue(cenote.usoSec.uso_actual))
        properties.put("densidad_urbana", strValue(cenote.usoSec.densidad_urbana))
        properties.put("tipo_vialidad", strValue(cenote.usoSec.tipo_vialidad))
        properties.put("servicios_publicos", strValue(cenote.usoSec.servicios_publicos))
        properties.put("ecosistema", strValue(cenote.usoSec.ecosistema))

        // TODO: Agregar sección Problemática del Sitio
        properties.put("contaminacion", strValue(cenote.problemSec.contaminacion))
        properties.put("vertederos", strValue(cenote.problemSec.vertederos))
        properties.put("movimientos", strValue(cenote.problemSec.movimientos))
        properties.put("depresiones", strValue(cenote.problemSec.depresiones))
        properties.put("visitas_masivas", strValue(cenote.problemSec.visitas_masivas))
        properties.put("usos_previos", strValue(cenote.problemSec.usos_previos))

        // TODO: Agregar sección Gestión
        properties.put("estado_cenote", strValue(cenote.gestionSec.estado_cenote))
        properties.put("respuesta_estado", strValue(cenote.gestionSec.respuesta_estado))
        properties.put("agentes", strValue(cenote.gestionSec.agentes))

        // TODO: Agregar sección Fotografías
        for ((idx, foto) in cenote.fotos.iterator().withIndex()) {
            properties.put("imagen_${idx+1}", if (conFotos) imgValue(foto.ruta.toString()) else "foto no soportada*")
            properties.put("desc_img_${idx+1}", strValue(foto.desc.toString()))
        }

        properties.put("fecha", SimpleDateFormat().format(cenote.cenoteSaved.fecha))
        return properties
    }

    private fun createGeometry(cenote: CenoteGeojson): JSONObject {
        val coordinates = JSONArray()
        if (cenote.generalSec.longitude != null && cenote.generalSec.latitude != null) {
            coordinates.put(cenote.generalSec.longitude)
            coordinates.put(cenote.generalSec.latitude)
        }
        geometry.put("coordinates", if (coordinates.length() == 2) coordinates else JSONObject.NULL)
        return geometry
    }

    private fun createFeature(cenote: CenoteGeojson): JSONObject {
        feature.put("properties", createProperties(cenote))
        feature.put("geometry", createGeometry(cenote))
        return feature
    }

    fun create(): JSONObject {
        val geojson = jsonHeader
        val features = JSONArray()
        for(cenote in cenoteSaved) {
            features.put(createFeature(getData(cenote)))
        }
        geojson.put("features", features)
        // println(geojson)
        return geojson
    }

    fun asText(): String {
        return create().toString(2)
    }
}
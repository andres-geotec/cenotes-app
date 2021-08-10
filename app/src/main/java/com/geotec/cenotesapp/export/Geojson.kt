package com.geotec.cenotesapp.export

import android.annotation.SuppressLint
import android.content.Context
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.model.CenoteGeojson
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Geojson(val context: Context, val cenoteSaved: ArrayList<CenoteSaved>) {
    private val jsonHeader = JSONObject(context.getString(R.string.geojsonHeader))
    private val feature = JSONObject("{\"type\": \"Feature\"}")
    private val geometry = JSONObject("{\"type\":\"Point\"}")

    private val sqlite = SqliteComunicate(context)

    private fun getData(cenote: CenoteSaved): CenoteGeojson {
        return sqlite.cenoteForGeojson(cenote)
    }

    private fun strValue(value: String?) = value ?: JSONObject.NULL

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

        // TODO: Agregar sección Fotografías
        //Base64.get
        //val baos = ByteArrayOutputStream()

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
        println(geojson)
        return geojson
    }

    fun asText(): String {
        return create().toString(2)
    }
}
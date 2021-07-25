package com.geotec.cenotesapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.geotec.cenotesapp.R

class EditorCenoteActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor_cenote)

        val actionbar = getSupportActionBar()
        // si es una nueva captura mostrar: "Nuevo cenote"
        actionbar?.setTitle(R.string.cenote_name_new)
        //actionbar?.setSubtitle(R.string.main_list_title)

        (findViewById(R.id.button_section_generalidades) as Button).setOnClickListener {
            startActivity(Intent(this, GeneralidadesActivity::class.java))
        }

        (findViewById(R.id.button_section_clasificacion) as Button).setOnClickListener {
            startActivity(Intent(this, ClasificacionActivity::class.java))
        }

        (findViewById(R.id.button_section_morfometria) as Button).setOnClickListener {
            startActivity(Intent(this, MorfometriaActivity::class.java))
        }

        (findViewById(R.id.button_section_gestion) as Button).setOnClickListener {
            startActivity(Intent(this, GestionActivity::class.java))
        }

        (findViewById(R.id.button_section_uso) as Button).setOnClickListener {
            startActivity(Intent(this, UsoActivity::class.java))
        }

        (findViewById(R.id.button_section_problematica) as Button).setOnClickListener {
            startActivity(Intent(this, ProblematicaActivity::class.java))
        }

        (findViewById(R.id.button_section_fotos) as Button).setOnClickListener {
            startActivity(Intent(this, FotografiasActivity::class.java))
        }
    }
}
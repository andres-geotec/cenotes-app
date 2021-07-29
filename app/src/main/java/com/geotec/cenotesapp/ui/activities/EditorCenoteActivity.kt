package com.geotec.cenotesapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.ActivityEditorCenoteBinding

class EditorCenoteActivity : AppCompatActivity() {
    private lateinit var bv : ActivityEditorCenoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding view
        bv = ActivityEditorCenoteBinding.inflate(layoutInflater)
        setContentView(bv.root)

        val actionbar = getSupportActionBar()
        // si es una nueva captura mostrar: "Nuevo cenote"
        actionbar?.setTitle(R.string.cenote_name_new)
        //actionbar?.setSubtitle(R.string.main_list_title)

        bv.btnShowGeneralDialog.setOnClickListener {
            Toast.makeText(this, "prueba dialog", Toast.LENGTH_SHORT).show()

            // Para ir al dialos se usa navigation de la sigiente manera
            /**
             * val bundle = bundleOf(<name> to <object>)
             * findNavController().navigate(R.id.<fragmentDialog>, bundle)
             */
        }

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
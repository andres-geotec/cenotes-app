package com.geotec.cenotesapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geotec.cenotesapp.R

class EditorCenoteActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor_cenote)

        val actionbar = getSupportActionBar()
        // si es una nueva captura mostrar: "Nuevo cenote"
        actionbar?.setTitle(R.string.cenote_name)
        //actionbar?.setSubtitle(R.string.main_list_title)
    }
}
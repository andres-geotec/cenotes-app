package com.geotec.cenotesapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geotec.cenotesapp.R

class ProblematicaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problematica)

        val actionbar = getSupportActionBar()
        actionbar?.setTitle(R.string.sec_problematica_title)
        actionbar?.setSubtitle(R.string.cenote_name)
        actionbar?.setHomeAsUpIndicator(R.drawable.ic_native_close)
    }
}
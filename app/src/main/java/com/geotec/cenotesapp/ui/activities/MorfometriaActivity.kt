package com.geotec.cenotesapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geotec.cenotesapp.R

class MorfometriaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morfometria)

        val actionbar = getSupportActionBar()
        actionbar?.setTitle(R.string.section_name_morfometria)
        actionbar?.setSubtitle(R.string.cenote_name)
        actionbar?.setHomeAsUpIndicator(R.drawable.ic_native_close)
    }
}
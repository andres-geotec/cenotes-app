package com.geotec.cenotesapp.ui.editor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geotec.cenotesapp.R

class EditorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        //frgContainer
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()

        fragmentTransition.add(R.id.frgContainer, SectionsFragment())
        fragmentTransition.commit()
    }
}
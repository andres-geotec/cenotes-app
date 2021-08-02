package com.geotec.cenotesapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.ActivityMainBinding
import com.geotec.cenotesapp.ui.editor.EditorActivity
import com.geotec.cenotesapp.view.iu.fragments.ToolbarMainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bv : ActivityMainBinding
    private val fragmentManager = supportFragmentManager
    private val fragmentTransition = fragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        bv = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bv.root)

        fragmentTransition.add(R.id.fragment_toolbar_main, ToolbarMainFragment())
        fragmentTransition.commit()

        bv.btnNewRecord.setOnClickListener {
            startActivity(Intent(this, EditorActivity::class.java))
        }


        bv.buttonNewRecord.setOnClickListener {
            openEditionCenote()
        }
    }

    private fun openEditionCenote() {
        val intent = Intent(this, EditorCenoteActivity::class.java)
        startActivity(intent)
        //Toast.makeText(this@MainActivity, "send activity", Toast.LENGTH_SHORT).show()
    }
}
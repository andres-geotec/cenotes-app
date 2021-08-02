package com.geotec.cenotesapp.ui.inicio

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.ActivityMainBinding
import com.geotec.cenotesapp.sqlite.AdminSQLiteOpenHelper
import com.geotec.cenotesapp.sqlite.CenoteReaderContract.CenoteEntry

class MainActivity : AppCompatActivity() {
    private lateinit var bv : ActivityMainBinding
    private val fragmentTransition = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bv = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bv.root)

        fragmentTransition.add(R.id.fragmentToolbarMain, ToolbarMainFragment())
        fragmentTransition.commit()

        val dbHelper = AdminSQLiteOpenHelper(this@MainActivity)

        bv.buttonTest.setOnClickListener {
            val values = ContentValues().apply {
                put(CenoteEntry.COLUMN_NAME_CLAVE, "0")
                put(CenoteEntry.COLUMN_NAME_NAME, "Cenote de prueba")
            }

            val db = dbHelper.writableDatabase
            val newRowId = db?.insert(CenoteEntry.TABLE_NAME, null, values)

            Toast.makeText(this@MainActivity, "Saved " + newRowId.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
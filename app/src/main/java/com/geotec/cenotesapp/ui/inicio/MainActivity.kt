package com.geotec.cenotesapp.ui.inicio

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.ActivityMainBinding
import com.geotec.cenotesapp.sqlite.AdminSQLiteOpenHelper
import com.geotec.cenotesapp.sqlite.CenoteReaderContract.CenoteEntry
import com.geotec.cenotesapp.ui.cenote.CenotesSavedFragment
import com.geotec.cenotesapp.ui.cenote.EditorCenoteFragment
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var bv : ActivityMainBinding
    private lateinit var dbHelper : AdminSQLiteOpenHelper
    private val fragmentTransition = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bv = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bv.root)

        fragmentTransition.add(R.id.fragmentToolbarMain, ToolbarMainFragment())
        // fragmentTransition.add(R.id.fragmentContentMain, CenotesSavedFragment())
        fragmentTransition.commit()
    }

    private fun insertInSqlite(values: ContentValues) {
        val db = dbHelper.writableDatabase
        val newRowId = db?.insert(CenoteEntry.TABLE_NAME, null, values)
        Toast.makeText(this@MainActivity, "Saved $newRowId", Toast.LENGTH_SHORT).show()
    }

    private fun loadFromSqlite() {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(BaseColumns._ID, CenoteEntry.COLUMN_NAME_CVE, CenoteEntry.COLUMN_NAME_NAME)
        val selection = "${CenoteEntry.COLUMN_NAME_CVE} = ?"
        val selectionArgs = arrayOf("0")
        val sortOrder = "${CenoteEntry.COLUMN_NAME_NAME} DESC"
        val cursor = db.query(
            CenoteEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        // val itemIds = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val itemCve = getLong(getColumnIndexOrThrow(CenoteEntry.COLUMN_NAME_CVE))
                val itemName = getString(getColumnIndexOrThrow(CenoteEntry.COLUMN_NAME_NAME))
                Toast.makeText(this@MainActivity, "$itemId: $itemName ($itemCve)", Toast.LENGTH_SHORT).show()
                // itemIds.add(itemId)
            }
        }
    }
}
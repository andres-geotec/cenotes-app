package com.geotec.cenotesapp.sqlite

import android.content.Context
import android.provider.BaseColumns

class SqliteComunicate(context: Context) {
    private var dbHelper: AdminSQLiteOpenHelper = AdminSQLiteOpenHelper(context)

    public fun loadFromSqlite() : ArrayList<String> {
        val db = this.dbHelper.readableDatabase
        val columns = arrayOf(BaseColumns._ID, CenoteReaderContract.CenoteEntry.COLUMN_NAME_CVE, CenoteReaderContract.CenoteEntry.COLUMN_NAME_NAME)
        val cursor = db.query(CenoteReaderContract.CenoteEntry.TABLE_NAME,   // The table to query
            columns, null, null, null, null, null)

        val items = ArrayList<String>()
        with(cursor) {
            while (moveToNext()) {
                // val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                // val itemCve = getLong(getColumnIndexOrThrow(CenoteReaderContract.CenoteEntry.COLUMN_NAME_CVE))
                val itemName = getString(getColumnIndexOrThrow(CenoteReaderContract.CenoteEntry.COLUMN_NAME_NAME))
                // Toast.makeText(this.context, "$itemId: $itemName ($itemCve)", Toast.LENGTH_SHORT).show()
                items.add(itemName)
            }
        }

        return items
    }
}
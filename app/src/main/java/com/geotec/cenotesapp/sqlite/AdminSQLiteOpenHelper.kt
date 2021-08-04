package com.geotec.cenotesapp.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    val cenoteContract = CenoteReaderContract()

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(cenoteContract.SQL_CREATE_TABLE_CENOTE_ENTRIY)
        db?.execSQL(cenoteContract.SQL_CREATE_TABLE_CENOTE_ALTA)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(cenoteContract.SQL_DELETE_TABLE_CENOTE_ENTRY)
        db?.execSQL(cenoteContract.SQL_DELETE_TABLE_CENOTE_ALTA)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "CenoteReader.db"
    }
}
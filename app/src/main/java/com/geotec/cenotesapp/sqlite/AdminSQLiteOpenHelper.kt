package com.geotec.cenotesapp.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val cenoteContract = CenoteReaderContract()

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(cenoteContract.SQL_CREATE_TABLE_CENOTE_ALTA)
        db?.execSQL(cenoteContract.SQL_CREATE_TABLE_CENOTE_GENERAL_SEC)
        //db?.execSQL(cenoteContract.SQL_CREATE_TABLE_CENOTE_ACCESS_SEC)
        db?.execSQL(cenoteContract.SQL_CREATE_TABLE_CENOTE_CLASIFI_SEC)
        db?.execSQL(cenoteContract.SQL_CREATE_TABLE_CENOTE_MORFO_SEC)
        db?.execSQL(cenoteContract.SQL_CREATE_TABLE_CENOTE_USO_SEC)
        db?.execSQL(cenoteContract.SQL_CREATE_TABLE_CENOTE_PROBLEM_SEC)
        db?.execSQL(cenoteContract.SQL_CREATE_TABLE_CENOTE_GESTION_SEC)
        db?.execSQL(cenoteContract.SQL_CREATE_TABLE_CENOTE_FOTOS_SEC)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(cenoteContract.SQL_DELETE_TABLE_CENOTE_ALTA)
        db?.execSQL(cenoteContract.SQL_DELETE_TABLE_CENOTE_GENERAL_SEC)
        //db?.execSQL(cenoteContract.SQL_DELETE_TABLE_CENOTE_ACCESS_SEC)
        db?.execSQL(cenoteContract.SQL_DELETE_TABLE_CENOTE_CLASIFI_SEC)
        db?.execSQL(cenoteContract.SQL_DELETE_TABLE_CENOTE_MORFO_SEC)
        db?.execSQL(cenoteContract.SQL_DELETE_TABLE_CENOTE_USO_SEC)
        db?.execSQL(cenoteContract.SQL_DELETE_TABLE_CENOTE_PROBLEM_SEC)
        db?.execSQL(cenoteContract.SQL_DELETE_TABLE_CENOTE_GESTION_SEC)
        db?.execSQL(cenoteContract.SQL_DELETE_TABLE_CENOTE_FOTOS_SEC)
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
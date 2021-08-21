package com.geotec.cenotesapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            } else {
                synchronized(this) {
                    val instance: UserDatabase =  Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database",
                    ).addMigrations(MIGRATION_1_2(),)
                    .build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }

    class MIGRATION_1_2: Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE usser ADD COLUMN age INTEGER")
        }
    }
}
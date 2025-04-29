package com.app.benhuntoon.final_project.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MadLibEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MadLibDatabase : RoomDatabase() {
    abstract fun madLibDao(): MadLibDao

    companion object {
        @Volatile
        private var INSTANCE: MadLibDatabase? = null

        fun getDatabase(context: Context): MadLibDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MadLibDatabase::class.java,
                    "madlib_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


//package com.app.benhuntoon.final_project.data.database
//
//import androidx.room.Database
//import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
//
//@Database(entities = [MadLibEntity::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
//abstract class MadLibDatabase : RoomDatabase() {
//    abstract fun madLibDao(): MadLibDao
//}
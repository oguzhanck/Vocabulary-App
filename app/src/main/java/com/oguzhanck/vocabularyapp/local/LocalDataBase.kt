package com.oguzhanck.vocabularyapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oguzhanck.vocabularyapp.model.ListEntity
import com.oguzhanck.vocabularyapp.model.WordEntity


const val DB_VERSION = 1

@Database(entities = [WordEntity::class, ListEntity::class], version = DB_VERSION)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        private var INSTANCE: LocalDatabase? = null
        fun getAppDatabase(context: Context): LocalDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<LocalDatabase>(
                    context.applicationContext, LocalDatabase::class.java, "WordDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE
        }
    }
}
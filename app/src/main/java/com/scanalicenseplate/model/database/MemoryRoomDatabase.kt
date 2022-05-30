package com.mytaskgame.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mytaskgame.model.entities.PlateNumber

@Database(entities = [PlateNumber::class], version = 1)
abstract class MemoryRoomDatabase : RoomDatabase(){

    abstract fun memoryDou() :MemoryDou

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MemoryRoomDatabase? = null

        fun getDatabase(context: Context): MemoryRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemoryRoomDatabase::class.java,
                    "number_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
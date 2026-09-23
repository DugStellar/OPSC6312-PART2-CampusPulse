package com.campuspulse.campuspulse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Event::class, Ticket::class], version = 1, exportSchema = false)
abstract class CampusPulseDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun ticketDao(): TicketDao

    companion object {
        @Volatile
        private var INSTANCE: CampusPulseDatabase? = null

        fun getDatabase(context: Context): CampusPulseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CampusPulseDatabase::class.java,
                    "campus_pulse_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
package com.arv.practica11.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arv.practica11.data.dao.NotasDao

abstract class NotasDatabase: RoomDatabase() {
    abstract fun notasDao(): NotasDao
    companion object{
        @Volatile
        private var INSTANCE: NotasDatabase?=null
        fun getDatabase(context: Context): NotasDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotasDatabase::class.java,
                    "notas.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
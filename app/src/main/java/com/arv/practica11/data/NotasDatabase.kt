package com.arv.practica11.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arv.practica11.data.dao.NotasDao
import com.arv.practica11.data.entities.Nota

@Database(entities = [Nota::class], version = 2)
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
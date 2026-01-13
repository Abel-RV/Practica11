package com.arv.practica11.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.arv.practica11.data.entities.Nota
import kotlinx.coroutines.flow.Flow

@Dao
interface NotasDao {
    @Upsert
    suspend fun upsertNota(nota: Nota)
    @Delete
    suspend fun deleteNota(nota: Nota)

    @Query("SELECT * FROM Nota ORDER BY titulo ASC")
    fun getNotasAlf_ASC(): Flow<List<Nota>>

    @Query("SELECT * FROM Nota ORDER BY titulo DESC")
    fun getNotasAlf_DESC(): Flow<List<Nota>>

    @Query("SELECT * FROM Nota ORDER BY fecha ASC")
    fun getNotasFecha_ASC(): Flow<List<Nota>>

    @Query("SELECT * FROM Nota ORDER BY fecha DESC")
    fun getNotasFecha_DESC(): Flow<List<Nota>>
}
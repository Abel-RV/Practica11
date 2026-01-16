package com.arv.practica11.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Nota(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    var titulo: String,
    var contenido:String?,
    val fecha: String,
    val categoria:String
)
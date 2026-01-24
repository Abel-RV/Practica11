package com.arv.practica11.data.state

import com.arv.practica11.data.entities.Nota
import com.arv.practica11.data.enums.SortType
import java.time.LocalDate

data class NotaState(
    val notas: List<Nota> =emptyList(),

    var titulo:String ="",
    var contenido:String?="",

    val fechaCreacion: LocalDate = LocalDate.now(),

    val isAddingNota: Boolean=false,
    val categoriaSeleccionada:String="Personal",
    val filtroActual:String = "Todas",
    val currentSortType: SortType = SortType.TITULO_ASC,
    val idNota:Int? =null
)
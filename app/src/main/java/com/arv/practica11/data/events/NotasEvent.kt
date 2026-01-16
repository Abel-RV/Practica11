package com.arv.practica11.data.events

import com.arv.practica11.data.enums.SortType
import com.arv.practica11.data.entities.Nota

sealed interface NotasEvent {
    data class SetTitulo(var titulo: String): NotasEvent
    data class SetContenido(var contenido:String?): NotasEvent

    object ShowDialog: NotasEvent
    object HiddenDialog: NotasEvent

    data class SortNotas(val sortType: SortType): NotasEvent
    data class DeleteNota(val nota: Nota): NotasEvent
    
    data class SetCategoria(val categoria: String) : NotasEvent
    data class SetFiltro(val categoria: String) : NotasEvent
    object SaveNota: NotasEvent
}
package com.arv.practica11.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arv.practica11.data.dao.NotasDao
import com.arv.practica11.data.entities.Nota
import com.arv.practica11.data.enums.SortType
import com.arv.practica11.data.events.NotasEvent
import com.arv.practica11.data.state.NotaState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotaViewModel(private val dao: NotasDao): ViewModel() {
    private val _sortType= MutableStateFlow(SortType.TITULO_ASC)

    private val _contacts = _sortType.flatMapLatest { sortType->
        when(sortType){
            SortType.TITULO_ASC -> dao.getNotasAlf_ASC()
            SortType.TITULO_DESC -> dao.getNotasAlf_DESC()

            SortType.FECHA_ASC -> dao.getNotasFecha_ASC()
            SortType.FECHA_DESC -> dao.getNotasFecha_DESC()
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        emptyList()
    )

    private val _state = MutableStateFlow(NotaState())

    val state: StateFlow<NotaState> = combine(
        _state,
        _sortType,
        _contacts
    ) { state, sortType, notas ->
        state.copy(
            notas = notas,
            currentSortType = sortType
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        NotaState()
    )

    fun onEvent(event: NotasEvent){
        when(event){
            is NotasEvent.SetTitulo->{
                _state.update { it.copy(titulo = event.titulo) }
            }
            is NotasEvent.SetContenido->{
                _state.update { it.copy(contenido = event.contenido) }
            }

            is NotasEvent.ShowDialog->{
                _state.update { it.copy(isAddingNota = true) }
            }
            is NotasEvent.HiddenDialog->{
                _state.update { it.copy(isAddingNota = false) }
            }

            is NotasEvent.SortNotas->{
                _sortType.value = event.sortType
            }

            is NotasEvent.DeleteNota->{
                viewModelScope.launch {
                    dao.deleteNota(event.nota)
                }
            }
            is NotasEvent.SaveNota->{
                var titulo = _state.value.titulo
                var contenido = _state.value.contenido
                val fecha = _state.value.fechaCreacion
                if(titulo.isBlank()){
                    return
                }
                val nota = Nota(
                    titulo = titulo,
                    contenido = contenido,
                    fecha = fecha.toString(),
                    categoria = categoria
                )
                viewModelScope.launch {
                    dao.upsertNota(nota)
                    _state.update {
                        it.copy(
                            isAddingNota = false,
                            titulo = "",
                            contenido = ""
                        )
                    }
                }
            }

            is NotasEvent.SetCategoria -> TODO()
            is NotasEvent.SetFiltro -> TODO()
        }
    }
}
package com.arv.practica11.ui.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arv.practica11.data.AddNotaDialog
import com.arv.practica11.data.entities.Nota
import com.arv.practica11.data.enums.SortType
import com.arv.practica11.data.events.NotasEvent
import com.arv.practica11.data.state.NotaState

@Composable
fun NotasScreen(
    state: NotaState,
    onEvent:(NotasEvent)-> Unit,
    modifier: Modifier= Modifier
){
    val context = LocalContext.current

    fun compartirNota(nota: Nota){
        val enviarIndent: Intent= Intent().apply {
            action= Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,"${nota.titulo}\n\n ${nota.contenido}")
            type="text/plain"
        }

        val compartirIntent = Intent.createChooser(enviarIndent,null);
        context.startActivity(compartirIntent)
    }

    val categoriasFiltro = listOf("Todas","Personal","Trabajo","Estudios")
    val filtros = listOf("TITULO ASC","TITULO DESC","FECHA ASC","FECHA DESC")
    Scaffold(
        floatingActionButton={
            FloatingActionButton(
                onClick = {onEvent(NotasEvent.ShowDialog)},
                content = {Icon(Icons.Default.Add, contentDescription = "Add")}

            )
        },
        modifier=modifier
    ) { padding->
        Column( modifier= Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
        ) {
            Text("Filtrar por categoria")
            Row {
                categoriasFiltro.forEach { categoria->
                    FilterChip(
                        selected=state.filtroActual==categoria,
                        onClick = {onEvent(NotasEvent.SetFiltro(categoria))},
                        label = {Text(categoria)}
                    )
                }
            }
            LazyColumn(
            ) {
                item {
                    Text("Ordenar por:", style = MaterialTheme.typography.labelLarge)
                    Row(
                        modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        SortType.entries.forEach { sortType->
                            Column(
                                modifier = Modifier.clickable{
                                    onEvent(NotasEvent.SortNotas(sortType))
                                }
                            ) {
                                FilterChip(
                                    selected = state.currentSortType==sortType,
                                    onClick = {onEvent(NotasEvent.SortNotas(sortType))},
                                    label = {filtros}
                                )
                                Text(sortType.name.replace("_"," "), fontSize = 12.sp)
                            }
                        }
                    }
                }

                items(state.notas) { nota ->
                    androidx.compose.material3.Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(nota.titulo, style = MaterialTheme.typography.titleMedium)
                                Text(nota.contenido ?: "", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    "${nota.fecha} - ${nota.categoria}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            IconButton(onClick = { onEvent(NotasEvent.DeleteNota(nota)) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                            IconButton(onClick = {onEvent(NotasEvent.editarNota(nota))}) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar")
                            }
                            IconButton(onClick = {compartirNota(nota)}) {
                                Icon(Icons.Default.Share, contentDescription = "Compartir")
                            }
                        }
                    }
                }
            }

        }

        if(state.isAddingNota){
            AddNotaDialog(
                state=state,
                onEvent=onEvent
            )
        }
    }
}
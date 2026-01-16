package com.arv.practica11.ui.screens

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
    val categoriasFiltro = listOf("Todas","Trabajo","Estudios")
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
        }
       LazyColumn(
           modifier= Modifier
               .fillMaxSize()
               .padding(padding)
               .padding(16.dp)
       ) {
           item {
               Text("Filtrar por categoría:", style = MaterialTheme.typography.labelLarge)
               Row(
                   modifier=Modifier.fillMaxWidth(),
                   horizontalArrangement = Arrangement.SpaceEvenly,
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   SortType.entries.forEach { sortType->
                       Row(
                           verticalAlignment = Alignment.CenterVertically,
                           modifier = Modifier.clickable{
                               onEvent(NotasEvent.SortNotas(sortType))
                           }
                       ) {
                           RadioButton(
                               selected = state.currentSortType==sortType,
                               onClick = {onEvent(NotasEvent.SortNotas(sortType))}
                           )
                           Text(sortType.name.replace("_"," "))
                       }
                   }
               }
           }

           items(state.notas) { nota->
               Row (
                   modifier = Modifier.fillMaxWidth(),
                   verticalAlignment = Alignment.CenterVertically
               ){
                   Column(
                       modifier = Modifier.weight(1f)
                   ){
                       Text("${nota.titulo} ${nota.contenido}", fontSize = 20.sp)
                       Text(nota.fecha, fontSize = 12.sp)
                   }
                   IconButton(
                       onClick = {onEvent(NotasEvent.DeleteNota(nota))}
                   ) {
                       Icon(Icons.Default.Delete, contentDescription = "Delete")
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
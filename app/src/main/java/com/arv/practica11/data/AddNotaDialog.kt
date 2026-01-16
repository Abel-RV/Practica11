package com.arv.practica11.data

import android.text.Layout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.arv.practica11.data.events.NotasEvent
import com.arv.practica11.data.state.NotaState

@Composable
fun AddNotaDialog(
    state: NotaState,
    onEvent: (NotasEvent)-> Unit
){
    val categorias = listOf("Personal","Trabajo","Estudios")
    AlertDialog(
        onDismissRequest = {onEvent(NotasEvent.HiddenDialog)},
        title = { Text("Add nota") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.titulo,
                    onValueChange = {onEvent(NotasEvent.SetTitulo(it))},
                    label = {Text("Titulo")}
                )
                OutlinedTextField(
                    value = state.contenido.toString(),
                    onValueChange ={onEvent(NotasEvent.SetContenido(it))}
                )
            }
            Text("Categoria", modifier = Modifier.padding(top = 8.dp))
            categorias.forEach {
                categoria->
                Row(
                    Modifier.fillMaxWidth().selectable(
                        selected = (categoria==state.categoriaSeleccionada),
                        onClick = {onEvent(NotasEvent.SetCategoria(categoria))}
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (categoria==state.categoriaSeleccionada),
                        onClick = {onEvent(NotasEvent.SetCategoria(categoria))}
                    )
                    Text(text = categoria)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onEvent(NotasEvent.SaveNota)
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = {onEvent(NotasEvent.HiddenDialog)}
            ) {
                Text("Cancel")
            }
        }
    )
}
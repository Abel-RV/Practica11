package com.arv.practica11.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.arv.practica11.data.events.NotasEvent
import com.arv.practica11.data.state.NotaState

@Composable
fun AddNotaDialog(
    state: NotaState,
    onEvent: (NotasEvent)-> Unit
){
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
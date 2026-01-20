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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.arv.practica11.data.events.NotasEvent
import com.arv.practica11.data.state.NotaState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotaDialog(
    state: NotaState,
    onEvent: (NotasEvent)-> Unit
){
    var expanded by remember { mutableStateOf(false) }
    val categorias = listOf("Personal", "Trabajo", "Estudios")

    AlertDialog(
        onDismissRequest = {onEvent(NotasEvent.HiddenDialog)},
        title = { Text("Añadir Nota") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.titulo,
                    onValueChange = {onEvent(NotasEvent.SetTitulo(it))},
                    label = {Text("Título")},
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.contenido.toString(),
                    onValueChange ={onEvent(NotasEvent.SetContenido(it))},
                    label = {Text("Contenido")},
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = state.categoriaSeleccionada,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Categoría") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categorias.forEach { categoria ->
                            DropdownMenuItem(
                                text = { Text(text = categoria) },
                                onClick = {
                                    onEvent(NotasEvent.SetCategoria(categoria))
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onEvent(NotasEvent.SaveNota)
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(
                onClick = {onEvent(NotasEvent.HiddenDialog)}
            ) {
                Text("Cancelar")
            }
        }
    )
}
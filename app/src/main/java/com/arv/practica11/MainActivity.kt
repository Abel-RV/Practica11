package com.arv.practica11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.arv.practica11.data.NotasDatabase
import com.arv.practica11.data.UserPreferences
import com.arv.practica11.ui.screens.NotaViewModel
import com.arv.practica11.ui.screens.NotasScreen
import com.arv.practica11.ui.theme.Practica11Theme

class MainActivity : ComponentActivity() {
    private lateinit var db: NotasDatabase
    private lateinit var viewModel: NotaViewModel

    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db= Room.databaseBuilder(
            applicationContext,
            NotasDatabase::class.java,
            "notas.db"
        ).fallbackToDestructiveMigration().build()

        userPreferences = UserPreferences(applicationContext)

        viewModel= ViewModelProvider(
            this,
            object : ViewModelProvider.Factory{
                override fun <T: ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return NotaViewModel(
                        db.notasDao(),
                        userPreferences
                    ) as T
                }
            }
        )[NotaViewModel::class.java]
        setContent {
            val state = viewModel.state.collectAsState()
            NotasScreen(
                state=state.value,
                onEvent = viewModel::onEvent
            )
        }
    }
}
package edu.ucne.prioridades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

import androidx.compose.material3.Text

import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint

import edu.ucne.prioridades.data.local.database.PrioridadDb
import edu.ucne.prioridades.data.local.entities.PrioridadEntity
import edu.ucne.prioridades.presentation.navigation.PrioridadNavHost

import edu.ucne.prioridades.ui.theme.PrioridadesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrioridadesTheme {
                val navHost = rememberNavController()
                PrioridadNavHost(navHost)
            }
        }
    }

    }






    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun PrioridadScreenPreview() {

}
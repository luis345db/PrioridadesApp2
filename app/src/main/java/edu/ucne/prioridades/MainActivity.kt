package edu.ucne.prioridades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.runtime.Composable

import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

import androidx.room.Room

import edu.ucne.prioridades.data.local.database.PrioridadDb
import edu.ucne.prioridades.data.local.entities.PrioridadEntity
import edu.ucne.prioridades.presentation.navigation.PrioridadNavHost

import edu.ucne.prioridades.ui.theme.PrioridadesTheme


class MainActivity : ComponentActivity() {

    private lateinit var prioridadDb: PrioridadDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDb :: class.java,
            "Prioridad.db"
        ).fallbackToDestructiveMigration().build()
        setContent {
            PrioridadesTheme {
                val navHost = rememberNavController()
                PrioridadNavHost(navHost, prioridadDb)
            }
        }
    }

    }






    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun PrioridadScreenPreview() {
        val prioridadList = listOf(
            PrioridadEntity(1, "Enel", 9),
            PrioridadEntity(2, "Juan", 9),
        )
}
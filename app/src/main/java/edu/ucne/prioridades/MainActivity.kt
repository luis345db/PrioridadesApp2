package edu.ucne.prioridades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.prioridades.data.local.database.PrioridadDB
import edu.ucne.prioridades.data.local.entities.PrioridadEntity
import edu.ucne.prioridades.presentation.navigation.PrioridadNavHost

import edu.ucne.prioridades.presentation.prioridad.PrioridadListScreen
import edu.ucne.prioridades.ui.theme.PrioridadesTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var prioridadDb: PrioridadDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDB::class.java, "Prioridad.db"
        ).fallbackToDestructiveMigration().build()

        setContent {
            PrioridadesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ){
                      val navHost = rememberNavController()
                        PrioridadNavHost(navHost)
                    }
                }
            }
        }
    }



   /* private suspend fun savePrioridad(prioridad: PrioridadEntity) {
        prioridadDb.prioridadDao().save(prioridad)
    }
*/



    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        PrioridadesTheme {
            val prioridadList = listOf(
                PrioridadEntity(1, "Alta", 4),
                PrioridadEntity(2, "Baja", 20),
            )
            PrioridadListScreen(
                prioridadList,
                onAddPrioridad = {}
            )
        }
    }
}
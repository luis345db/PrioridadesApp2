package edu.ucne.prioridades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import edu.ucne.prioridades.data.local.database.PrioridadDB
import edu.ucne.prioridades.ui.theme.PrioridadesTheme

class MainActivity : ComponentActivity() {

    private lateinit var prioridadDb: PrioridadDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        prioridadDb = Room.databaseBuilder(
            applicationContext,
            PrioridadDB::class.java,"Prioridad.db"
        ).fallbackToDestructiveMigration().build()
        setContent {
            PrioridadesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PrioridadScreen(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PrioridadScreen(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        )       {
                    OutlinedTextField(
                        modifier =Modifier.fillMaxWidth(),
                        label = {
                Text(text = "Descripcion")
            },
            value = "",
            onValueChange = {}

        )

        OutlinedTextField(
            modifier =Modifier.fillMaxWidth(),

            label = {
                Text(text = "Dias Compromiso")
                    },
            value = "",
            onValueChange = {}
        )

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {  }
        ) {
            Icon(Icons.Default.Add, contentDescription ="Guardar")
            Text(text = "Guardar")
            
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    PrioridadesTheme {
        PrioridadScreen("Android")
    }
}
package edu.ucne.prioridades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Room
import edu.ucne.prioridades.data.local.database.PrioridadDB
import edu.ucne.prioridades.data.local.entities.PrioridadEntity
import edu.ucne.prioridades.ui.theme.PrioridadesTheme
import kotlinx.coroutines.launch

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
                        PrioridadScreen()
                    }
                }
            }
        }
    }


    @Composable
    fun PrioridadScreen() {

        var descripcion by remember { mutableStateOf("") }
        var diasCompromiso by remember { mutableStateOf("") }
        var errorMessage: String? by remember {
            mutableStateOf(null)
        }
        val scope = rememberCoroutineScope()


        Scaffold { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Prioridades",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "Descripcion")
                            },
                            value = descripcion,
                            onValueChange = {
                                descripcion = it
                                errorMessage = null
                            },


                            )

                        OutlinedTextField(

                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text(text = "Dias de compromiso")
                            },
                            value = diasCompromiso,
                            onValueChange = {
                                diasCompromiso = it
                                errorMessage = null
                            },
                            /*keyboardActions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )*/
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        errorMessage?.let {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }

                        OutlinedButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                if (descripcion.isBlank())
                                    errorMessage = "La descripción no puede estar vacía"
                                else if (diasCompromiso.isBlank())
                                    errorMessage = "Días de compromiso no puede ir vacío"
                                else if(diasCompromiso.toInt() <= 0 || diasCompromiso.toInt() > 30)
                                    errorMessage = "Días de compromiso no puede ser menor a 1 o mayor a 30"

                                else {
                                    scope.launch {
                                        savePrioridad(
                                            PrioridadEntity(
                                                descripcion = descripcion,
                                                diasCompromiso = diasCompromiso.toInt()
                                            )
                                        )
                                        descripcion = ""
                                        diasCompromiso = ""
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Guardar Prioridad"
                            )
                            Text("Guardar")
                        }
                    }
                }
                val lifecycleOwner = LocalLifecycleOwner.current
                val prioridadList by prioridadDb.prioridadDao().getAll()
                    .collectAsStateWithLifecycle(
                        lifecycleOwner = lifecycleOwner,
                        minActiveState = Lifecycle.State.STARTED,
                        initialValue = emptyList()
                    )
                PrioridadListScreen(prioridadList)
            }
        }
    }

    @Composable
    fun PrioridadListScreen(
        prioridadList: List<PrioridadEntity>
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            Spacer(modifier = Modifier.height(32.dp))
            Text("Lista de Prioridades")

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(prioridadList){
                    PrioridadRow(it = it)
                }
            }
        }

    }
    @Composable
    fun PrioridadRow(it: PrioridadEntity){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(
                text = it.descripcion,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = it.diasCompromiso.toString(),
                modifier = Modifier.weight(2f)
            )
        }
        HorizontalDivider()
    }

    private suspend fun savePrioridad(prioridad: PrioridadEntity) {
        prioridadDb.prioridadDao().save(prioridad)
    }


    /*@Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }
*/
    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        PrioridadesTheme {
            PrioridadScreen()
        }
    }
}
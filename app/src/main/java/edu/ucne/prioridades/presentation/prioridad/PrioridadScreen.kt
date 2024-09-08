package edu.ucne.prioridades.presentation.prioridad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.ucne.prioridades.data.local.database.PrioridadDB
import edu.ucne.prioridades.data.local.entities.PrioridadEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadScreen(
    prioridadDb: PrioridadDB,
    //goPrioridadList: () -> Unit,
    goBack: () -> Unit,
    prioridadId: Int
) {
    val dao = prioridadDb.prioridadDao()

    var descripcion by remember { mutableStateOf("") }
    var diasCompromiso by remember { mutableStateOf("") }
    var errorMessage: String? by remember {
        mutableStateOf(null)
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(prioridadId) {
        if (prioridadId != 0) {
            val prioridad = dao.find(prioridadId)
            if (prioridad != null) {
                descripcion = prioridad.descripcion
                diasCompromiso = prioridad.diasCompromiso.toString()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Registro de Prioridades") },
                navigationIcon = {
                    IconButton(onClick = { goBack }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menú")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    ) { innerPadding ->

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
                                   /* savePrioridad(
                                        PrioridadEntity(
                                            descripcion = descripcion,
                                            diasCompromiso = diasCompromiso.toInt()
                                        )
                                    )*/
                                    descripcion = ""
                                    diasCompromiso = ""
                                }
                                goBack()
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

        }
    }
}


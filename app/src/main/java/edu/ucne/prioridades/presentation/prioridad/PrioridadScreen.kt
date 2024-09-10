package edu.ucne.prioridades.presentation.prioridad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
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
import edu.ucne.prioridades.data.local.dao.PrioridadDao

import edu.ucne.prioridades.data.local.database.PrioridadDb
import edu.ucne.prioridades.data.local.entities.PrioridadEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadScreen(
    prioridadDb: PrioridadDb,
    //goPrioridadList: () -> Unit,
    goBack: () -> Unit,
    prioridadId: Int
) {
    val dao = prioridadDb.prioridadDao()
    val scope = rememberCoroutineScope()
    var descripcion by remember { mutableStateOf("") }
    var diasCompromiso by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }


    val diasCompromisoInt = diasCompromiso.toIntOrNull()
    val isDescripcionValid = descripcion.isNotBlank()
    val isDiasCompromisoValid = diasCompromisoInt != null && diasCompromisoInt > 0
    val isValid = isDescripcionValid && isDiasCompromisoValid

    errorMessage = when {
        !isDescripcionValid -> "Favor ingresar la descripción"
        !isDiasCompromisoValid -> "El valor de días de compromiso debe ser un número positivo mayor que cero"
        else -> null
    }
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
                            Text(text = "Descripción")
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
                            Text(text = "Días de compromiso")
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
                        onClick = {
                            if (isValid) {
                                scope.launch {
                                    try {
                                        val existePrioridad = dao.findByDescripcion(descripcion)
                                        if (existePrioridad != null && existePrioridad.prioridadId != prioridadId) {
                                            errorMessage =
                                                "Ya existe una prioridad con esta descripción"
                                            return@launch
                                        }

                                        val prioridad = PrioridadEntity(
                                            prioridadId = if (prioridadId == 0) null else prioridadId,
                                            descripcion = descripcion,
                                            diasCompromiso = diasCompromisoInt!!
                                        )

                                        savePrioridad(dao, prioridad)
                                        goBack()
                                    } catch (e: Exception) {
                                        errorMessage = "Error al guardar la prioridad."
                                    }
                                }
                            }
                        },

                    ) {
                        Text(text = "Guardar")
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }

                    if (prioridadId != 0) {
                        Button(
                            onClick = {
                                scope.launch {
                                    try {
                                        dao.delete(
                                            PrioridadEntity(
                                                prioridadId = prioridadId
                                            )
                                        )
                                        goBack()
                                    } catch (e: Exception) {
                                        errorMessage = "Error al eliminar la prioridad."
                                    }
                                }
                            }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Guardar Prioridad"
                            )
                            Text("Eliminar")


                        }


                    }
                }

            }
        }
    }
}
suspend fun savePrioridad(dao: PrioridadDao, prioridad: PrioridadEntity) {
        if (prioridad.descripcion.isBlank()) {
            throw IllegalArgumentException("Favor ingresar la descripción")
        }
        if (prioridad.diasCompromiso == null || prioridad.diasCompromiso!! <= 0) {
            throw IllegalArgumentException("No ingresar cero ni dígitos menores que cero")
        }
        val existePrioridad = dao.findByDescripcion(prioridad.descripcion)
        if (existePrioridad != null && existePrioridad.prioridadId != prioridad.prioridadId) {
            throw IllegalArgumentException("Ya existe una prioridad con esta descripción")
        }
        dao.save(prioridad)
}
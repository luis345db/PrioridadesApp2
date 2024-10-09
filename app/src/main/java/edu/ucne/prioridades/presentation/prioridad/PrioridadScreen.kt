package edu.ucne.prioridades.presentation.prioridad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.prioridades.data.local.dao.PrioridadDao

import edu.ucne.prioridades.presentation.prioridad.PrioridadViewModel
import edu.ucne.prioridades.data.local.entities.PrioridadEntity
import kotlinx.coroutines.launch
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.prioridades.ui.theme.PrioridadesTheme


@Composable
fun PrioridadScreen(

    viewModel: PrioridadViewModel = hiltViewModel(),
    goBack: () -> Unit,
    prioridadId: Int
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = prioridadId) {
        if (prioridadId != 0) {
            viewModel.selectedPriodidad(prioridadId)
        }
    }

    PrioridadBodyScreen(
        uiState = uiState,
        onDescripcionChange = viewModel::onDescripcionChange,
        onDiasCompromisoChange = viewModel::onDiasCompromisoChange,
        onPrioridadIdChange = viewModel::onPrioidadIdChange,
        savePrioridad = viewModel::save,
        deletePrioridad = viewModel::delete,
        nuevoPrioridad = viewModel::nuevo,
        goBack = goBack,
        prioridadId = prioridadId
    )
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadBodyScreen(
    uiState: PrioridadViewModel.UiState,
    onDescripcionChange: (String) -> Unit,
    onDiasCompromisoChange: (Int) -> Unit,
    onPrioridadIdChange: (Int) -> Unit,
    savePrioridad: () -> Unit,
    prioridadId: Int,
    deletePrioridad: () -> Unit,
    nuevoPrioridad: () -> Unit,

    goBack: () -> Unit,

){

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Registro de Prioridades")
                },
                navigationIcon = {
                    IconButton(onClick = goBack ) {
                        Icon(imageVector = Icons.Filled.Menu,
                            contentDescription = "Menú"
                        )
                    }
                }
            )

            //Spacer(modifier = Modifier.height(16.dp))
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
                        value = uiState.descripcion?: "",
                        onValueChange = onDescripcionChange,
                        )

                    OutlinedTextField(
                        label = { Text(text = "Días Compromiso") },
                        value = uiState.diasCompromiso?.toString() ?: "",
                        onValueChange = { newValue ->
                            if (newValue.isEmpty()) {

                                onDiasCompromisoChange(0)
                            } else {

                                val intValue = newValue.toIntOrNull()
                                if (intValue != null) {
                                    onDiasCompromisoChange(intValue)
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    uiState.errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }

                    OutlinedButton(
                        onClick = {
                            nuevoPrioridad()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "new button"
                        )
                        Text(text = "Nuevo")
                    }
                    val scope = rememberCoroutineScope()
                    OutlinedButton(

                        onClick = {
                            savePrioridad()
                            goBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "save button"
                        )
                        Text(text = "Guardar")
                    }

                    if (prioridadId != 0) {
                        Button(
                            onClick = {
                                deletePrioridad()
                                goBack()

                                }
                            ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Eliminar Prioridad"
                            )
                            Text("Eliminar")


                        }


                    }
                }

            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrioridadScreenPreview(){
    PrioridadesTheme(){
        PrioridadScreen(
            prioridadId = 0,
            goBack = {}
        )
    }
}

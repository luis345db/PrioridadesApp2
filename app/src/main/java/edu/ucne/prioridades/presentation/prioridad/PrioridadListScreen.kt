package edu.ucne.prioridades.presentation.prioridad

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.prioridades.data.local.entities.PrioridadEntity
import edu.ucne.prioridades.data.remote.dto.PrioridadDto
import edu.ucne.prioridades.presentation.prioridad.PrioridadViewModel.UiState

@Composable
fun PrioridadListScreen(
    viewModel: PrioridadViewModel = hiltViewModel(),
    goToPrioridad: (Int) -> Unit,
    goToAddPrioridad: () -> Unit,
    onEditPrioridad: () -> Unit,
    onDeletePrioridad: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getPrioridades()
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PrioridadListBodyScreen(
        uiState,
        goToPrioridad,
        goToAddPrioridad
    )
}

@Composable
fun PrioridadListBodyScreen(
    uiState: UiState,
    goToPrioridad: (Int) -> Unit,
    goToAddPrioridad: () -> Unit
){
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick =  goToAddPrioridad,
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Añadir Prioridad"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("Lista de Prioridades")

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.prioridades) {
                    PrioridadRow(
                        it,
                        goToPrioridad,
                        goToAddPrioridad
                    )
                }
            }
        }

    }
}


@Composable
fun PrioridadRow(
    it: PrioridadDto,
    goToPrioridad: (Int) -> Unit,
    goToAddPrioridad: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable { goToPrioridad(it.prioridadId ?: 0) }

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


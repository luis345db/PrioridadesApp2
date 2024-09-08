package edu.ucne.prioridades.presentation.prioridad

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucne.prioridades.data.local.entities.PrioridadEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioridadListScreen(
    prioridadList: List<PrioridadEntity>,

    goToPrioridad: (Int) -> Unit,
    goToAddPrioridad: () -> Unit,
) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Lista de Prioridades") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { goToAddPrioridad() },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Prioridad")
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(prioridadList) { prioridad ->
                    PrioridadRow(
                        prioridad = prioridad,
                        goToPrioridad = goToPrioridad
                    )
                }
            }
        }
    )

}


@Composable
fun PrioridadRow(
    prioridad: PrioridadEntity,
    goToPrioridad: (Int) -> Unit,
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable { goToPrioridad(prioridad.prioridadId ?: 0) }

    ){

        Text(
            text = prioridad.descripcion,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = prioridad.diasCompromiso.toString(),
            modifier = Modifier.weight(2f)
        )
    }
    HorizontalDivider()
}


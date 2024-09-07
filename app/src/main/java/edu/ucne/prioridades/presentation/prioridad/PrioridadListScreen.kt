package edu.ucne.prioridades.presentation.prioridad

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
    onAddPrioridad: () -> Unit
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Prioridades")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPrioridad) {
                Icon(Icons.Filled.Add, "Agregar nueva entidad")
            }
        }
    ){
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(it)
        ){
            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(prioridadList) {
                    PrioridadRow(it)
                }
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


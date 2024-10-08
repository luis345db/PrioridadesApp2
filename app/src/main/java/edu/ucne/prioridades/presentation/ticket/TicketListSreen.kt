package edu.ucne.prioridades.presentation.ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.prioridades.data.local.entities.PrioridadEntity
import edu.ucne.prioridades.data.local.entities.TicketEntity
import kotlinx.coroutines.CoroutineScope

@Composable
fun TicketListScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    goToTicket: (Int) -> Unit,
    createTicket: () -> Unit,
    onEditTicket: () -> Unit,
    onDeleteTicket: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicketListBodyScreen(
        uiState,
        goToTicket,
        createTicket
    )
}


@Composable
fun TicketListBodyScreen(
    uiState: UiState,
    goToTicket: (Int) -> Unit,
    createTicket: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = createTicket,
            ) {
                Icon(
                     Icons.Default.Add,
                    contentDescription = "Create a new Ticket"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("Lista de tickets")

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.tickets) {
                    TicketRow(
                        it,
                        goToTicket,
                        createTicket,
                        prioridades = uiState.prioridades
                    )
                }
            }
        }
    }
}
@Composable
private fun TicketRow(
    it: TicketEntity,
    goToTicket: (Int) -> Unit,
    createTicket: () -> Unit,
    prioridades: List<PrioridadEntity>
) {
val descripcionPrio =prioridades.find {prioridad ->
    prioridad.prioridadId == it.prioridadId

}?.descripcion
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                goToTicket(it.ticketId ?: 0)
            }
        ) {
            Text(modifier = Modifier.weight(1f), text = it.ticketId.toString())
            Text(
                modifier = Modifier.weight(2f),
                text = it.cliente,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(modifier = Modifier.weight(2f), text = it.asunto)

            Text(modifier = Modifier.weight(2f), text = it.descripcion)

            Text(modifier = Modifier.weight(2f), text = descripcionPrio.toString())
        }
    }
    HorizontalDivider()

}

package edu.ucne.prioridades.presentation.ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.prioridades.data.local.entities.PrioridadEntity
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import edu.ucne.prioridades.ui.theme.PrioridadesTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun TicketScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    ticketId: Int,
    goBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = ticketId) {
        if (ticketId != 0) {
            viewModel.selectedTicket(ticketId)
        }
    }
    TicketBodyScreen(
        uiState = uiState,
        onClienteChange = viewModel::onClienteChange,
        onAsuntoChange = viewModel::onAsuntoChange,
        onTicketIdChange = viewModel::onTicketIdChange,
        saveTicket = viewModel::save,
        deleteTicket = viewModel::delete,
        nuevoTicket = viewModel::nuevo,
        goBack = goBack,
        onPrioridadIdChange = viewModel::onPrioridadIdChange,
        onDescripcionChange = viewModel::onDescripcionChange,
        ticketId = ticketId,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketBodyScreen(
    uiState: UiState,
    onClienteChange: (String) -> Unit,
    onAsuntoChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onTicketIdChange: (Int) -> Unit,
    saveTicket: () -> Unit,
    ticketId: Int,
    deleteTicket: () -> Unit,
    nuevoTicket: () -> Unit,
    goBack: () -> Unit,
    onPrioridadIdChange: (Int) -> Unit
) {
    val priorities = uiState.prioridades
    var expanded by remember { mutableStateOf(false) }
    var selectedPriority by remember { mutableStateOf(uiState.prioridadId ?: 0) }
    var priorityDescription by remember {
        mutableStateOf(priorities.find { it.prioridadId == selectedPriority }?.descripcion ?: "Seleccionar prioridad")


    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Ticket")
                },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        label = { Text(text = "Cliente") },
                        value = uiState.cliente ?: "",
                        onValueChange = onClienteChange,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text(text = "Asunto") },
                        value = uiState.asunto ?: "",
                        onValueChange = onAsuntoChange,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    uiState.errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }
                    OutlinedTextField(
                        label = { Text(text = "Descripcion") },
                        value = uiState.descripcion ?: "",
                        onValueChange = onDescripcionChange,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Priority Dropdown Menu
                    OutlinedTextField(
                        readOnly = true,
                        label = { Text("Prioridad") },
                        value = priorityDescription,
                        onValueChange = { /* No-op */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Select Priority",
                                modifier = Modifier
                                    .clickable { expanded = !expanded }
                            )
                        }
                    )
                    DropdownMenu (
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        if (priorities.isEmpty()) {
                            Text("No hay prioridades disponibles", modifier = Modifier.padding(16.dp))
                        } else {
                            priorities.forEach { prioridad ->
                                DropdownMenuItem(
                                    text = { Text(prioridad.descripcion) },
                                    onClick = {
                                        selectedPriority = prioridad.prioridadId ?: 0
                                        priorityDescription = prioridad.descripcion
                                        onPrioridadIdChange(selectedPriority)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        OutlinedButton(
                            onClick = {
                                nuevoTicket()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "new button"
                            )
                            Text(text = "Nuevo")
                        }
                        OutlinedButton(
                            onClick = {
                                saveTicket()
                                goBack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "save button"
                            )
                            Text(text = "Guardar")
                        }

                        if (ticketId != 0) {
                            Button(
                                onClick = {
                                    deleteTicket()
                                    goBack()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar Ticket"
                                )
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}

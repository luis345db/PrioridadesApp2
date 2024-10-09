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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import edu.ucne.prioridades.ui.theme.PrioridadesTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun TicketScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    ticketId: Int,
    goBack: () -> Unit,
    isTicketDelete: Boolean
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(ticketId) {
        if (isTicketDelete) {
            viewModel.selectedTicket(ticketId)
        } else {
            viewModel.selectedTicket(ticketId)
        }
    }
    TicketBodyScreen(
        uiState = uiState,
        onFechaChange = viewModel::onFechaChange,
        onClienteIdChange = viewModel::onClienteIdChange,
        onSistemaIdChange = viewModel::onSistemaIdChange,
        onPrioridadIdChange = viewModel::onPrioridadChange,
        onSolicitadoPorChange = viewModel::onSolicitadoPorChange,
        onAsuntoChange = viewModel::onAsuntoChange,
        onDescripcionChange = viewModel::onDescripcionChange,
        saveTicket = viewModel::save,
        deleteTicket = viewModel::delete,
        goBack = goBack,
        isTicketDelete = isTicketDelete,
        ticketId = ticketId,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketBodyScreen(
    uiState: UiState,
    onFechaChange: (Date) -> Unit,
    onClienteIdChange: (Int) -> Unit,
    onSistemaIdChange: (Int) -> Unit,
    onPrioridadIdChange: (Int) -> Unit,
    onSolicitadoPorChange: (String) -> Unit,
    onAsuntoChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    saveTicket: () -> Unit,
    deleteTicket: () -> Unit,
    goBack: () -> Unit,
    isTicketDelete: Boolean,
    ticketId: Int,
) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formattedDate = uiState.fecha.let { dateFormatter.format(it) } ?: ""

    var showDatePicker by remember { mutableStateOf(false) }
    var clienteExpanded by remember { mutableStateOf(false) }
    var sistemaExpanded by remember { mutableStateOf(false) }
    var prioridadExpanded by remember { mutableStateOf(false) }

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
                .padding(3.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp)
                ) {

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        label = { Text("Fecha") },
                        value = formattedDate,
                        readOnly = true,
                        onValueChange = {},
                        trailingIcon = {
                            Icon(Icons.Default.Add, contentDescription = null,
                                modifier = Modifier.clickable { showDatePicker = true })
                        }
                    )

                    if (showDatePicker) {
                        DatePickerDialog(
                            onDateSelected = { date ->
                                onFechaChange(date)
                                showDatePicker = false
                            },
                            onDismissRequest = { showDatePicker = false }
                        )
                    }

                    // Campo Cliente
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(3.dp)
                                .clickable { clienteExpanded = true },
                            label = { Text("Cliente") },
                            value = if (uiState.clienteId != 0) uiState.clientes.firstOrNull { it.clienteId == uiState.clienteId }?.nombre
                                ?: "" else "",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.clickable { clienteExpanded = true }
                                )
                            }
                        )
                        DropdownMenu(
                            expanded = clienteExpanded,
                            onDismissRequest = { clienteExpanded = false }
                        ) {
                            uiState.clientes.forEach { cliente ->
                                DropdownMenuItem(
                                    text = { Text(cliente.nombre) },
                                    onClick = {
                                        onClienteIdChange(cliente.clienteId ?: 0)
                                        clienteExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Campo Sistema
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(3.dp)
                                .clickable { sistemaExpanded = true },
                            label = { Text("Sistema") },
                            value = if (uiState.sistemaId != 0) uiState.sistemas.firstOrNull { it.sistemasId == uiState.sistemaId }?.sistemaNombre
                                ?: "" else "",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.clickable { sistemaExpanded = true }
                                )
                            }
                        )
                        DropdownMenu(
                            expanded = sistemaExpanded,
                            onDismissRequest = { sistemaExpanded = false }
                        ) {
                            uiState.sistemas.forEach { sistema ->
                                DropdownMenuItem(
                                    text = { Text(sistema.sistemaNombre) },
                                    onClick = {
                                        onSistemaIdChange(sistema.sistemasId ?: 0)
                                        sistemaExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Campo Prioridad
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(3.dp)
                                .clickable { prioridadExpanded = true },
                            label = { Text("Prioridad") },
                            value = uiState.prioridades.firstOrNull { it.prioridadId == uiState.prioridadId }?.descripcion
                                ?: "",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.clickable { prioridadExpanded = true }
                                )
                            }
                        )
                        DropdownMenu(
                            expanded = prioridadExpanded,
                            onDismissRequest = { prioridadExpanded = false }
                        ) {
                            uiState.prioridades.forEach { prioridad ->
                                DropdownMenuItem(
                                    text = { Text(prioridad.descripcion) },
                                    onClick = {
                                        onPrioridadIdChange(prioridad.prioridadId ?: 0)
                                        prioridadExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp),
                        label = { Text("Solicitado por") },
                        value = uiState.solicitadoPor,
                        onValueChange = onSolicitadoPorChange
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp),
                        label = { Text("Asunto") },
                        value = uiState.asunto,
                        onValueChange = onAsuntoChange
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp),
                        label = { Text("Descripción") },
                        value = uiState.descripcion,
                        onValueChange = onDescripcionChange
                    )

                    Spacer(modifier = Modifier.padding(3.dp))

                    uiState.errorMessage?.let {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 3.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = it,
                                color = Color.Red
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp),
                        horizontalArrangement = Arrangement.SpaceBetween // Distribuye los botones
                    ) {

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

@Composable
fun DatePickerDialog(
    onDateSelected: (Date) -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                onDateSelected(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    LaunchedEffect(Unit) {
        datePickerDialog.show()
    }
    DisposableEffect(Unit) {
        onDispose {
            datePickerDialog.dismiss()
        }
    }
}
package edu.ucne.prioridades.presentation.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridades.data.local.entities.PrioridadEntity
import edu.ucne.prioridades.data.local.entities.TicketEntity
import edu.ucne.prioridades.data.remote.dto.ClienteDto
import edu.ucne.prioridades.data.remote.dto.PrioridadDto
import edu.ucne.prioridades.data.remote.dto.SistemaDto
import edu.ucne.prioridades.data.remote.dto.TicketDto
import edu.ucne.prioridades.data.repository.PrioridadRepository
import edu.ucne.prioridades.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val prioridadRepository: PrioridadRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTickets()
        getPrioridades()
    }

    fun save() {
        viewModelScope.launch {
            val errorMessage = validate()
            if (errorMessage != null) {
                _uiState.update { it.copy(errorMessage = errorMessage) }
            } else {
                ticketRepository.save(_uiState.value.toEntity())
                nuevo()
            }
        }
    }

    private fun validate(): String? {
        return when {
            _uiState.value.clienteId.toString().isBlank() -> "El nombre del cliente no puede estar vacío"
            _uiState.value.asunto.isBlank() -> "El asunto no puede estar vacío"
            _uiState.value.descripcion.isBlank() -> "La descripción no puede estar vacía"
            _uiState.value.fecha == null -> "La fecha no puede estar vacía"
            else -> null
        }
    }

    fun nuevo() {
        _uiState.update {
            it.copy(
                ticketId = null,
                fecha = Date(),
                clienteId = 0,
                sistemaId = 0,
                prioridadId = 0,
                solicitadoPor = "",
                asunto = "",
                descripcion = "",
                errorMessage = null
            )
        }
    }

    fun selectedTicket(ticketId: Int){
        viewModelScope.launch {
            if(ticketId > 0){
                val ticket = ticketRepository.getTicket(ticketId)
                _uiState.update {
                    it.copy(
                        ticketId = ticket.ticketId,
                        fecha = ticket.fecha,
                        clienteId = ticket.clienteId ?: 0,
                        sistemaId = ticket.sistemaId ?: 0,
                        prioridadId = ticket.prioridadId ?: 0,
                        solicitadoPor = ticket.solicitadoPor ?: "",
                        asunto = ticket.asunto ?: "",
                        descripcion = ticket.descripcion ?: ""
                    )
                }
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            ticketRepository.delete(_uiState.value.ticketId!!)
            nuevo()
        }
    }

    private fun getTickets() {
        viewModelScope.launch {
            val tickets = ticketRepository.getTickets()
            _uiState.update {
                it.copy(tickets = tickets)
            }
        }
    }

    private fun getPrioridades() {
        viewModelScope.launch {
            val prioridades = prioridadRepository.getPrioridades()
            _uiState.update {
                it.copy(prioridades = prioridades)
            }
        }
    }

    fun onClienteIdChange(clienteId: Int) {
        _uiState.update {
            it.copy(clienteId = clienteId, errorMessage = null)
        }
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    fun onAsuntoChange(asunto: String) {
        _uiState.update {
            it.copy(asunto = asunto)
        }
    }

    fun onTicketIdChange(ticketId: Int) {
        _uiState.update {
            it.copy(ticketId = ticketId)
        }
    }

    fun onPrioridadIdChange(prioridadId: Int) {
        _uiState.update {
            it.copy(prioridadId = prioridadId)
        }
    }

}

data class UiState(
    val ticketId: Int? = null,
    val fecha: Date = Date(),
    val clienteId: Int = 0,
    val sistemaId: Int = 0,
    val prioridadId: Int = 0,
    val solicitadoPor: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val errorMessage: String? = null,
    val tickets: List<TicketDto> = emptyList(),
    val prioridades: List<PrioridadDto> = emptyList(),
    val sistemas: List<SistemaDto> = emptyList(),
    val clientes: List<ClienteDto> = emptyList()

)

fun UiState.toEntity() = TicketDto(
    ticketId = ticketId,
    fecha = fecha,
    clienteId = clienteId,
    sistemaId = sistemaId,
    prioridadId = prioridadId,
    solicitadoPor = solicitadoPor,
    asunto = asunto,
    descripcion = descripcion

    )
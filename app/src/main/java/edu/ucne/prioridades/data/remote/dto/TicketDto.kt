package edu.ucne.prioridades.data.remote.dto

import android.media.audiofx.AudioEffect.Descriptor
import java.util.Date

data class TicketDto(
    val ticketId: Int?,
    val fecha : Date,
    val clienteId: Int,
    val sistemaId: Int,
    val prioridadId: Int,
    val solicitadoPor: String,
    val asunto: String,
    val descripcion: String
)
package edu.ucne.prioridades.data.repository

import edu.ucne.prioridades.data.local.dao.TicketDao
import edu.ucne.prioridades.data.local.entities.TicketEntity
import edu.ucne.prioridades.data.remote.TicketsApi
import edu.ucne.prioridades.data.remote.dto.TicketDto
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketsApi: TicketsApi
) {
    suspend fun save(ticket: TicketDto) = ticketsApi.saveTickets(ticket)

    suspend fun getTicket(ticketId: Int) = ticketsApi.getTickets(ticketId)


    suspend fun delete(ticket: Int) {
        try {
            ticketsApi.deleteTickets(ticket)
        } catch (e: Exception) {
            // Maneja el error aquí (puedes lanzar una excepción personalizada o hacer otra cosa)
            throw e
        }
    }
    suspend fun getTickets() = ticketsApi.getAllTickets()
}
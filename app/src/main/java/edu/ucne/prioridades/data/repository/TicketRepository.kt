package edu.ucne.prioridades.data.repository

import edu.ucne.prioridades.data.local.dao.TicketDao
import edu.ucne.prioridades.data.local.entities.TicketEntity
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val ticketDao: TicketDao
) {
    suspend fun save(ticket: TicketEntity) = ticketDao.save(ticket)

    suspend fun getTicket(ticketId: Int) = ticketDao.find(ticketId)

    suspend fun findCliente(cliente: String) = ticketDao.findCliente(cliente)

    suspend fun findAsunto(asunto: String) = ticketDao.findAsunto(asunto)

    suspend fun findDescripcion(descripcion: String) = ticketDao.findDescripcion(descripcion)

    suspend fun delete(ticket: TicketEntity) = ticketDao.delete(ticket)

    fun getTickets() = ticketDao.getAll()
}
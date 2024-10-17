package edu.ucne.prioridades.data.remote

import edu.ucne.prioridades.data.remote.dto.PrioridadDto
import edu.ucne.prioridades.data.remote.dto.TicketDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TicketsApi {

    @GET("/api/Tickets/{id}")
    suspend fun getTickets(@Path("id") id:Int): TicketDto

    @GET("/api/Tickets")
    suspend fun getAllTickets(): List<TicketDto>

    @POST("/api/Tickets")
    suspend fun saveTickets(@Body ticketDto: TicketDto?): TicketDto

    @DELETE("/api/Tickets/{id}")
    suspend fun deleteTickets(@Path("id") id: Int): Unit

}
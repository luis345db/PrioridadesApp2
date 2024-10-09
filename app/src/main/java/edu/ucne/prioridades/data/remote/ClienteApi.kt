package edu.ucne.prioridades.data.remote

import edu.ucne.prioridades.data.remote.dto.ClienteDto
import edu.ucne.prioridades.data.remote.dto.PrioridadDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ClienteApi {
    @GET("/api/Clientes/{id}")
    suspend fun getClientes(@Path("id") id:Int): ClienteDto

    @GET("/api/Clientes")
    suspend fun getAllClientes(): List<ClienteDto>

    @POST("/api/Clientes")
    suspend fun saveClientes(@Body clienteDto: ClienteDto?): ClienteDto

    @DELETE("/api/Clientes/{id}")
    suspend fun deleteClientes(@Path("id") id: Int)

}
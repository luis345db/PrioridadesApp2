package edu.ucne.prioridades.data.remote

import edu.ucne.prioridades.data.remote.dto.PrioridadDto
import edu.ucne.prioridades.data.remote.dto.SistemaDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SistemaApi {
    @GET("/api/Sistemas/{id}")
    suspend fun getSistemas(@Path("id") id:Int): SistemaDto

    @GET("/api/Sistemas")
    suspend fun getAllSistemas(): List<SistemaDto>

    @POST("/api/Sistemas")
    suspend fun saveSistemas(@Body sistemaDto: SistemaDto?): SistemaDto

    @DELETE("/api/Sistemas/{id}")
    suspend fun deleteSistemas(@Path("id") id: Int)

}
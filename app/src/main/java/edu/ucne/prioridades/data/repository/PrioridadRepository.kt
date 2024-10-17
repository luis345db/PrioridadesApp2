package edu.ucne.prioridades.data.repository

import edu.ucne.prioridades.data.remote.PrioridadesApi
import edu.ucne.prioridades.data.remote.dto.PrioridadDto
import javax.inject.Inject

class PrioridadRepository @Inject constructor(
    private val prioridadesApi: PrioridadesApi
) {
    suspend fun save(prioridad : PrioridadDto) = prioridadesApi.savePrioridad(prioridad)

    suspend fun  getPrioridad(id:Int) = prioridadesApi.getPrioridad(id)

    suspend fun  delete(id: Int) {
        try {
            prioridadesApi.deletePrioridad(id)
        } catch (e: Exception) {

            throw e
        }
    }

    suspend fun  getPrioridades()= prioridadesApi.getAllPrioridades()

}
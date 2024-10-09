package edu.ucne.prioridades.data.repository

import edu.ucne.prioridades.data.remote.PrioridadesApi
import edu.ucne.prioridades.data.remote.SistemaApi
import edu.ucne.prioridades.data.remote.dto.PrioridadDto
import edu.ucne.prioridades.data.remote.dto.SistemaDto
import javax.inject.Inject

class SistemaRepository@Inject constructor(
    private val sistemaApi: SistemaApi
) {
    suspend fun save(sistema : SistemaDto) = sistemaApi.saveSistemas(sistema)

    suspend fun  getSistema(id:Int) = sistemaApi.getSistemas(id)

    suspend fun  delete(id: Int) = sistemaApi.deleteSistemas(id)

    suspend fun  getSistema()= sistemaApi.getAllSistemas()

}
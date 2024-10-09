package edu.ucne.prioridades.data.repository

import edu.ucne.prioridades.data.remote.ClienteApi
import edu.ucne.prioridades.data.remote.dto.ClienteDto
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    private val clienteApi: ClienteApi
) {
    suspend fun save(cliente: ClienteDto) = clienteApi.saveClientes(cliente)

    suspend fun getCliente(clienteId: Int) = clienteApi.getClientes(clienteId)


    suspend fun delete(cliente: Int) = clienteApi.deleteClientes(cliente)

    suspend fun getCliente() = clienteApi.getAllClientes()
}
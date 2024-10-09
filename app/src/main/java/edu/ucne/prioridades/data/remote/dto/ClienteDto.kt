package edu.ucne.prioridades.data.remote.dto

data class ClienteDto (
    val clienteId:Int?,
    val nombre: String,
    val telefono: String,
    val celular: String,
    val rnc: String,
    val email: String,
    val direccion: String
)

package edu.ucne.prioridades.presentation.navigation

import android.adservices.adid.AdId
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object  PrioridadList : Screen() // Consulta

    @Serializable
    data class  Prioridad(val prioridadId: Int) : Screen()  //Registro
}
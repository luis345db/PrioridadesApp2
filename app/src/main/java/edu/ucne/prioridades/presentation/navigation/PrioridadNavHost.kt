package edu.ucne.prioridades.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.prioridades.presentation.prioridad.PrioridadListScreen
import edu.ucne.prioridades.presentation.prioridad.PrioridadScreen
import edu.ucne.prioridades.presentation.prioridad.PrioridadViewModel.UiState
import edu.ucne.prioridades.data.local.database.PrioridadDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun PrioridadNavHost(
    navHost: NavHostController,

) {
    NavHost(
        navController = navHost,
        startDestination = Screen.PrioridadList
    ) {
        composable<Screen.PrioridadList> {
            PrioridadListScreen(
             //   prioridadList = UiState.prioridades,
                goToPrioridad = {
                    navHost.navigate(Screen.Prioridad(it))
                },
                goToAddPrioridad = {
                    navHost.navigate(Screen.Prioridad(0))
                },
                onEditPrioridad = {
                    navHost.navigate(Screen.Prioridad(0))
                },
                onDeletePrioridad = {
                    navHost.navigate(Screen.Prioridad(0)) },


                )
        }

        composable<Screen.Prioridad> {
            val args = it.toRoute<Screen.Prioridad>()
            PrioridadScreen(
                prioridadId = args.prioridadId,
                goBack = {
                    navHost.navigateUp()
                },

            )
        }
    }
}
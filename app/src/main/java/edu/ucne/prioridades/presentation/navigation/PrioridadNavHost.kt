package edu.ucne.prioridades.presentation.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import edu.ucne.prioridades.presentation.ticket.TicketListScreen
import edu.ucne.prioridades.presentation.ticket.TicketScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Modifier


@Composable
fun PrioridadNavHost(
    navHost: NavHostController,

) {

    NavHost(
        navController = navHost,
        startDestination = Screen.TicketList
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

        composable<Screen.TicketList> {
            TicketListScreen(
                goToTicket = {
                    navHost.navigate(Screen.Ticket(it))
                },
                createTicket = {
                    navHost.navigate(Screen.Ticket(0))
                },
                onDeleteTicket = {
                    navHost.navigate(Screen.Ticket(0))
                },
                onEditTicket = {
                    navHost.navigate(Screen.Ticket(0))
                }


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

        composable<Screen.Prioridad> {
            val args = it.toRoute<Screen.Prioridad>()
            PrioridadScreen(
                prioridadId = args.prioridadId,
                goBack = {
                    navHost.navigateUp()
                },

                )
        }

        composable<Screen.Ticket> {
            val args =it.toRoute<Screen.Ticket>()
            TicketScreen(
                ticketId = args.ticketId,
                goBack = {
                    navHost.navigateUp()
                }
            )
        }
    }
}
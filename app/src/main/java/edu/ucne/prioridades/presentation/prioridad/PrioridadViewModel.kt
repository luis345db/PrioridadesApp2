package edu.ucne.prioridades.presentation.prioridad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridades.data.remote.dto.PrioridadDto
import edu.ucne.prioridades.data.repository.PrioridadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrioridadViewModel @Inject constructor(
    private val prioridadRepository: PrioridadRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPrioridades()
    }

    fun save() {
        viewModelScope.launch {
            if(_uiState.value.descripcion.isNullOrBlank() && _uiState.value.diasCompromiso == 0){
                _uiState.update{
                    it.copy(errorMessage = "Campos vacios")
                }
            }
            else{
                prioridadRepository.save(_uiState.value.toEntity())
            }
        }
    }

    fun nuevo(){
        _uiState.update{
            it.copy(
                prioridadId = null,
                descripcion = "",
                diasCompromiso = null,
                errorMessage= null
            )
        }
    }

    fun selectedPriodidad(prioridadId : Int){
        viewModelScope.launch {
            if(prioridadId > 0){
                val prioridad = prioridadRepository.getPrioridad(prioridadId)
                _uiState.update{
                    it.copy(
                        prioridadId = prioridad?.prioridadId,
                        descripcion = prioridad?.descripcion,
                        diasCompromiso = prioridad?.diasCompromiso,

                    )
                }
            }
        }
    }

    fun delete(){
        viewModelScope.launch {
            prioridadRepository.delete(_uiState.value.prioridadId!!)
        }
    }

    private fun getPrioridades(){
        viewModelScope.launch {
            val prioridades = prioridadRepository.getPrioridades()
                _uiState.update {
                    it.copy(prioridades = prioridades)
                }


        }
    }

    fun onDescripcionChange(descripcion: String){
        _uiState.update{
            it.copy(descripcion = descripcion)
        }
    }

    fun onDiasCompromisoChange(diasCompromiso: Int){
        _uiState.update{
            it.copy(diasCompromiso = diasCompromiso)
        }
    }

    fun onPrioidadIdChange(prioridadId: Int) {
        _uiState.update {
            it.copy(prioridadId = prioridadId)
        }
    }

    data class UiState(
        val prioridadId: Int? = null,
        val descripcion: String? = "",
        val diasCompromiso: Int? = null,
        val errorMessage: String? = null,
        val prioridades: List<PrioridadDto> = emptyList()
    )

    fun UiState.toEntity() = PrioridadDto(
        prioridadId = prioridadId,
        descripcion = descripcion ?: "",
        diasCompromiso = diasCompromiso?: 0
    )




}
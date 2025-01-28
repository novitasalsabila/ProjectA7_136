package com.example.klinik.ui.viewmodel.terapisVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinik.model.Terapis
import com.example.klinik.repository.TerapisRepository
import com.example.klinik.ui.view.Terapis.DestinasiDetailTerapis
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailTerapisUiState {
    data class Success(val terapis: Terapis) : DetailTerapisUiState()
    object Error : DetailTerapisUiState()
    object Loading : DetailTerapisUiState()
}

class DetailTerapisViewModel(
    savedStateHandle: SavedStateHandle,
    private val terapisRepository: TerapisRepository
) : ViewModel() {

    private val id_terapis: String = checkNotNull(savedStateHandle[DestinasiDetailTerapis.ID_TERAPIS])
    var detailTerapisUiState: DetailTerapisUiState by mutableStateOf(DetailTerapisUiState.Loading)
        private set

    init {
        getTerapisById()
    }

    fun getTerapisById() {
        viewModelScope.launch {
            detailTerapisUiState = DetailTerapisUiState.Loading
            detailTerapisUiState = try {
                DetailTerapisUiState.Success(terapis = terapisRepository.getTerapisById(id_terapis))
            } catch (e: IOException) {
                DetailTerapisUiState.Error
            }
        }
    }
}

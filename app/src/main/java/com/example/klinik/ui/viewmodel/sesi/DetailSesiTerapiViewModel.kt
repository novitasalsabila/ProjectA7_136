package com.example.klinik.ui.viewmodel.sesi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinik.model.SesiTerapi
import com.example.klinik.repository.SesiTerapiRepository
import com.example.klinik.ui.view.SesiTerapis.DestinasiDetailSesiTerapi
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailSesiTerapiUiState {
    data class Success(val sesiTerapi: SesiTerapi) : DetailSesiTerapiUiState()
    object Error : DetailSesiTerapiUiState()
    object Loading : DetailSesiTerapiUiState()
}

class DetailSesiTerapiViewModel(
    savedStateHandle: SavedStateHandle,
    private val sesiTerapiRepository: SesiTerapiRepository
) : ViewModel() {

    private val id_sesi: String = checkNotNull(savedStateHandle[DestinasiDetailSesiTerapi.ID_SESI])
    var detailSesiTerapiUiState: DetailSesiTerapiUiState by mutableStateOf(DetailSesiTerapiUiState.Loading)
        private set

    init {
        getSesiTerapiById()
    }

    fun getSesiTerapiById() {
        viewModelScope.launch {
            detailSesiTerapiUiState = DetailSesiTerapiUiState.Loading
            detailSesiTerapiUiState = try {
                DetailSesiTerapiUiState.Success(sesiTerapi = sesiTerapiRepository.getSesiById(id_sesi))
            } catch (e: IOException) {
                DetailSesiTerapiUiState.Error
            }
        }
    }
}

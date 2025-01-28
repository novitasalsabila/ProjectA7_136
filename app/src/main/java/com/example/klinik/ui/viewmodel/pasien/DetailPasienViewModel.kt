package com.example.klinik.ui.viewmodel.pasien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinik.model.Pasien
import com.example.klinik.repository.PasienRepository
import com.example.klinik.ui.view.Pasien.DestinasiDetailPasien
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailPasienUiState {
    data class Success(val pasien: Pasien) : DetailPasienUiState()
    object Error : DetailPasienUiState()
    object Loading : DetailPasienUiState()
}

class DetailPasienViewModel(
    savedStateHandle: SavedStateHandle,
    private val pasienRepository: PasienRepository
) : ViewModel() {

    private val id_pasien: String = checkNotNull(savedStateHandle[DestinasiDetailPasien.ID_PASIEN])
    var detailPasienUiState: DetailPasienUiState by mutableStateOf(DetailPasienUiState.Loading)
        private set

    init {
        getPasienById()
    }

    fun getPasienById() {
        viewModelScope.launch {
            detailPasienUiState = DetailPasienUiState.Loading
            detailPasienUiState = try {
                DetailPasienUiState.Success(pasien = pasienRepository.getPasienById(id_pasien))
            } catch (e: IOException) {
                DetailPasienUiState.Error
            }
        }
    }
}

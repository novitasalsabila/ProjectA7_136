package com.example.klinik.ui.viewmodel.pasien

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinik.model.Pasien
import com.example.klinik.repository.PasienRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.SavedStateHandle
import com.example.klinik.ui.view.Pasien.DestinasiEditPasien


class UpdatePasienViewModel(
    savedStateHandle: SavedStateHandle,
    private val pasienRepository: PasienRepository
) : ViewModel() {
    var updateUiState by mutableStateOf(InsertPasienUiState())
        private set

    private val ID_PASIEN: String = checkNotNull(savedStateHandle[DestinasiEditPasien.ID_PASIEN]) {
        "ID pasien tidak ditemukan di SavedStateHandle"
    }

    init {
        viewModelScope.launch {
            try {
                val pasien = pasienRepository.getPasienById(ID_PASIEN)
                updateUiState = pasien.toUIStatePsn()
            } catch (e: Exception) {
                Log.e("UpdatePasienViewModel", "Error fetching pasien: ${e.message}", e)
            }
        }
    }

    fun updateInsertPasienState(insertUiEvent: InsertPasienUiEvent) {
        updateUiState = updateUiState.copy(insertUiEvent = insertUiEvent)
    }

    fun updatePasien() {
        viewModelScope.launch {
            try {
                pasienRepository.updatePasien(ID_PASIEN, updateUiState.insertUiEvent.toPasien())
            } catch (e: Exception) {
                Log.e("UpdatePasienViewModel", "Error updating pasien: ${e.message}", e)
            }
        }
    }
}

fun Pasien.toUIStatePsn(): InsertPasienUiState = InsertPasienUiState(
    insertUiEvent = this.toInsertUiEvent()
)

package com.example.klinik.ui.viewmodel.terapisVM

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.SavedStateHandle
import com.example.klinik.model.JenisTerapi
import com.example.klinik.model.Terapis
import com.example.klinik.repository.JenisTerapiRepository
import com.example.klinik.repository.TerapisRepository
import com.example.klinik.ui.view.JenisTerapi.DestinasiEditJenisTerapi
import com.example.klinik.ui.view.Terapis.DestinasiEditTerapis
import com.example.klinik.ui.viewmodel.JenisTerapi.InsertJenisTerapiUiEvent
import com.example.klinik.ui.viewmodel.JenisTerapi.InsertJenisTerapiUiState
import com.example.klinik.ui.viewmodel.JenisTerapi.toInsertUiEvent
import com.example.klinik.ui.viewmodel.JenisTerapi.toJenisTerapi


class UpdateTerapisViewModel(
    savedStateHandle: SavedStateHandle,
    private val terapisRepository: TerapisRepository
) : ViewModel() {
    var updateUiState by mutableStateOf(InsertTerapisUiState())
        private set

    private val ID_TERAPIS: String = checkNotNull(savedStateHandle[DestinasiEditTerapis.ID_TERAPI]) {
        "ID pasien tidak ditemukan di SavedStateHandle"
    }

    init {
        viewModelScope.launch {
            try {
                val terapis = terapisRepository.getTerapisById(ID_TERAPIS)
                updateUiState = terapis.toUIStatePsn()
            } catch (e: Exception) {
                Log.e("UpdatePasienViewModel", "Error fetching pasien: ${e.message}", e)
            }
        }
    }

    fun updateInsertTerapisState(insertUiEvent: InsertTerapisUiEvent) {
        updateUiState = updateUiState.copy(insertUiEvent = insertUiEvent)
    }

    fun updateTerapis() {
        viewModelScope.launch {
            try {
                terapisRepository.updateTerapis(ID_TERAPIS, updateUiState.insertUiEvent.toTerapis())
            } catch (e: Exception) {
                Log.e("UpdateJenisTerapiViewModel", "Error updating jenis terapi: ${e.message}", e)
            }
        }
    }
}

fun Terapis.toUIStatePsn(): InsertTerapisUiState = InsertTerapisUiState(
    insertUiEvent = this.toInsertUiEvent()
)
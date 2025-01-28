package com.example.klinik.ui.viewmodel.JenisTerapi

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.SavedStateHandle
import com.example.klinik.model.JenisTerapi
import com.example.klinik.repository.JenisTerapiRepository
import com.example.klinik.ui.view.JenisTerapi.DestinasiEditJenisTerapi


class UpdateJenisTerapiViewModel(
    savedStateHandle: SavedStateHandle,
    private val jenisTerapiRepository: JenisTerapiRepository
) : ViewModel() {
    var updateUiState by mutableStateOf(InsertJenisTerapiUiState())
        private set

    private val ID_JENIS_TERAPI: String = checkNotNull(savedStateHandle[DestinasiEditJenisTerapi.ID_JENIS_TERAPI]) {
        "ID pasien tidak ditemukan di SavedStateHandle"
    }

    init {
        viewModelScope.launch {
            try {
                val jenisterapi = jenisTerapiRepository.getJenisTerapiById(ID_JENIS_TERAPI)
                updateUiState = jenisterapi.toUIStatePsn()
            } catch (e: Exception) {
                Log.e("UpdatePasienViewModel", "Error fetching pasien: ${e.message}", e)
            }
        }
    }

    fun updateInsertJenisTerapiState(insertUiEvent: InsertJenisTerapiUiEvent) {
        updateUiState = updateUiState.copy(insertUiEvent = insertUiEvent)
    }

    fun updateJenisTerapi() {
        viewModelScope.launch {
            try {
                jenisTerapiRepository.updateJenisTerapi(ID_JENIS_TERAPI, updateUiState.insertUiEvent.toJenisTerapi())
            } catch (e: Exception) {
                Log.e("UpdateJenisTerapiViewModel", "Error updating jenis terapi: ${e.message}", e)
            }
        }
    }
}

fun JenisTerapi.toUIStatePsn(): InsertJenisTerapiUiState = InsertJenisTerapiUiState(
    insertUiEvent = this.toInsertUiEvent()
)
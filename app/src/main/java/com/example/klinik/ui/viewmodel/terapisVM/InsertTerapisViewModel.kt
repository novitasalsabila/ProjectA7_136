package com.example.klinik.ui.viewmodel.terapisVM

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinik.model.Terapis
import com.example.klinik.repository.TerapisRepository
import kotlinx.coroutines.launch

class InsertTerapisViewModel(private val terapisRepo: TerapisRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertTerapisUiState())
        private set

    fun updateInsertTerapisState(insertUiEvent: InsertTerapisUiEvent) {
        uiState = InsertTerapisUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertTerapis() {
        viewModelScope.launch {
            try {
                Log.d("Retrofit", "Data dikirim: ${uiState.insertUiEvent.toTerapis()}")
                val response = terapisRepo.insertTerapis(uiState.insertUiEvent.toTerapis())
                Log.d("Retrofit", "Response: $response")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Retrofit", "Error: ${e.message}")
            }
        }
    }
}

fun InsertTerapisUiEvent.toTerapis(): Terapis = Terapis(
    id_terapis = id_terapis,
    nama_terapis = nama_terapis,
    spesialisasi = spesialisasi,
    nomor_izin_praktik = nomor_izin_praktik
)

fun Terapis.toUiStateTerapis(): InsertTerapisUiState = InsertTerapisUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Terapis.toInsertUiEvent(): InsertTerapisUiEvent = InsertTerapisUiEvent(
    id_terapis = id_terapis,
    nama_terapis = nama_terapis,
    spesialisasi = spesialisasi,
    nomor_izin_praktik = nomor_izin_praktik
)

data class InsertTerapisUiState(
    val insertUiEvent: InsertTerapisUiEvent = InsertTerapisUiEvent()
)

data class InsertTerapisUiEvent(
    val id_terapis: String = "",
    val nama_terapis: String = "",
    val spesialisasi: String = "",
    val nomor_izin_praktik: String = ""
)

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

class InsertPasienViewModel(private val pasienRepo: PasienRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertPasienUiState())
        private set

    fun updateInsertPasienState(insertUiEvent: InsertPasienUiEvent) {
        uiState = InsertPasienUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertPasien() {
        viewModelScope.launch {
            try {
                Log.d("Retrofit", "Data dikirim: ${uiState.insertUiEvent.toPasien()}")
                val response = pasienRepo.insertPasien(uiState.insertUiEvent.toPasien())
                Log.d("Retrofit", "Response: ${response}")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Retrofit", "Error: ${e.message}")
            }
        }
    }

}

fun InsertPasienUiEvent.toPasien(): Pasien = Pasien(
    id_pasien = id_pasien,
    nama_pasien = nama_pasien,
    alamat = alamat,
    nomor_telepon = nomor_telepon,
    tanggal_lahir = tanggal_lahir,
    riwayat_medikal = riwayat_medikal
)

fun Pasien.toUiStatePasien(): InsertPasienUiState = InsertPasienUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Pasien.toInsertUiEvent(): InsertPasienUiEvent = InsertPasienUiEvent(
    id_pasien = id_pasien,
    nama_pasien = nama_pasien,
    alamat = alamat,
    nomor_telepon = nomor_telepon,
    tanggal_lahir = tanggal_lahir,
    riwayat_medikal = riwayat_medikal
)

data class InsertPasienUiState(
    val insertUiEvent: InsertPasienUiEvent = InsertPasienUiEvent()
)

data class InsertPasienUiEvent(
    val id_pasien: String = "",
    val nama_pasien: String = "",
    val alamat: String = "",
    val nomor_telepon: String = "",
    val tanggal_lahir: String = "",
    val riwayat_medikal: String = ""
)

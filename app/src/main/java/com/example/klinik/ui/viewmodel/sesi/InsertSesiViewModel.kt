package com.example.klinik.ui.viewmodel.sesi

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinik.model.JenisTerapi
import com.example.klinik.model.Pasien
import com.example.klinik.model.SesiTerapi
import com.example.klinik.model.Terapis
import com.example.klinik.repository.JenisTerapiRepository
import com.example.klinik.repository.PasienRepository
import com.example.klinik.repository.SesiTerapiRepository
import com.example.klinik.repository.TerapisRepository
import kotlinx.coroutines.launch

class InsertSesiViewModel(
    private val sesiTerapiRepository: SesiTerapiRepository,
    private val pasienRepository: PasienRepository,
    private val terapisRepository: TerapisRepository,
    private val jenisTerapiRepository: JenisTerapiRepository
) : ViewModel() {

    var pasienList by mutableStateOf(listOf<Pasien>())
        private set

    var terapisList by mutableStateOf(listOf<Terapis>())
        private set

    var jenisTerapiList by mutableStateOf(emptyList<JenisTerapi>())
        private set

    var uiState by mutableStateOf(InsertSesiUiState())
        private set

    fun updateInsertSesiState(insertUiEvent: InsertSesiUiEvent) {
        uiState = InsertSesiUiState(insertUiEvent = insertUiEvent)
    }

    init {
        fetchDropdownData()
    }

    private fun fetchDropdownData() {
        viewModelScope.launch {
            try {
                pasienList = pasienRepository.getPasien()
                Log.d("DropdownData", "Pasien List: $pasienList")
                terapisList = terapisRepository.getTerapis()
                Log.d("DropdownData", "Terapis List: $terapisList")
                jenisTerapiList = jenisTerapiRepository.getAllJenisTerapi()
                Log.d("DropdownData", "Jenis Terapi List: $jenisTerapiList")
            } catch (e: Exception) {
                Log.e("DropdownData", "Error fetching data: ${e.message}")
            }
        }
    }


    suspend fun insertSesi() {
        viewModelScope.launch {
            try {
                Log.d("Retrofit", "Data dikirim: ${uiState.insertUiEvent.toSesiTerapi()}")
                val response = sesiTerapiRepository.insertSesi(uiState.insertUiEvent.toSesiTerapi())
                Log.d("Retrofit", "Response: ${response}")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Retrofit", "Error: ${e.message}")
            }
        }
    }

}

fun InsertSesiUiEvent.toSesiTerapi(): SesiTerapi = SesiTerapi(
    id_sesi = id_sesi,
    id_pasien = id_pasien,
    id_terapis = id_terapis,
    id_jenis_terapi = id_jenis_terapi,
    tanggal_sesi = tanggal_sesi,
    catatan_sesi = catatan_sesi
)

fun SesiTerapi.toUiStateSesi(): InsertSesiUiState = InsertSesiUiState(
    insertUiEvent = toInsertUiEvent()
)

fun SesiTerapi.toInsertUiEvent(): InsertSesiUiEvent = InsertSesiUiEvent(
    id_sesi = id_sesi,
    id_pasien = id_pasien,
    id_terapis = id_terapis,
    id_jenis_terapi = id_jenis_terapi,
    tanggal_sesi = tanggal_sesi,
    catatan_sesi = catatan_sesi
)

data class InsertSesiUiState(
    val insertUiEvent: InsertSesiUiEvent = InsertSesiUiEvent()
)

data class InsertSesiUiEvent(
    val id_sesi: String = "",
    val id_pasien: String = "",
    val id_terapis: String = "",
    val id_jenis_terapi: String = "",
    val tanggal_sesi: String = "",
    val catatan_sesi: String = ""
)
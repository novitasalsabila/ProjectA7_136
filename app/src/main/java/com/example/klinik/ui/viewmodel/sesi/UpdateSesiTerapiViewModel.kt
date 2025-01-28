package com.example.klinik.ui.viewmodel.sesi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log
import com.example.klinik.model.JenisTerapi
import com.example.klinik.model.Pasien
import com.example.klinik.model.SesiTerapi
import com.example.klinik.model.Terapis
import com.example.klinik.repository.JenisTerapiRepository
import com.example.klinik.repository.PasienRepository
import com.example.klinik.repository.SesiTerapiRepository
import com.example.klinik.repository.TerapisRepository
import com.example.klinik.ui.navigation.DestinasiNavigasi
import com.example.klinik.ui.view.SesiTerapis.DestinasiUpdateSesiTerapi

object DestinasiUpdateSesiTerapi : DestinasiNavigasi {
    override val route = "update sesi"
    const val ID_SESI = "id_sesi"
    override val titleRes = "Detail Sesi"
    val routeWithArg = "$route/{$ID_SESI}"
}

class UpdateSesiTerapiViewModel(
    savedStateHandle: SavedStateHandle,
    private val sesiTerapiRepository: SesiTerapiRepository,
    private val pasienRepository: PasienRepository,
    private val terapisRepository: TerapisRepository,
    private val jenisTerapiRepository: JenisTerapiRepository
) : ViewModel() {

    var pasienList by mutableStateOf(listOf<Pasien>())
        private set

    var terapisList by mutableStateOf(listOf<Terapis>())
        private set

    var jenisTerapiList by mutableStateOf(listOf<JenisTerapi>())
        private set

    var uiState by mutableStateOf(InsertSesiUiState())
        private set

    private val id_sesi: String = checkNotNull(savedStateHandle[DestinasiUpdateSesiTerapi.ID_SESI])

    init {
        fetchDropdownData()
        loadSesiData()
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

    private fun loadSesiData() {
        viewModelScope.launch {
            try {
                val sesiTerapi = sesiTerapiRepository.getSesiById(id_sesi)
                uiState = InsertSesiUiState(insertUiEvent = sesiTerapi.toInsertUiEvent())
                Log.d("SesiTerapiData", "Sesi Terapi loaded: $sesiTerapi")
            } catch (e: Exception) {
                Log.e("SesiTerapiData", "Error loading sesi terapi data: ${e.message}")
            }
        }
    }

    fun updateInsertSesiTerapiState(insertUiEvent: InsertSesiUiEvent) {
        uiState = InsertSesiUiState(insertUiEvent = insertUiEvent)
    }


    suspend fun updateSesi() {
        viewModelScope.launch {
            try {
                Log.d("Retrofit", "Data dikirim: ${uiState.insertUiEvent.toSesiTerapi()}")
                val response = sesiTerapiRepository.updateSesi(id_sesi, uiState.insertUiEvent.toSesiTerapi())
                Log.d("Retrofit", "Response: $response")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Retrofit", "Error: ${e.message}")
            }
        }
    }
}
fun SesiTerapi.toUIStatePsn(): InsertSesiUiState = InsertSesiUiState(
    insertUiEvent = this.toInsertUiEvent()
)